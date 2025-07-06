package com.demo.reserve.bank.asset.management.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.reserve.bank.asset.management.api.dto.AssetManagementResponse;
import com.demo.reserve.bank.asset.management.api.service.AssetManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class that handles HTTP requests related to asset management.
 */
@RestController
@RequestMapping("/api/asset-management")
@RequiredArgsConstructor
@Slf4j
public class AssetManagementController {
	
	@Autowired
    private final AssetManagementService assetManagementService;

    /**
     *
     *Retrieves the availability status of assets based on their codes.
     *@param assetCode The list of asset codes to check availability for.
     *@return The list of asset management responses containing the availability status for each asset code.
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AssetManagementResponse> isAssetAvailable(@RequestParam List<String> assetCode) {
        log.info("Received asset availability check request for asset code: {}", assetCode);
        return assetManagementService.isAssetAvailable(assetCode);
    }
}