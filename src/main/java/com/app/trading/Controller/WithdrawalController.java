package com.app.trading.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Model.User;
import com.app.trading.Model.Wallet;
import com.app.trading.Model.Withdrawal;
import com.app.trading.Service.UserService;
import com.app.trading.Service.WalletService;
import com.app.trading.Service.WithdrawalService;
import com.app.trading.request.CreateOrderRequest;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
	@Autowired
	private WithdrawalService withdrawalService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/withdrawal/{amount}")
	public ResponseEntity<?>withdrawalRequest(@RequestHeader("Authorization") String jwt,
			@PathVariable Long amount)throws Exception {
		
		User user =userService.findUserProfileByJwt(jwt);
		Wallet userWallet=walletService.getUserWallet(user);
		
		Withdrawal withdrawal = withdrawalService.requestyWithdrawal(amount, user);
		walletService.addBalance(userWallet,-withdrawal.getAmount());
		
		//yaha transaction wala part aayega 
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
		
		
	}
	
	@PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
	public ResponseEntity<?> proceedWithdrawal(
			@PathVariable Long id,
			@PathVariable boolean accept,
			@RequestHeader("Authorization") String jwt)throws Exception {
		User user =userService.findUserProfileByJwt(jwt);
		Withdrawal withdrawal = withdrawalService.procedWithdrawal(id, accept);
		Wallet userWallet=walletService.getUserWallet(user);
		if(!accept) {
			walletService.addBalance(userWallet, withdrawal.getAmount());
			
		}
		return 	new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	@GetMapping("/api/withdrawal")
	public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
			@RequestHeader("Authorization") String jwt)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		List<Withdrawal> withdrawal = withdrawalService.getUsersWithdrawalHistory(user);
		return new ResponseEntity<>(withdrawal,HttpStatus.OK);
		
	}
	
	@GetMapping("/api/admin/withdrawal")
	public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt)throws Exception{
		User user =userService.findUserProfileByJwt(jwt);
		List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();
		return new ResponseEntity<>(withdrawal,HttpStatus.OK);
	}
	
}