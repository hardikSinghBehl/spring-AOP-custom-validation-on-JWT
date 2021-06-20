package com.hardik.crackerjack.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hardik.crackerjack.exception.DuplicateEmailIdException;
import com.hardik.crackerjack.exception.InvalidCountryIdException;
import com.hardik.crackerjack.exception.InvalidLoginCredentialsException;
import com.hardik.crackerjack.exception.dto.ExceptionResponseDto;

@ControllerAdvice
public class ExceptionResponseHandler {

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	@ExceptionHandler(DuplicateEmailIdException.class)
	public ResponseEntity<?> duplicateEmailIdExceptionHandler(DuplicateEmailIdException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ExceptionResponseDto.builder().status("Failure")
						.message("User with provided emailId already exists in the system")
						.timestamp(LocalDateTime.now()).build());
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	@ExceptionHandler(InvalidLoginCredentialsException.class)
	public ResponseEntity<?> invalidLoginCredentialsExceptionHandler(InvalidLoginCredentialsException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionResponseDto.builder().status("Failure")
				.message("Invalid login credentials provided").timestamp(LocalDateTime.now()).build());
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	@ExceptionHandler(InvalidCountryIdException.class)
	public ResponseEntity<?> invalidLoginCredentialsExceptionHandler(InvalidCountryIdException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ExceptionResponseDto.builder().status("Failure")
						.message("Invalid country id provided, no country with provided id exists in the system")
						.timestamp(LocalDateTime.now()).build());
	}

	@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> geenricExceptionHandler(Exception exception) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ExceptionResponseDto.builder().status("Failure")
				.message("Something went wrong").timestamp(LocalDateTime.now()).build());
	}

}
