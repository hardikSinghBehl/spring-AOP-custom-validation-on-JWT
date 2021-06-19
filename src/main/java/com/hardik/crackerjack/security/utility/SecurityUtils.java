package com.hardik.crackerjack.security.utility;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtils {

	public User convert(com.hardik.crackerjack.entity.User user) {
		return new User(user.getEmailId(), user.getPassword(), List.of());
	}

}