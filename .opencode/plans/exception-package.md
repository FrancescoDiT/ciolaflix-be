# Exception Package Implementation Plan

## Goal
Create an `exception` package with a `CiolaException` base class, custom exceptions for each error scenario, an `ErrorResponse` DTO, and a `GlobalExceptionHandler`.

## Files to Create

All under `src/main/java/xyz/fdt/ciolaflixbe/exception/`:

### 1. CiolaException.java (abstract base)
- Extends `RuntimeException`
- Fields: `HttpStatus status` (via Lombok `@Getter`)
- Constructor: `(String message, HttpStatus status)`

### 2. Custom Exceptions (all extend CiolaException)

| Class | HTTP Status | Purpose |
|-------|-------------|---------|
| `UserNotFoundException` | 404 | CiolaMan not found |
| `MediaNotFoundException` | 404 | Media not found in local DB |
| `TmdbMediaNotFoundException` | 404 | Media not found in TMDB |
| `MediaAlreadyLikedException` | 409 | Duplicate like |
| `InvalidMediaTypeException` | 400 | Invalid mediaType string |
| `TmdbConnectionException` | 502 | TMDB network failure |
| `SignupException` | 400 | Signup failures |

Each has a single constructor: `(String message)` that calls `super(message, HttpStatus.XXX)`.

### 3. ErrorResponse.java (DTO)
- `@Getter @Builder`
- Fields: `int status`, `String error`, `String message`

### 4. GlobalExceptionHandler.java
- `@RestControllerAdvice`
- Handlers:
  - `CiolaException` → uses exception's status
  - `MethodArgumentNotValidException` → 400 with field error details
  - `IllegalArgumentException` → 400
  - `Exception` → 500 fallback (generic message)

## Not Changed
- No services or controllers are modified
- Exceptions are created and ready to be swapped in later
