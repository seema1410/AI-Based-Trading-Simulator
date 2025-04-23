package com.app.trading.Controller;

import com.app.trading.Model.QuizResponse;
import com.app.trading.Model.User;
import com.app.trading.Service.QuizService;
import com.app.trading.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired private QuizService quizService;
    @Autowired private UserService userService;

    @PostMapping("/submit")
    public ResponseEntity<QuizResponse> submitQuiz(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String riskProfile,
            @RequestParam String experienceLevel,
            @RequestParam String goal) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt);
        QuizResponse response = quizService.saveQuizResponse(
            user.getId(), 
            riskProfile, 
            experienceLevel, 
            goal
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<QuizResponse> getLatestQuiz(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(quizService.getLatestResponse(user.getId()));
    }
}