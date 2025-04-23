package com.app.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.TwoFactorOTP;
import com.app.trading.Model.User;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP ,String> {
	
	TwoFactorOTP findByUserId(Long userId);//String ki jagah Long likha h
	

}
