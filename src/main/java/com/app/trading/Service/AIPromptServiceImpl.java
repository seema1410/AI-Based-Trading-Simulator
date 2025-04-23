

package com.app.trading.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AIPromptServiceImpl implements AIPromptService {

    @Value("${ai.prompt.advice}")
    private String adviceTemplate;

    @Value("${ai.prompt.question}")
    private String questionTemplate;

    @Override
    public String generateAdvicePrompt(
            String riskProfile, 
            String experienceLevel, 
            String goal, 
            String userQuestion) {
        
        if(userQuestion == null || userQuestion.isBlank()) {
            return String.format(adviceTemplate, riskProfile, experienceLevel, goal);
        }
        return String.format(questionTemplate, riskProfile, experienceLevel, goal, userQuestion);
    }
}
/*
package com.app.trading.Service;

import org.springframework.stereotype.Service;

@Service
public class AIPromptServiceImpl implements AIPromptService {

   /* @Override
    public String generateAdvicePrompt(
            String riskProfile,
            String experienceLevel,
            String goal,
            String userQuestion,
            String fullName) {

        StringBuilder prompt = new StringBuilder();

        // Greeting based on user's name
        if (fullName != null && !fullName.isBlank()) {
            prompt.append("Hi ").append(fullName).append("! ");
        } else {
            prompt.append("Hello crypto explorer! ");
        }

        // Personalization based on user's profile
        prompt.append("We’ve analyzed your quiz and here's your tailored advice. ");
        prompt.append("As a ").append(experienceLevel.toLowerCase())
              .append(" with a ").append(riskProfile.toLowerCase())
              .append(" risk appetite, aiming to ").append(goal.toLowerCase()).append(", ");

     // Friendly and motivating guidance
        prompt.append("Hey, let's kick off with some great coins, simple strategies, and smart risk tips! ");
        prompt.append("Avoid mistakes and build your crypto confidence!");

        return prompt.toString();
    }*/
	
	/*
	@Override
	public String generateAdvicePrompt(
	        String riskProfile,
	        String experienceLevel,
	        String goal,
	        String userQuestion,
	        String fullName) {

	    StringBuilder prompt = new StringBuilder();

	    // Greeting based on user's name
	    if (fullName != null && !fullName.isBlank()) {
	        prompt.append("Hi ").append(fullName).append("! ");
	    } else {
	        prompt.append("Hello crypto explorer! ");
	    }

	    // Personalization based on user's profile
	    prompt.append("We’ve analyzed your quiz and here's your tailored advice. ");
	    prompt.append("As a ").append(experienceLevel.toLowerCase())
	          .append(" with a ").append(riskProfile.toLowerCase())
	          .append(" risk appetite, aiming to ").append(goal.toLowerCase()).append(", ");

	    // Friendly and motivating guidance
	    prompt.append("let’s kick off with some great coins, simple strategies, and smart risk tips!");

	    // If user has a question, include it in the prompt
	    if (userQuestion != null && !userQuestion.isBlank()) {
	        prompt.append(" You asked: ").append(userQuestion);
	    }

	    return prompt.toString();
	}
*/