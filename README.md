# Chain of Responsibility Validator in Kotlin

A Kotlin-idiomatic implementation of the Chain of Responsibility pattern for validation, complete with an example Spring service and unit tests.

## Features
- Fluent API for chaining validation steps
- Supports boolean conditions and object-based validations
- Custom error handling via `onError`
- Throws `IllegalArgumentException` on first validation failure
- Easy integration with Spring Boot services

## Installation

Add the module or dependency to your `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.example:validatepatterns:1.0.0")
}
```

## Usage

### Validator

```kotlin
ChainOfResponsibilityValidator()
    // Error if username is blank
    .next({ user.username.isBlank() }, "Username must not be blank")
    // Error if email does not contain '@'
    .next({ !user.email.contains("@") }, "Email must contain '@'")
    // Error if age is outside 18..99
    .next({ user.age !in 18..99 }, "Age must be between 18 and 99")
    // Custom error logging
    .onError { msg -> log.warn("Validation failed: {}", msg) }
    // Throws IllegalArgumentException if any check failed
    .throwIfError()
```

### Example Spring Service

```kotlin
@Service
class UserValidationService {

    fun validateUser(user: UserValidationService.UserDto): Boolean {
        return try {
            ChainOfResponsibilityValidator()
                .next({ user.username.isBlank() }, "Username must not be blank")
                .next({ !user.email.contains("@") }, "Email must contain '@'")
                .next({ user.age !in 18..99 }, "Age must be between 18 and 99")
                .onError { msg -> log.warn("Validation failed for user {}: {}", user, msg) }
                .throwIfError()
            true
        } catch (_: IllegalArgumentException) {
            false
        }
    }
}
```

### Running Tests

Unit tests are located under `src/test/kotlin/...`.  
Run them with:
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
│   │                   └── UserValidationService.kt
│   └── test
│       └── kotlin
│           └── com
│               └── example
│                   └── validation
│                       ├── ChainOfResponsibilityValidatorTest.kt
│                       └── UserValidationServiceTest.kt
```

## License

This project is licensed under the MIT License.
