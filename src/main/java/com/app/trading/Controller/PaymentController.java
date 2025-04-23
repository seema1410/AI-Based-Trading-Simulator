package com.app.trading.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Domain.PaymentMethod;
import com.app.trading.Model.PaymentOrder;
import com.app.trading.Model.User;
import com.app.trading.Response.PaymentResponse;
import com.app.trading.Service.PaymentService;
import com.app.trading.Service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

@RestController

public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;
	
	@Autowired
    private UserService userService;
	
	@PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
	public ResponseEntity<PaymentResponse> paymentHandler(
			@PathVariable PaymentMethod paymentMethod,
			@PathVariable Long amount,
			@RequestHeader("Authorization") String jwt)throws Exception, RazorpayException , StripeException{
		
		User user = userService.findUserProfileByJwt(jwt);
		PaymentResponse paymentResponse;
		
		PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
		
		if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
			paymentResponse = paymentService.createRazorpayPaymentLing(user, amount,order.getId());
		}else {
			paymentResponse = paymentService.createStripePaymentLing(user, amount,order.getId());
		}
		
		return new ResponseEntity<>(paymentResponse,HttpStatus.CREATED);
		
	}

}
