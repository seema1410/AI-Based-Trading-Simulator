package com.app.trading.Service;

import com.app.trading.Model.Coin;
import com.app.trading.Model.User;
import com.app.trading.Model.WatchList;

public interface WatchListService {
	
	WatchList findUserWatchList(Long userId) throws Exception;
	WatchList createWatchList(User user);
	WatchList findById(Long id) throws Exception;
	
	Coin addItemToWatchList(Coin coin , User user) throws Exception;

}