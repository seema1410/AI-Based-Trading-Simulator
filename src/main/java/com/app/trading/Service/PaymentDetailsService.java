package com.app.trading.Service;

import com.app.trading.Model.PaymentDetails;
import com.app.trading.Model.User;

public interface PaymentDetailsService {
	
	public PaymentDetails addPaymentDetails(String accountNumber,
			String accountHolderName,
			String ifsc,
			String bankName,
			User user);
	
	public PaymentDetails getUsersPaymentsDetails(User user);

}