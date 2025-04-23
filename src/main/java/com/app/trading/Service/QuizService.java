package com.app.trading.Service;

import com.app.trading.Model.QuizResponse;

public interface QuizService {
	
	QuizResponse saveQuizResponse(Long userId, String riskProfile, String experienceLevel, String goal);
    QuizResponse getLatestResponse(Long userId);
    

}
