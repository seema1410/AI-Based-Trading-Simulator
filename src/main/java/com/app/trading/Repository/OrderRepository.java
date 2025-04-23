package com.app.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Domain.Asset;
import com.app.trading.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByUserId(Long userId);
	//Asset findByUserIdAndCoindId(Long userId, Long coinId);

}