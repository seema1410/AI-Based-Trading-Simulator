package com.app.trading.Response;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String jwt;
	private boolean status;
	private String message;
	private boolean twoFactorAuthEnabled;
	private String session;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    public boolean isTwoFactorAuthEnabled() {
        return twoFactorAuthEnabled;
    }

    public void setTwoFactorAuthEnabled(boolean twoFactorAuthEnabled) {
        this.twoFactorAuthEnabled = twoFactorAuthEnabled;
    }
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
}
