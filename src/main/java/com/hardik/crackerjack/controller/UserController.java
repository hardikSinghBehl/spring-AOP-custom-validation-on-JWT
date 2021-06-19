package com.hardik.crackerjack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.crackerjack.annotation.CheckIfEnabled;
import com.hardik.crackerjack.annotation.CheckIfFromIndia;
import com.hardik.crackerjack.dto.UserLoginRequestDto;
import com.hardik.crackerjack.dto.UserRegisterationDto;
import com.hardik.crackerjack.security.utility.JwtUtils;
import com.hardik.crackerjack.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtUtils jwtUtils;

	@PostMapping(value = "/user/auth/register")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Registers user in the system")
	public ResponseEntity<?> userRegisterationHandler(
			@RequestBody(required = true) final UserRegisterationDto userRegisterationDto) {
		return userService.register(userRegisterationDto);
	}

	@PostMapping(value = "/user/auth/login")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Returns JWT representing logged in user")
	public ResponseEntity<?> userLoginHandler(
			@RequestBody(required = true) final UserLoginRequestDto userLoginRequestDto) {
		return userService.login(userLoginRequestDto);
	}

	@CheckIfEnabled
	@CheckIfFromIndia
	@GetMapping(value = "/user/details")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Returns details of logged in user")
	public ResponseEntity<?> userDetailRetreivalHandler(
			@Parameter(hidden = true) @RequestHeader(required = true, name = "Authorization") final String header) {
		return userService.retreiveDetails(jwtUtils.extractUserId(header));
	}

}
