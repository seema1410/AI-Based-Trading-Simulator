package com.app.trading.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Model.Order;
import com.app.trading.Model.PaymentOrder;
import com.app.trading.Model.User;
import com.app.trading.Model.Wallet;
import com.app.trading.Model.WalletTransaction;
import com.app.trading.Service.OrderService;
import com.app.trading.Service.PaymentService;
import com.app.trading.Service.UserService;
import com.app.trading.Service.WalletService;

@RestController


public class WalletController {
	
    @Autowired
	private WalletService walletService;
    
    @Autowired
    private UserService  userService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
   
    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String Jwt) throws Exception{
    	
    	User user=userService.findUserProfileByJwt(Jwt);
    	
    	Wallet wallet = walletService.getUserWallet(user);
    	
    	return  new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
    		@RequestHeader("Authorization") String jwt ,
    		@PathVariable  Long walletId,
    		@RequestBody WalletTransaction req)throws Exception{
    	
    	User senderUser = userService.findUserProfileByJwt(jwt);
    	Wallet receiveWallet = walletService.findWalletById(walletId);
    	Wallet wallet = walletService.WalletToWalletTransfer(senderUser, receiveWallet, req.getAmount());
    	
    	return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
    		@RequestHeader("Authorization") String jwt ,
    		@PathVariable  Long orderId)throws Exception{
    	
    	User user = userService.findUserProfileByJwt(jwt);
    	Order order = orderService.getOrderById(orderId);
    	Wallet wallet  = walletService.payOrderPayment(order, user);
    	return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(
    		@RequestHeader("Authorization") String jwt ,
    		@RequestParam(name="order_id") Long orderId,
    		@RequestParam(name="payment_id") String paymentId
    		)throws Exception{
    	
    	User user = userService.findUserProfileByJwt(jwt);
    	PaymentOrder order =paymentService.getPaymentOrderById(orderId);
    	Wallet wallet  = walletService.getUserWallet(user);
    	Boolean status = paymentService.ProccedPaymentOrder(order, paymentId);
  
    	if(wallet.getBalance()==null) {
    		wallet.setBalance(BigDecimal.valueOf(0));
    	}
    	
    	if(status) {
    		wallet = walletService.addBalance(wallet, order.getAmount());
    	}
    	return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
    
}