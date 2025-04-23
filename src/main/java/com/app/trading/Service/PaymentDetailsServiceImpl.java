package com.app.trading.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Model.PaymentDetails;
import com.app.trading.Model.User;
import com.app.trading.Repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService{
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Override
	public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
			String bankName, User user) {
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setAccountHolderName(accountHolderName);
		paymentDetails.setAccountNumber(accountNumber);
		paymentDetails.setIfsc(ifsc);
		paymentDetails.setBankName(bankName);
		paymentDetails.setUser(user);
		
		return paymentDetailsRepository.save(paymentDetails);
		
	}

	@Override
	public PaymentDetails getUsersPaymentsDetails(User user) {
		return paymentDetailsRepository.findByUserId(user.getId());
	}

}
