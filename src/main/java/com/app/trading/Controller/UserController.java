package com.app.trading.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Domain.VerificationType;
import com.app.trading.Model.ForgotPasswordToken;
import com.app.trading.Model.User;
import com.app.trading.Model.VerificationCode;
import com.app.trading.Response.ApiResponse;
import com.app.trading.Response.AuthResponse;
import com.app.trading.Service.EmailService;
import com.app.trading.Service.ForgotPasswordService;
import com.app.trading.Service.UserService;
import com.app.trading.Service.VerificationCodeService;
import com.app.trading.request.ForgotPasswordTokenRequest;
import com.app.trading.request.ResetPasswordRequest;
import com.app.trading.utils.OtpUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PostMapping("/api/users/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOtp(
			@RequestHeader("Authorization")
			String jwt,
			@PathVariable VerificationType verificationType) throws Exception{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
		
		if(verificationCode==null) {
			verificationCode=verificationCodeService.sendVerficationCode(user, verificationType);
			
		}
		
		if(verificationType.equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
			
		}
		
		return new ResponseEntity<>("Verification otp successfully",HttpStatus.OK);
	}
	@PatchMapping("/api/users/enables-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactor(
			@PathVariable String otp,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode= verificationCodeService.getVerificationCodeByUser(user.getId());
			String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
					verificationCode.getEmail():verificationCode.getMobile();
			boolean isVerified= verificationCode.getOtp().equals(otp);
			
			if( isVerified) {
				User updatedUser= userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
				verificationCodeService.deleteVerificationCodeById(verificationCode);
				return new ResponseEntity<>(updatedUser,HttpStatus.OK);
			}
			throw new Exception ("Wrong otp");
		}
	
	
	@PostMapping("/auth/users/reset-password/send-otp")
	public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
			@RequestBody ForgotPasswordTokenRequest req) throws Exception{
		
		User user = userService.findUserByEmail(req.getSendTo());
		
		String otp=OtpUtils.generateOTP();
		UUID uuid = UUID.randomUUID();
		String id =uuid.toString();
		ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
		
		if(token==null) {
			token = forgotPasswordService.createToken(user, id, otp, req.getVerificationType(),req.getSendTo());
		}
		
		if(req.getVerificationType().equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
		}
		
		AuthResponse response = new AuthResponse();
		response.setSession(token.getId());
		response.setMessage("password reset otp sent successfully");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	

	@PatchMapping("/api/users/reset-password/verify-otp")
	public ResponseEntity<ApiResponse> resetPasswordOtp(
			@RequestParam String id,
			@RequestBody ResetPasswordRequest req ,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		ForgotPasswordToken forgotPasswordToken= forgotPasswordService.findById(id);
			boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
			
			if(isVerified) {
				userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
			 ApiResponse res = new ApiResponse();
			 res.setMessage("password update sucessfully");
			 return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
			
			}
			
			throw new Exception ("wrong otp");
			
	
	}
	



}
