package com.app.trading.Service;

import java.util.List;

import com.app.trading.Model.User;
import com.app.trading.Model.Withdrawal;

public interface WithdrawalService {
	
	Withdrawal requestyWithdrawal(Long amount,User user);
	Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception;
	List<Withdrawal> getUsersWithdrawalHistory(User user);
	List<Withdrawal> getAllWithdrawalRequest();


}