package com.hardik.crackerjack.exception.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class ExceptionResponseDto {

	private final String status;
	private final String message;
	private final LocalDateTime timestamp;

}
