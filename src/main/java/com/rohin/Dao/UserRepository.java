package com.rohin.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohin.Models.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	User findByUsername(String username);
	User findByToken(String token);
}
