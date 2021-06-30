# Spring-AOP for custom validation on JWT via annotations
### POC showing approach to remove boilerplate validations using custom annotations and wiring in business logic inside aspects that execute before methods annotated with created annotation.
### Problem

We want certain APIs to execute only if the logged-in user's account is enabled in the database i.e is_enabled column is set to true (user-id claim extracted from JWT received in headers)

### Solution

1. Make dummy annotation with name CheckIfEnabled
```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckIfEnabled {
}
```
2. Create Aspect using @Before containing business logic for validation that runs before all controller methods annotated with @CheckIfEnabled

[LINK to class](https://github.com/hardikSinghBehl/spring-AOP-custom-validation-on-JWT/blob/main/src/main/java/com/hardik/crackerjack/aspect/CheckIfEnabledAspect.java)

* We fetch the JWT from the controller method argument (We specifically name it header, can be placed in a constant class)
* We extract the user-id from the JWT
* We check if account of the user is enabled, if not we throw an exception which stops the controller method from being executed

```
  @Before("execution(* *.*(..)) && @annotation(checkIfEnabled)")
	public void checkIfEnabled(JoinPoint joinPoint, CheckIfEnabled checkIfEnabled) {
		log.info("Running Is Enabled Check On Method {}() in {}.class", joinPoint.getSignature().getName(),
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
			if (!userRepository.existsByIdAndIsEnabled(userId, true))
				throw new RuntimeException();
		}
	}
```
3. We annotate the controllers with above annotation to apply the validation thus saving bolierplate/repeated if else statements inside the service layer
```
	@CheckIfEnabled
	@GetMapping(value = "/user/details", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Returns details of logged in user")
	public ResponseEntity<?> userDetailRetreivalHandler(
			@Parameter(hidden = true) @RequestHeader(required = true, name = "Authorization") final String header) {
		return userService.retreiveDetails(jwtUtils.extractUserId(header));
	}
```
##### Consider starring the repo if found helpful

### BONUS (Remove 'Bearer ' from JWT using Spring-AOP)

It is a common approach to remove 'Bearer ' using .replace() or .substring inside the service layer and extract the required claim with an utiliy method.
We can remove the 'Bearer ' String from the JWT using @Around aspect that modifies the arguments recieved by a method

[Link to aspect class](https://github.com/hardikSinghBehl/spring-AOP-custom-validation-on-JWT/blob/main/src/main/java/com/hardik/crackerjack/aspect/RemoveBearerAspect.java)

## Local Setup

* Install Java 16
* Install Maven

Recommended way is to use [sdkman](https://sdkman.io/) for installing both maven and java

Run the below commands in the core

```
mvn clean install
```

```
mvn spring-boot:run
```
server port is configured to 9090 (can be changed in application.properties file)

Go to the below url to view swagger-ui (API docs)

```
http://localhost:9090/swagger-ui.html
```

