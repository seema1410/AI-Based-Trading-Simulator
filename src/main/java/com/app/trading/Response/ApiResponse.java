package com.app.trading.Response;

import lombok.Data;

@Data
public class ApiResponse {
	
	private String message ;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
