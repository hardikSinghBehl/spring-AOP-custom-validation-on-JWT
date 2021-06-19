package com.hardik.crackerjack.aspect;

import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import com.hardik.crackerjack.annotation.CheckIfFromIndia;
import com.hardik.crackerjack.repository.UserRepository;
import com.hardik.crackerjack.security.utility.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class CheckIfFromIndiaAspect {

	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	private Integer headerArgumentPosition;

	@Before("execution(* *.*(..)) && @annotation(checkIfFromIndia)")
	public void checkIfFromIndia(JoinPoint joinPoint, CheckIfFromIndia checkIfFromIndia) {
		log.info("Running India Country Check On Method {}() in {}.class", joinPoint.getSignature().getName(),
				joinPoint.getSignature().getDeclaringType().getSimpleName());

		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
		AtomicInteger count = new AtomicInteger(0);

		for (int i = 0; i < codeSignature.getParameterNames().length; i++) {
			if (codeSignature.getParameterNames()[i].equalsIgnoreCase("header")) {
				headerArgumentPosition = count.get();
				break;
			} else
				count.incrementAndGet();
		}

		if (headerArgumentPosition != null) {
			final var header = (String) joinPoint.getArgs()[headerArgumentPosition];
			final var userId = jwtUtils.extractUserId(header.replace("Bearer ", ""));
			if (!userRepository.existsByIdAndCountryId(userId, 1))
				throw new RuntimeException();
		}
	}

}