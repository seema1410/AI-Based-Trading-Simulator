package com.app.trading.Service;


import com.app.trading.Domain.PaymentMethod;
import com.app.trading.Model.PaymentOrder;
import com.app.trading.Model.User;
import com.app.trading.Response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
	
	PaymentOrder createOrder(User user,Long amount ,PaymentMethod paymentMethod);
	
	PaymentOrder getPaymentOrderById(Long id) throws Exception;
	
	Boolean ProccedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException ;
	
	
	
	PaymentResponse createStripePaymentLing(User user , Long amount , Long orderId) throws StripeException;

	PaymentResponse createRazorpayPaymentLing(User user, Long amount, Long orderId) throws RazorpayException;


}
