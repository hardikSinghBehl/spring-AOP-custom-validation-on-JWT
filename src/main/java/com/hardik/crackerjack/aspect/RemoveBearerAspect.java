package com.hardik.crackerjack.aspect;

import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class RemoveBearerAspect {

	private Integer headerArgumentPosition;

	@Around("execution(* com.hardik.crackerjack.controller.*.*(..))")
	public Object removeBearerStringFromHeader(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		log.info("Running Bearer Removal Task From Headers Method {}() in {}.class",
				proceedingJoinPoint.getSignature().getName(),
				proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName());

		Object[] modifiedArgs = proceedingJoinPoint.getArgs();

		CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();
		AtomicInteger count = new AtomicInteger(0);

		for (int i = 0; i < codeSignature.getParameterNames().length; i++) {
			if (codeSignature.getParameterNames()[i].equalsIgnoreCase("header")) {
				headerArgumentPosition = count.get();
				break;
			} else
				count.incrementAndGet();
		}

		if (headerArgumentPosition != null) {
			final var header = (String) proceedingJoinPoint.getArgs()[headerArgumentPosition];
			modifiedArgs[headerArgumentPosition] = header.replace("Bearer ", "");

			return proceedingJoinPoint.proceed(modifiedArgs);
		}

		return proceedingJoinPoint.proceed();
	}

}