package com.app.trading.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Model.Coin;
import com.app.trading.Model.PaymentDetails;
import com.app.trading.Model.User;
import com.app.trading.Model.WatchList;
import com.app.trading.Service.PaymentDetailsService;
import com.app.trading.Service.UserService;
import com.app.trading.Service.WatchListService;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {
	
	@Autowired
	private  WatchListService watchListService;
	
	@Autowired
	private  UserService userService;
	
	@Autowired
	private PaymentDetailsService paymentDetailsService;
	
	@PostMapping("/payment-details")
	public ResponseEntity<PaymentDetails> addPaymentDetails(
			@RequestHeader("Authorization") String jwt,
			@RequestBody PaymentDetails paymentDetailsRequest)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
				paymentDetailsRequest.getAccountNumber(),
				paymentDetailsRequest.getAccountHolderName(),
				paymentDetailsRequest.getIfsc(),
				paymentDetailsRequest.getBankName(),
				user);
		return new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
	}
	
	@GetMapping("/payment-details")
	public ResponseEntity<PaymentDetails> getUsersPaymentDetails(
			@RequestHeader("Authorization") String jwt)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		PaymentDetails paymentDetails = paymentDetailsService.getUsersPaymentsDetails(user);
		return  new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
		
	}


}
