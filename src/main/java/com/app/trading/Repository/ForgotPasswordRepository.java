package com.app.trading.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String>{
	
	ForgotPasswordToken findByUserId(Long userId);

}
