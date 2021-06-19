package com.hardik.crackerjack.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hardik.crackerjack.dto.UserLoginRequestDto;
import com.hardik.crackerjack.dto.UserRegisterationDto;
import com.hardik.crackerjack.entity.User;
import com.hardik.crackerjack.repository.UserRepository;
import com.hardik.crackerjack.security.utility.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;

	private Boolean existsByEmailId(final String emailId) {
		return userRepository.existsByEmailId(emailId);
	}

	public ResponseEntity<?> register(final UserRegisterationDto userRegisterationDto) {

		if (existsByEmailId(userRegisterationDto.getEmailId()))
			throw new RuntimeException();

		final var user = new User();
		user.setEmailId(userRegisterationDto.getEmailId());
		user.setPassword(passwordEncoder.encode(userRegisterationDto.getPassword()));
		user.setCountryId(userRegisterationDto.getCountryId());
		user.setIsEnabled(true);
		userRepository.save(user);

		final var response = new JSONObject();
		response.put("Message", "User Created Successfully");
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto) {
		final var user = userRepository.findByEmailId(userLoginRequestDto.getEmailId())
				.orElseThrow(() -> new RuntimeException());

		if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
			throw new RuntimeException();

		final var response = new JSONObject();
		response.put("JWT", jwtUtils.generateToken(user));
		return ResponseEntity.ok(response.toString());
	}

}
