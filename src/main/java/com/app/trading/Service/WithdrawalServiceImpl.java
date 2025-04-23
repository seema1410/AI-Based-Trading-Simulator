package com.app.trading.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Domain.WithdrawalStatus;
import com.app.trading.Model.User;
import com.app.trading.Model.Withdrawal;
import com.app.trading.Repository.WithdrawalRepository;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {
	@Autowired
	private WithdrawalRepository withdrawalRepository;

	@Override
	public Withdrawal requestyWithdrawal(Long amount, User user) {
		Withdrawal withdrawal=new Withdrawal();
		withdrawal.setAmount(amount);
		withdrawal.setUser(user);
		withdrawal.setStatus(WithdrawalStatus.PENDING);
		return withdrawalRepository.save(withdrawal);
	}

	@Override
	public Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception {
		Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
		if(withdrawal.isEmpty()) {
			throw new Exception ("withdrawal not found");
		}
		Withdrawal withdrawal1 = withdrawal.get();
		withdrawal1.setDate(LocalDateTime.now());
		if(accept) {
			withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
		}else {
			withdrawal1.setStatus(WithdrawalStatus.PENDING);
		}
		return withdrawalRepository.save(withdrawal1);
	}

	@Override
	public List<Withdrawal> getUsersWithdrawalHistory(User user) {
		return withdrawalRepository.findByUserId(user.getId());
	}

	@Override
	public List<Withdrawal> getAllWithdrawalRequest() {
		return withdrawalRepository.findAll();
	}

}
