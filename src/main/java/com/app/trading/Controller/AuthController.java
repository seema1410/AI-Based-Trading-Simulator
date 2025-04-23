package com.app.trading.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Config.JwtProvider;
import com.app.trading.Model.TwoFactorOTP;
import com.app.trading.Model.User;
import com.app.trading.Repository.UserRepository;
import com.app.trading.Response.AuthResponse;
import com.app.trading.Service.CustomeUserDetailsService;
import com.app.trading.Service.EmailService;
import com.app.trading.Service.TwoFactorOtpService;
import com.app.trading.Service.WatchListService;
import com.app.trading.utils.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomeUserDetailsService customeUserDetailsService;
	
	@Autowired
	private TwoFactorOtpService twoFactorOtpService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private WatchListService watchListService;

	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user)throws Exception{
		User isEmailExist=userRepository.findByEmail(user.getEmail());
		if(isEmailExist!=null) {
			throw new Exception("Email is already used with another account");
		}
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		 User savedUser = userRepository.save(newUser);
		 
		 watchListService.createWatchList(savedUser);
	
		  
		 
		Authentication auth = new UsernamePasswordAuthenticationToken(
				user.getEmail()
				,user.getPassword()
				);
		
		
		
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        String jwt = JwtProvider.generateToken(auth);
        
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");
		 
		return  new  ResponseEntity<>(res, HttpStatus.CREATED);


	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user)throws Exception{
		
		
		   String userName = user.getEmail();
		   
		   String password = user.getPassword(); 
		
		
		Authentication auth =authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        String jwt = JwtProvider.generateToken(auth);
        
        User authUser =userRepository.findByEmail(userName);
        
        if(user.getTwoFactorAuth().isEnabled()) {
        	AuthResponse res = new AuthResponse();
        	res.setMessage("Two Factor Auth Is Enabled");
        	res.setTwoFactorAuthEnabled(true);
        	String otp = OtpUtils.generateOTP();
        	
        	TwoFactorOTP oldTwoFactorOTP=twoFactorOtpService.findByUser(authUser.getId());
        	
        	if(oldTwoFactorOTP!=null) {
        		twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
        		
        	}
        	TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);
        	
        	emailService.sendVerificationOtpEmail(userName,otp);
        	
        	res.setSession(newTwoFactorOTP.getId());
        	return new 	ResponseEntity<>(res,HttpStatus.CREATED);
        	
        	
        }
        
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");
		 
		return  new  ResponseEntity<>(res, HttpStatus.CREATED);


	}
	private Authentication authenticate(String userName,String password) {
		UserDetails userDetails=customeUserDetailsService.loadUserByUsername(userName);
		
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username");
		}
		if(!password.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("invalid username");
		}
		
		return new  UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
			
	}
	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySigInOtp(
			@PathVariable String otp,
			@RequestParam String id) throws Exception{
		
		TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
		
		if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
			AuthResponse res = new AuthResponse();
			res.setMessage("Two Factor Authentication Verified");
			res.setTwoFactorAuthEnabled(true);
			res.setJwt(twoFactorOTP.getJwt());
			return new ResponseEntity<>(res , HttpStatus.CREATED);
			
		}
		
		throw new Exception ("invalid otp");
		
	}

}