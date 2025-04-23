package com.app.trading.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Domain.Asset;
import com.app.trading.Domain.OrderStatus;
import com.app.trading.Domain.OrderType;
import com.app.trading.Model.Coin;
import com.app.trading.Model.Order;
import com.app.trading.Model.OrderItem;
import com.app.trading.Model.User;
import com.app.trading.Repository.OrderItemRepository;
import com.app.trading.Repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private WalletService walletService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private AssetService assetService;

	@Override
	public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
		double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();

		Order order = new Order();
		order.setUser(user);
		order.setOrderItem(orderItem);
		order.setOrderType(orderType);
		order.setPrice(BigDecimal.valueOf(price));
		order.setTimeStamp(LocalDateTime.now());
		order.setStatus(OrderStatus.PENDING);
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long orderId) throws Exception {

		return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
	}

	@Override
	public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {

		return orderRepository.findByUserId(userId);
	}

	private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
		OrderItem orderItem = new OrderItem();
		orderItem.setCoin(coin);
		orderItem.setQuantity(quantity);
		orderItem.setBuyPrice(buyPrice);
		orderItem.setSellPrice(sellPrice);
		return orderItemRepository.save(orderItem);

	}

	@Transactional
	public Order buyAsset(Coin coin, Double quantity, User user) throws Exception {
		double buyPrice = coin.getCurrentPrice();
		OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
		Order order = createOrder(user, orderItem, OrderType.BUY);
		orderItem.setOrder(order);
		walletService.payOrderPayment(order, user);
		order.setStatus(OrderStatus.SUCCESS);
		order.setOrderType(OrderType.BUY);
		Order savedOrder = orderRepository.save(order);

		Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
				order.getOrderItem().getCoin().getId());

		if (oldAsset == null) {
			assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());

		} else {
			assetService.updateAsset(oldAsset.getId(), quantity);

		}

		return savedOrder;
	}

	@Transactional
	public Order sellAsset(Coin coin, Double quantity, User user) throws Exception {
		if (quantity <= 0) {
			throw new Exception("Quantity should be greater then 0");
		}
		double sellPrice = coin.getCurrentPrice();

		Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());

		if (assetToSell != null) {
			double buyPrice = assetToSell.getBuyPrice();
			
			OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
		

		Order order = createOrder(user, orderItem, OrderType.SELL);
		orderItem.setOrder(order);

		if (assetToSell.getQuantity() >= quantity) {

			order.setStatus(OrderStatus.SUCCESS);
			order.setOrderType(OrderType.SELL);
			Order savedOrder = orderRepository.save(order);
			
			walletService.payOrderPayment(order, user);
			Asset updatedAsset = assetService.updateAsset(assetToSell.getId(),-quantity);
			
			if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
				assetService.deleteAsset(updatedAsset.getId());
			}
			return savedOrder;
		}

		throw new Exception("Insufficient quantity to sell");
	}
	throw new Exception ("asset not found");

	}

	@Transactional
	@Override
	public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
		if (orderType.equals(OrderType.BUY)) {
			return buyAsset(coin, quantity, user);

		} else if (orderType.equals(OrderType.SELL)) {
			return sellAsset(coin, quantity, user);

		}
		throw new Exception("invalid order Type");
	}

}
