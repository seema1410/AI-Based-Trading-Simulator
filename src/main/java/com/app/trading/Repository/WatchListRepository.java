package com.app.trading.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long>{
	
	WatchList findByUserId(Long userId);

}