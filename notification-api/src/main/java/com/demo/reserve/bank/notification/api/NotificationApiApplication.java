package com.demo.reserve.bank.notification.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

import com.demo.reserve.bank.notification.api.event.TransactionEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApiApplication.class, args);
	}
	
	/**
	 *This method is a Kafka message listener for the "notificationTopic" topic.
	 *It handles incoming messages and processes the TransactionEvent object.
	 *@param transactionEvent The TransactionEvent object received from the Kafka message.
	 */
	@KafkaListener(topics = "notificationTopic", groupId = "notificationId")
	public void handleNotification(TransactionEvent transactionEvent) {
		log.info("Received notification for transaction: {}", transactionEvent.getTransactionId());
	}

}
