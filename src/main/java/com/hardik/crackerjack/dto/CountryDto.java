package com.hardik.crackerjack.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class CountryDto {

	private final Integer id;
	private final String name;

}
