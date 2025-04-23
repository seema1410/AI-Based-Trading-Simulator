package com.app.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Domain.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {
	
	List<Asset> findByUserId(Long userId);

	Asset findByUserIdAndCoinId(Long userId, String coinId);

}
