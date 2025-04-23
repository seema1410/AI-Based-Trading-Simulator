package com.app.trading.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Model.TwoFactorOTP;
import com.app.trading.Model.User;
import com.app.trading.Repository.TwoFactorOtpRepository;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService{
	
	@Autowired
	private TwoFactorOtpRepository twoFactorOtpRepository;

	@Override
	public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
		
		UUID uuid = UUID.randomUUID();
		
		String id = uuid.toString();
		
		TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
		
		twoFactorOTP.setOtp(otp);
		twoFactorOTP.setJwt(jwt);
		twoFactorOTP.setId(id);
		twoFactorOTP.setUser(user);
		
		
		return twoFactorOtpRepository.save(twoFactorOTP);
	}

	@Override
	public TwoFactorOTP findByUser(Long userId) {
		User user = new User();//changes kiye h
        user.setId(userId); // Assuming your User class has an `setId` method
		return twoFactorOtpRepository.findByUserId(userId);
	}

	@Override
	public TwoFactorOTP findById(String id) {
		Optional<TwoFactorOTP> opt = twoFactorOtpRepository.findById(id);
		return opt.orElse(null);
	}

	@Override
	public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
		
		return twoFactorOtp.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {
		twoFactorOtpRepository.delete(twoFactorOtp);
		
	}

}
