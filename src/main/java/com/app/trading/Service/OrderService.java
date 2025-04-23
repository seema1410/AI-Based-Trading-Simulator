package com.app.trading.Service;

import java.util.List;

import com.app.trading.Domain.OrderType;
import com.app.trading.Model.Coin;
import com.app.trading.Model.Order;
import com.app.trading.Model.OrderItem;
import com.app.trading.Model.User;

public interface OrderService {
	
	Order createOrder(User user , OrderItem orderItem  ,  OrderType orderType );
	Order getOrderById(Long orderId) throws Exception;
	List<Order> getAllOrderOfUser(Long userId,OrderType orderType,String assetSymbol);
	Order processOrder(Coin coin , double quantity , OrderType orderType , User user) throws Exception;

}