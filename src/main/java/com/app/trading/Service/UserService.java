package com.app.trading.Service;

import com.app.trading.Domain.VerificationType;
import com.app.trading.Model.User;

public interface UserService {
	
	public User findUserProfileByJwt(String jwt) throws Exception;
	
	public User findUserByEmail(String email) throws Exception;
	
	public User findUserById(Long userId) throws Exception;
	
	public User  enableTwoFactorAuthentication(
			VerificationType verificationType,
			String sendTo,
			User user);
	
	User updatePassword(User user  , String newPassword);
	
	
	

}