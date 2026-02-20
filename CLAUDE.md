# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

CiolaFlix BE is a streaming service backend built with Spring Boot 4.0.3 and Java 25. It uses a dual-database architecture with the Blackout authentication framework handling user authentication separately from business data.

## Build and Development Commands

### Building and Running
```bash
# Build the project
mvn clean install

# Run the application (requires PostgreSQL running on localhost:5432)
mvn spring-boot:run

# Run tests
mvn test

# Run a specific test class
mvn test -Dtest=CiolaflixBeApplicationTests

# Run a specific test method
mvn test -Dtest=ClassName#methodName

# Package the application
mvn package
```

### Database Setup
The application expects PostgreSQL running on `localhost:5432` with:
- Database name: `ciolaflix`
- Username: `root`
- Password: `root`

Hibernate is configured with `ddl-auto: update`, so schemas will be created automatically on startup.

## Architecture

### Dual Database System

The application uses two separate PostgreSQL databases:

1. **Auth Database** (managed by Blackout framework)
   - Stores authentication accounts and credentials
   - Accessed through `AuthAccountRepo` from Blackout
   - Configured via `blackout.base-url` in application.yaml

2. **Primary Database** (business data)
   - Stores domain entities (CiolaMan, Liked, WatchLater, ContinueWatching)
   - Accessed through custom repositories in `xyz.fdt.ciolaflixbe.repo`
   - Configured via `spring.datasource.*`

This separation allows authentication to be managed independently of business logic.

### Layer Architecture

```
controller/  →  service/  →  repo/  →  model/
   REST API     Business    Data      JPA
   endpoints    logic       access    entities
```

- **Controllers**: Handle HTTP requests/responses (`@RestController`)
- **Services**: Contain business logic and transaction management
- **Repositories**: Spring Data JPA interfaces for data access
- **Models**: JPA entities with relationships

### Key Domain Model

`CiolaMan` is the central user entity that links to the auth system via `authAccountId`:
- One user record per authentication account
- Many-to-many relationships with `Liked` and `WatchLater` content
- One-to-many relationship with `ContinueWatching` (for tracking progress)

### Authentication & Authorization

**Blackout Framework Integration**:
- JWT-based authentication with configurable token expiration
- Public endpoints: configured in `blackout.filterchain.allowed` (e.g., `/ciola/signup`)
- Protected endpoints: require valid JWT token
- Current user accessible via `CurrentUserService<BlackoutUserPrincipal>` bean

**JWT Configuration** (in application.yaml):
- Access token: 15 minutes
- Refresh token: 1 hour (no remember) or 30 days (remember me)

### Configuration

**Blackout Configuration** (`BlackoutConfig.java`):
- Must define the `CurrentUserService` bean with `AuthAccountRepo`
- This enables accessing the authenticated user in business logic

**application.yaml Structure**:
- `server.servlet.context-path: /api` - All endpoints are prefixed with `/api`
- `blackout.parent.*` - Configures primary database for business entities
- `blackout.openapi.paths-to-match` - Controls Swagger documentation scope
- `blackout.cors.*` - CORS settings (currently allows all origins)

### API Structure

- Base path: `/api`
- Example endpoint: `POST /api/ciola/signup` (public)
- Swagger/OpenAPI docs available when running (auto-configured by Blackout)

### Package Structure

```
xyz.fdt.ciolaflixbe/
├── CiolaflixBeApplication.java    # Spring Boot main class
├── config/                         # Configuration classes
│   └── BlackoutConfig.java         # Blackout framework setup
├── controller/                     # REST controllers
├── service/                        # Business logic
├── repo/                          # JPA repositories
├── model/                         # JPA entities
├── dto/                           # Data transfer objects
└── security/                      # Security-related implementations
```

### Custom Maven Repository

The project uses a custom GitHub repository for the Blackout dependency:
- Repository: `https://maven.pkg.github.com/trinex-it/blackout`
- Requires appropriate GitHub authentication to fetch this dependency

### Technology Stack

- **Java 25** with Spring Boot 4.0.3
- **Spring Web MVC** for REST APIs
- **Spring Data JPA** with Hibernate for ORM
- **PostgreSQL** as the database
- **Lombok** to reduce boilerplate (configured as annotation processor)
- **Blackout Framework 1.0.3** for authentication/authorization
- **OpenAPI/Swagger** for API documentation
