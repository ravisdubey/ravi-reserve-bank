package com.demo.reserve.bank.asset.management.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.reserve.bank.asset.management.api.model.Asset;

public interface AssetManagementRepository extends JpaRepository<Asset, Long> {
	List<Asset> findByAssetCodeIn(List<String> assetCode);

}
