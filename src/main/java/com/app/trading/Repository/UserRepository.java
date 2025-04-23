package com.app.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trading.Model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByEmail(String email);

}
