package com.app.trading.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Model.Coin;
import com.app.trading.Model.User;
import com.app.trading.Model.WatchList;
import com.app.trading.Model.Withdrawal;
import com.app.trading.Service.CoinService;
import com.app.trading.Service.UserService;
import com.app.trading.Service.WatchListService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
	
	@Autowired
	private  WatchListService watchListService;
	
	@Autowired
	private  UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@GetMapping("/user")
	public ResponseEntity<WatchList> getUserWatchList(
			@RequestHeader("Authorization") String jwt)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		WatchList watchList = watchListService.findUserWatchList(user.getId());
		return ResponseEntity.ok(watchList);
		
	}
	@GetMapping("/{watchListId}")
	public ResponseEntity<WatchList> getWatchListById(
			@PathVariable Long watchListId)throws Exception{
		
		WatchList watchList = watchListService.findById(watchListId);
		return ResponseEntity.ok(watchList);
		
	}
	
	@PostMapping("/add/coin/{coinId}")
	public ResponseEntity<Coin> addItemToWatchList(
			@RequestHeader("Authorization") String jwt,
			@PathVariable String coinId)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Coin coin = coinService.findById(coinId);
		Coin addedCoin = watchListService.addItemToWatchList(coin,user);
		return ResponseEntity.ok(addedCoin);
		
	}

}