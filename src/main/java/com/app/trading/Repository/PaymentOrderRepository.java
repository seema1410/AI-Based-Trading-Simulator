package com.app.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

}