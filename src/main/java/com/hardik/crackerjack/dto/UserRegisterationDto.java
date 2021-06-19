package com.hardik.crackerjack.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class UserRegisterationDto {

	private final String emailId;
	private final String password;
	private final Integer countryId;

}
