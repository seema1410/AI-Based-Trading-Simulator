package com.app.trading.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Config.JwtProvider;
import com.app.trading.Domain.VerificationType;
import com.app.trading.Model.TwoFactorAuth;
import com.app.trading.Model.User;
import com.app.trading.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {
		String  email = JwtProvider.getEmailFromToken(jwt);
		User user = userRepository .findByEmail(email);
		
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		
		User user = userRepository .findByEmail(email);
		
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new Exception  ("User not found");
		}
		return user.get();
	}

	@Override
	public User enableTwoFactorAuthentication(VerificationType verificationType,
			String sendTo,
			User user) {
		TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
		twoFactorAuth.setEnabled(true);
		twoFactorAuth.setSendTo(verificationType);
		
		user.setTwoFactorAuth(twoFactorAuth);
		
		return userRepository.save(user);
		
	}

	@Override
	public User updatePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		
		return userRepository.save(user);
	}

}