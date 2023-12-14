package com.Student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Student.entity.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Integer> {
	
	public Screen findByName(String name);
}

