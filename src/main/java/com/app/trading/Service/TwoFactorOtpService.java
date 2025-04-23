package com.app.trading.Service;

import com.app.trading.Model.TwoFactorOTP;
import com.app.trading.Model.User;

public interface TwoFactorOtpService {
	
	TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);
	
	TwoFactorOTP findByUser(Long userId);
	
	TwoFactorOTP findById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp,String otp);
	
	void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
	
	

}
 
