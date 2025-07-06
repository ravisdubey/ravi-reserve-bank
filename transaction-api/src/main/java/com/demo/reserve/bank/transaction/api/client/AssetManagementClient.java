package com.demo.reserve.bank.transaction.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.reserve.bank.transaction.api.dto.AssetManagementResponse;

import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(value = "asset-management-api", url = "http://localhost:8081")
@Retry(name = "asset-management")
public interface AssetManagementClient {
	
	@GetMapping("/api/asset-management")
	List<AssetManagementResponse> checkAssetAvailability(@RequestParam List<String> assetCode);
}
