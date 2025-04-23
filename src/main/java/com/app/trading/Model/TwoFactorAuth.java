package com.app.trading.Model;


import com.app.trading.Domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
	private boolean Enabled = false;
	private VerificationType sendTo;
	public boolean isEnabled() {
		return Enabled;
	}
	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}
	public VerificationType getSendTo() {
		return sendTo;
	}
	public void setSendTo(VerificationType sendTo) {
		this.sendTo = sendTo;
	}
	
	

}
