package com.app.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	Wallet findByUserId(Long userId);
	


}
