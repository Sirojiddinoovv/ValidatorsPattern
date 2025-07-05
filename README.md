# Validation Utilities in Kotlin

A collection of idiomatic Kotlin validators:  
- **Chain of Responsibility Validator**  
- **Monad-based Validator**

## Features
- **Chain of Responsibility**: Fluent API for chaining validation steps with custom error handling.  
- **Monad Validator**: Functional style validation monad for concise input checks.  
- Easy integration with Spring Boot services.  
- Unit tests included.

## Installation

Add the module or dependency to your `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.example:validation-utils:1.0.0")
}
```

## Usage

### 1. Chain of Responsibility Validator

```kotlin
ChainOfResponsibilityValidator()
    // Error if username is blank
    .next({ user.username.isBlank() },   "Username must not be blank")
    // Error if email does not contain '@'
    .next({ !user.email.contains("@") }, "Email must contain '@'")
    // Error if age is outside 18..99
    .next({ user.age !in 18..99 },       "Age must be between 18 and 99")
    // Hook for custom error logging
    .onError { msg -> log.warn("Validation failed: {}", msg) }
    // Throws IllegalArgumentException if any check failed
    .throwIfError()
```

Example Spring service:

```kotlin
@Service
class UserValidationService {
    fun validateUser(user: UserDto): Boolean {
        return try {
            ChainOfResponsibilityValidator()
                .next({ user.username.isBlank() },   "Username must not be blank")
                .next({ !user.email.contains("@") }, "Email must contain '@'")
                .next({ user.age !in 18..99 },       "Age must be between 18 and 99")
                .onError { msg -> log.warn("Validation failed for user {}: {}", user, msg) }
                .throwIfError()
            true
        } catch (_: IllegalArgumentException) {
            false
        }
    }
}
```

### 2. MonadValidator

```kotlin
// Create a validator monad for the input object
MonadValidator.of(input)
    .validate({ it.isNotEmpty() },         "Input must not be empty")
    .validate({ it.length > 5 },           "Input length must be > 5")
    .validate({ it == EXPECTED_VALUE },    "Input does not match expected")
```

Example Spring service:

```kotlin
@Service
class InputValidationService {
    fun validateInput(input: String): Boolean {
        return try {
            MonadValidator.of(input)
                .validate({ it.isNotEmpty() },      "Input must not be empty")
                .validate({ it.length > 5 },        "Input length must be > 5")
                .validate({ it == EXPECTED_VALUE }, "Input does not match expected")
            true
        } catch (_: CommonException) {
            false
        }
    }
}
```

## Running Tests

Unit tests are under `src/test/kotlin`. Run with:
```bash
./gradlew test
```

## Project Structure

```
.
├── src
│   ├── main
│   │   └── kotlin
│   │       └── com
│   │           └── example
│   │               └── validation
│   │                   ├── ChainOfResponsibilityValidator.kt
│   │                   └── MonadValidator.kt
│   └── test
│       └── kotlin
│           └── com
│               └── example
│                   └── validation
│                       ├── ChainOfResponsibilityValidatorTest.kt
│                       └── MonadValidatorTest.kt
```

## License

MIT
