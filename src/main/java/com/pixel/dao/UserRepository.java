package com.pixel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixel.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
