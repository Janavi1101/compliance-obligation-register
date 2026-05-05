# Compliance Obligation Register

A Spring Boot backend application for managing compliance obligations with JWT authentication, CRUD APIs, Redis caching, email notifications, file upload/download, scheduler-based overdue alerts, Swagger documentation, and unit testing.

---

## Features

- User registration and login with JWT
- Compliance CRUD APIs
- Pagination support
- DTO validation
- Global exception handling
- Redis caching for selected GET operations
- Email notifications using JavaMailSender
- Scheduler for overdue compliance alerts
- File upload/download with UUID filenames
- Swagger/OpenAPI documentation
- H2 database for development
- JUnit 5 and Mockito tests

---

## Architecture

```text
+-------------------+
| Client / Swagger  |
+---------+---------+
          |
          v
+-------------------+
| REST Controllers  |
+---------+---------+
          |
          v
+-------------------+
| Service Layer     |
| Business Logic    |
+---------+---------+
          |
          v
+-------------------+
| Repository Layer  |
| Spring Data JPA   |
+---------+---------+
          |
          v
+-------------------+
| Database          |
| H2 / PostgreSQL   |
+-------------------+

Extra Components:
+-------------------+      +-------------------+
| Redis Cache       |      | Email Service     |
+-------------------+      +-------------------+

+-------------------+
| File Storage      |
| uploads/ folder   |
+-------------------+