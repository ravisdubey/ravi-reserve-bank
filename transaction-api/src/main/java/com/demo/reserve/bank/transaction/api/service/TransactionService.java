package com.demo.reserve.bank.transaction.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.demo.reserve.bank.transaction.api.client.AssetManagementClient;
import com.demo.reserve.bank.transaction.api.dto.AssetManagementResponse;
import com.demo.reserve.bank.transaction.api.dto.TransactionItemsDto;
import com.demo.reserve.bank.transaction.api.dto.TransactionRequest;
import com.demo.reserve.bank.transaction.api.event.TransactionEvent;
import com.demo.reserve.bank.transaction.api.model.Transaction;
import com.demo.reserve.bank.transaction.api.model.TransactionItems;
import com.demo.reserve.bank.transaction.api.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final AssetManagementClient assetManagementClient;
	private final KafkaTemplate<String, TransactionEvent> kafkaTemplate; 
	
	@CacheEvict(value = "assetAvailabilty", allEntries = true)
	public String processTransaction(TransactionRequest transactionRequest) {
		long start = System.currentTimeMillis();
		Transaction transaction = new Transaction();
		transaction.setTransactionId(UUID.randomUUID().toString());

		List<TransactionItems> transactionItems = transactionRequest.getTransactionItemsDtoList().stream()
				.map(this::mapToDto).toList();

		transaction.setTransactionItemsList(transactionItems);

		List<String> assetCodes = transaction.getTransactionItemsList().stream().map(TransactionItems::getAssetCode)
				.toList();

		boolean assetIsAvailable = checkAssetAvailability(assetCodes);

		if (assetIsAvailable) {
			transactionRepository.save(transaction);
			log.info("Call duration after processing: {} ms", System.currentTimeMillis() - start); 
			kafkaTemplate.send("notificationTopic", new TransactionEvent(transaction.getTransactionId()));
			return "Transaction Completed Successfully";
		} else {
			throw new IllegalArgumentException("Asset is not available, please try again later");
		}
	}

	/**
	 * Checks the availability of assets.
	 *
	 * @param assetCodes The list of asset codes to check.
	 * @return true if all assets are available, false otherwise.
	 */
	@Cacheable("assetAvailability")
	private boolean checkAssetAvailability(List<String> assetCodes) {
		return assetManagementClient.checkAssetAvailability(assetCodes).stream()
				.allMatch(AssetManagementResponse::isAssetAvailable);
	}

	/**
	 *
	 * Maps the provided transaction items DTO to a TransactionItems object.
	 * 
	 * @param transactionItemsDto The transaction items DTO to be mapped.
	 * @return The corresponding TransactionItems object.
	 */
	private TransactionItems mapToDto(TransactionItemsDto transactionItemsDto) {
		TransactionItems transactionItems = new TransactionItems();
		transactionItems.setAssetCode(transactionItemsDto.getAssetCode());
		transactionItems.setAssetName(transactionItemsDto.getAssetName());
		transactionItems.setValue(transactionItemsDto.getValue());
		return transactionItems;
	}
}
