package com.app.trading.Service;

import com.app.trading.Domain.VerificationType;
import com.app.trading.Model.User;
import com.app.trading.Model.VerificationCode;

public interface VerificationCodeService {
	
	VerificationCode sendVerficationCode(User user,VerificationType verificationType);
	
	VerificationCode getVerificationCodeById(long id) throws Exception;
	
	VerificationCode  getVerificationCodeByUser(Long userId);
	
	void deleteVerificationCodeById(VerificationCode verificationCode);

}
