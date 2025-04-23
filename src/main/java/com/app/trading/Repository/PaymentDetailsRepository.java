package com.app.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
	
	PaymentDetails findByUserId(Long userId);
	
	

}