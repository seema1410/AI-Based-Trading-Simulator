package com.app.trading.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
	
	public VerificationCode findByUserId(Long userId);

}
