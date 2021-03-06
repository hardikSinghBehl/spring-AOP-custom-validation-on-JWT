package com.hardik.crackerjack.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.crackerjack.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmailId(String emailId);

	Boolean existsByEmailId(String emailId);

	boolean existsByIdAndIsEnabled(UUID userId, boolean truth);

	boolean existsByIdAndCountryId(UUID userId, int indiaId);

}
