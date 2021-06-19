package com.hardik.crackerjack.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hardik.crackerjack.dto.UserLoginRequestDto;
import com.hardik.crackerjack.dto.UserRegisterationDto;
import com.hardik.crackerjack.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<?> register(final UserRegisterationDto userRegisterationDto) {
		return null;
	}

	public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto) {
		return null;
	}

}
