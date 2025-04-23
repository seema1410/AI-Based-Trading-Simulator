package com.app.trading.Service;


import com.app.trading.Model.Order;
import com.app.trading.Model.User;
import com.app.trading.Model.Wallet;

public interface WalletService {
	Wallet getUserWallet(User user);
	Wallet addBalance(Wallet wallet,Long money);
	Wallet findWalletById(Long id) throws Exception;
	Wallet WalletToWalletTransfer(User sender , Wallet receiverWallet ,Long amount  ) throws Exception;
	Wallet payOrderPayment(Order order , User user) throws Exception;
	


}