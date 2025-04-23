package com.app.trading.Controller;



import com.app.trading.Exception.UserNotFoundException;
import com.app.trading.Model.QuizResponse;
import com.app.trading.Model.User;
import com.app.trading.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired private UserService userService;
    @Autowired private QuizService quizService;
    @Autowired private AIPromptService aiPromptService;
    @Autowired private GeminiAIService geminiAIService;

   
    
    @GetMapping("/advice")
    public ResponseEntity<?> getAdvice(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            QuizResponse response = quizService.getLatestResponse(user.getId());

            if (response == null) {
                return ResponseEntity.badRequest().body("Please complete quiz first");
            }

            // ✅ Pass user.getFullName() for personalization
            String prompt = aiPromptService.generateAdvicePrompt(
                response.getRiskProfile(),
                response.getExperienceLevel(),
                response.getGoal(),
             
                user.getFullName() // 👈 Add this line
            );

            String advice = geminiAIService.getAIAdvice(prompt);
            return ResponseEntity.ok(advice);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting advice");
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(
            @RequestHeader("Authorization") String jwt,
            @RequestBody String question) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            QuizResponse response = quizService.getLatestResponse(user.getId());
            
            String prompt = aiPromptService.generateAdvicePrompt(
                response.getRiskProfile(),
                response.getExperienceLevel(),
                response.getGoal(),
                question
               
            );

            String answer = geminiAIService.getAIAdvice(prompt);
            return ResponseEntity.ok(answer);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing question");
        }
    }
}