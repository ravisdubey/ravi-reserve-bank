package com.demo.reserve.bank.asset.management.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.reserve.bank.asset.management.api.dto.AssetManagementResponse;
import com.demo.reserve.bank.asset.management.api.repository.AssetManagementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetManagementService {

	private final AssetManagementRepository assetManagementRepository;

	public List<AssetManagementResponse> isAssetAvailable(List<String> assetCode) {
		log.info("Checking asset availability");
		return assetManagementRepository.findByAssetCodeIn(assetCode).stream()
				.map(asset -> AssetManagementResponse
				.builder()
				.assetCode(asset.getAssetCode())
				.isAssetAvailable(asset.getValue() > 0)
				.build())
				.toList();
	}
}
