package com.hardik.crackerjack.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class UserRegisterationDto {

	@NotBlank(message = "email-id must not be empty")
	@Email(message = "email-id must be a valid email address")
	private final String emailId;

	@NotBlank(message = "password must not be empty")
	private final String password;

	@NotNull(message = "country-id must not be null")
	private final Integer countryId;

}
