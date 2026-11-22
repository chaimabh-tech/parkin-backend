# Park'In — Backend API

**Java · Spring Boot · Spring Security · JWT · MongoDB**

Backend of **Park'In**, an academic full-stack parking reservation application. This REST API supports user accounts, parking listings, reservations and complaints, and connects to an Angular frontend.

🎬 **[Watch the Park'In demo on YouTube](https://youtu.be/itPu4OKL4gM)**  
💻 **[Angular frontend repository](https://github.com/chaimabh-tech/parkin-backend)**

## Key features

- User registration and login with Spring Security and JWT
- Password hashing and user/role management
- Parking listing creation, retrieval and location-based search
- Reservation endpoints and parking capacity updates
- Complaint (*réclamation*) submission and retrieval
- Twilio-based SMS integration in the original implementation

> **Project status:** Portfolio / graduation project. Certain booking and access-control logic requires additional validation and hardening before production use.

## Technology stack

| Area | Technology |
| --- | --- |
| Language | Java 8 |
| Backend | Spring Boot 2.2.4, Spring Web |
| Security | Spring Security, JWT, BCrypt |
| Persistence | Spring Data MongoDB, MongoDB |
| Integration | REST APIs, Twilio |
| Build | Maven / Maven Wrapper |
| Packaging | Dockerfile included |

## Architecture

```text
Angular web app
       │ REST / JSON
       ▼
Spring Boot API ─── Spring Security / JWT
       │
       ├── Authentication & user accounts
       ├── Parking management
       ├── Reservations
       └── Complaints / SMS integration
       │
       ▼
     MongoDB
```

## Repository structure

```text
src/main/java/com/bezkoder/spring/jwt/mongodb/
├── controllers/
├── models/
├── payload/
├── repository/
└── security/
src/main/resources/application.properties
pom.xml
Dockerfile
mvnw
mvnw.cmd
```

## Local setup

**Prerequisites:** Java 8, MongoDB, and Maven (or the Maven Wrapper).

```bash
git clone https://github.com/chaimabh-tech/parkin-backend.git
cd parkin-backend
```

Configure your local MongoDB connection and JWT signing secret in `src/main/resources/application.properties`. Use the environment variables for you own  passwords. The SMS and email integrations are enabled, so the configuration for  their credentials is private as well.

Start the application:

```bash
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

The default Spring Boot port is `8080` unless changed in configuration.

## Example API capabilities

The codebase includes controllers for authentication, parking management, reservations and complaints. See the controller mappings in `src/main/java/.../controllers/` for the exact endpoints and request formats. API authentication and authorization should be reviewed before deploying publicly.

## Security and limitations

- Do not treat the original authorization rules as production-ready; some endpoints were configured with broad access permissions.
- Review reservation availability/capacity handling and error handling before live use.
- This project demonstrates implementation skills; it is **not** advertised as a production booking service.

## Related project

- [Park'In frontend — Angular](https://github.com/chaimabh-tech/parkin-frontend)
- [Application demo on YouTube](https://youtu.be/itPu4OKL4gM)

**Project by Chaima Ben Hsouna** — Engineering graduation project (Park'In / Cloud & DevOps capstone).
