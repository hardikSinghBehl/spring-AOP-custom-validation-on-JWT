package com.hardik.crackerjack.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.crackerjack.dto.CountryDto;
import com.hardik.crackerjack.repository.MasterCountryRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CountryController {

	private final MasterCountryRepository masterCountryRepository;

	@GetMapping("/countries")
	public ResponseEntity<List<CountryDto>> countryListRetreivalHandler() {
		return ResponseEntity.ok(masterCountryRepository.findAll().parallelStream()
				.map(country -> CountryDto.builder().id(country.getId()).name(country.getName()).build())
				.collect(Collectors.toList()));
	}

}
