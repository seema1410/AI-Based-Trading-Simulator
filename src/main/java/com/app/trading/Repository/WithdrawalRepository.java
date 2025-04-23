package com.app.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
	
	List<Withdrawal> findByUserId(Long userId);

}
