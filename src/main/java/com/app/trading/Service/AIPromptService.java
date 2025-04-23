package com.app.trading.Service;

public interface AIPromptService {
	
	String generateAdvicePrompt(String riskProfile, String experienceLevel, String goal, String userQuestion);

}
