package com.hardik.crackerjack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.crackerjack.entity.MasterCountry;

@Repository
public interface MasterCountryRepository extends JpaRepository<MasterCountry, Integer> {

}
