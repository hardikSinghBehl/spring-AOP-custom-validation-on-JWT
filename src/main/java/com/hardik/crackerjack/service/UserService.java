package com.hardik.crackerjack.service;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hardik.crackerjack.dto.UserDto;
import com.hardik.crackerjack.dto.UserLoginRequestDto;
import com.hardik.crackerjack.dto.UserRegisterationDto;
import com.hardik.crackerjack.entity.User;
import com.hardik.crackerjack.exception.DuplicateEmailIdException;
import com.hardik.crackerjack.exception.InvalidCountryIdException;
import com.hardik.crackerjack.exception.InvalidLoginCredentialsException;
import com.hardik.crackerjack.repository.MasterCountryRepository;
import com.hardik.crackerjack.repository.UserRepository;
import com.hardik.crackerjack.security.utility.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final MasterCountryRepository masterCountryRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;

	private Boolean existsByEmailId(final String emailId) {
		return userRepository.existsByEmailId(emailId);
	}

	private Boolean existsByCountryId(final Integer countryId) {
		return masterCountryRepository.existsById(countryId);
	}

	public ResponseEntity<?> register(final UserRegisterationDto userRegisterationDto) {

		if (existsByEmailId(userRegisterationDto.getEmailId()))
			throw new DuplicateEmailIdException();

		if (existsByCountryId(userRegisterationDto.getCountryId()))
			throw new InvalidCountryIdException();

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

	public ResponseEntity<?> login(final UserLoginRequestDto userLoginRequestDto) {
		final var user = userRepository.findByEmailId(userLoginRequestDto.getEmailId())
				.orElseThrow(() -> new RuntimeException());

		if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
			throw new InvalidLoginCredentialsException();

		final var response = new JSONObject();
		response.put("JWT", jwtUtils.generateToken(user));
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> retreiveDetails(final UUID userId) {
		final var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
		return ResponseEntity.ok(
				UserDto.builder().createdAt(user.getCreatedAt()).emailId(user.getEmailId()).id(user.getId()).build());
	}

}
