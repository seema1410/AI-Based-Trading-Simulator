package com.app.trading.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.trading.Model.QuizResponse;
import com.app.trading.Model.User;
import com.app.trading.Repository.QuizResponseRepository;
import com.app.trading.Repository.UserRepository;

@Service
public class QuizServiceImpl implements QuizService{
	

    @Autowired
    private QuizResponseRepository quizResponseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public QuizResponse saveQuizResponse(Long userId, String riskProfile, String experienceLevel, String goal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        QuizResponse response = new QuizResponse();
        response.setUser(user);
        response.setRiskProfile(riskProfile);
        response.setExperienceLevel(experienceLevel);
        response.setGoal(goal);
        response.setSubmittedAt(LocalDateTime.now());

        return quizResponseRepository.save(response);
    }

    @Override
    public QuizResponse getLatestResponse(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return quizResponseRepository.findTopByUserOrderBySubmittedAtDesc(user);
    }

}
