package com.hardik.crackerjack.bootstrap;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.hardik.crackerjack.entity.MasterCountry;
import com.hardik.crackerjack.repository.MasterCountryRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MasterCountryDataBootstrap {

	private final MasterCountryRepository masterCountryRepository;
	private final List<String> countryList = List.of("India", "Netherlands", "Australia", "Netherlands");

	@PostConstruct
	public void masterCountryDataBootstrapHandler() {
		for (int i = 0; i < countryList.size() - 1; i++) {
			final var country = new MasterCountry();
			country.setId(i);
			country.setName(countryList.get(i));
			masterCountryRepository.save(country);
		}
	}

}
