package com.app.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
