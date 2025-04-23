package com.app.trading.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Model.Coin;
import com.app.trading.Model.User;
import com.app.trading.Model.WatchList;
import com.app.trading.Repository.WatchListRepository;

@Service
public class WatchListServiceImpl  implements WatchListService{
	
	@Autowired
	private WatchListRepository watchListRepository;

	@Override
	public WatchList findUserWatchList(Long userId) throws Exception {
		WatchList watchList=watchListRepository.findByUserId(userId);
		
		if(watchList==null) {
			throw new Exception("WatchList not found");
		}
		return watchList;
	}

	@Override
	public WatchList createWatchList(User user) {
		WatchList watchList=new WatchList();
		watchList.setUser(user);
		return watchListRepository.save(watchList);
	}

	@Override
	public WatchList findById(Long id) throws Exception {
		Optional<WatchList> watchListOptionl = watchListRepository.findById(id);
		if(watchListOptionl.isEmpty()) {
			throw new Exception("WatchList not found");
		}
		
		return watchListOptionl.get();
	}

	@Override
	public Coin addItemToWatchList(Coin coin, User user) throws Exception {
		WatchList watchList=findUserWatchList(user.getId());
		if(watchList.getCoins().contains(coin)) {
			watchList.getCoins().remove(coin);
		}else 
			watchList.getCoins().add(coin);
		watchListRepository.save(watchList);
		return coin;
					

	}

}