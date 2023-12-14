package com.school.service;

import java.util.Optional;

import com.school.model.User;

public interface UserService {

	 Integer saveUser(User user);

	Optional<User> findByUsername(String username);
}
