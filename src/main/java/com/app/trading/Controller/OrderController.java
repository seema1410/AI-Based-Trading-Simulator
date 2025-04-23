package com.app.trading.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.trading.Domain.OrderType;
import com.app.trading.Model.Coin;
import com.app.trading.Model.Order;
import com.app.trading.Model.User;
import com.app.trading.Service.CoinService;
import com.app.trading.Service.OrderService;
import com.app.trading.Service.UserService;
import com.app.trading.request.CreateOrderRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	
	@PostMapping("/pay")
	public ResponseEntity<Order> payOrderPayment
	(@RequestHeader("Authorization") String jwt,
			@RequestBody CreateOrderRequest req)throws Exception{
		
		User user = userService.findUserProfileByJwt(jwt);
		Coin coin = coinService.findById(req.getCoinId());
		
		Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);//usererror
		
		return ResponseEntity.ok(order);
		
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(
			@RequestHeader("Authorization") String jwtToken,
			@PathVariable Long OrderId) throws Exception{
		
		User user = userService.findUserProfileByJwt(jwtToken);
		Order order = orderService.getOrderById(OrderId);
		if(order.getUser().getId().equals(user.getId())) {
			return ResponseEntity.ok(order);	
		}else {
			throw new Exception("User dont have access");
		}
	}
	public ResponseEntity<List<Order>> getAllOrderForUser(
			@RequestHeader("Authorization") String jwt,
			@RequestParam(required=false) OrderType order_Type,
			@RequestParam(required = false) String asset_Symbol) throws Exception{
		
		
		Long userId = userService.findUserProfileByJwt(jwt).getId();
		List<Order> userOrders=orderService.getAllOrderOfUser(userId, order_Type, asset_Symbol);
		return ResponseEntity.ok(userOrders);
	}
	
	

	

}
