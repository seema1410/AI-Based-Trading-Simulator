package com.app.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.QuizResponse;
import com.app.trading.Model.User;

public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long>{
	
	  // Optional: Find all quiz responses by a user
	 List<QuizResponse> findByUser(User user);

	    // Optional: Latest quiz by user (for AI prompts)
	    QuizResponse findTopByUserOrderBySubmittedAtDesc(User user);
	}


