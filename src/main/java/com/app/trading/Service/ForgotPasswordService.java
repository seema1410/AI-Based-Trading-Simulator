package com.app.trading.Service;

import com.app.trading.Domain.VerificationType;
import com.app.trading.Model.ForgotPasswordToken;
import com.app.trading.Model.User;

public interface ForgotPasswordService {
	
	ForgotPasswordToken createToken(User user , String id , String otp ,
			VerificationType verificationType , String sendTo);
	
	ForgotPasswordToken findById(String id);
	
	ForgotPasswordToken findByUser(Long userId);
	
	void deleteToken(ForgotPasswordToken token);

	
	

}
