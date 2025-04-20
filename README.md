# 📘 Tasky

**Tasky** is a secure and extensible task management REST API built using **Kotlin**, **Spring Boot**, and **MongoDB**. It features user authentication with **JWT tokens**, task CRUD operations, validation, and refresh token handling — all wrapped in a clean and modular architecture.

---

## 🚀 Features

- ✅ User registration & login with password hashing
- 🔐 JWT-based authentication (access & refresh tokens)
- 🧾 Task creation, listing, and deletion
- 🗑️ Auto-expiring refresh tokens (via MongoDB TTL index)
- 📦 Input validation with granular error messages
- ⚙️ Centralized exception handling
- 📁 Clean architecture with layered separation
- 🛡️ Secure endpoints protected by filters and token validation

---

## 🛠 Tech Stack

| Tech | Description |
|------|-------------|
| **Kotlin** | Main programming language |
| **Spring Boot** | Web framework |
| **Spring Security** | Authentication and security filters |
| **MongoDB** | NoSQL database for users, tasks, and refresh tokens |
| **JWT (JJWT)** | Token-based authentication |
| **Gradle (KTS)** | Build and dependency management |
| **Jakarta Validation** | Input validation annotations |

---

## 📡 API Endpoints

### 🔐 Auth

| Method | Endpoint         | Description                          |
|--------|------------------|--------------------------------------|
| POST   | `/auth/register` | Register a new user                  |
| POST   | `/auth/login`    | Login and receive access & refresh tokens |
| POST   | `/auth/refresh`  | Get a new token pair using refresh token |

### ✅ Tasks

| Method | Endpoint         | Description                |
|--------|------------------|----------------------------|
| GET    | `/tasks`         | Get all tasks (authenticated) |
| POST   | `/tasks`         | Create a task              |
| DELETE | `/tasks/{id}`    | Delete task by ID          |

---

## 🔐 Auth Flow

- Passwords are **hashed with bcrypt**
- On login, a **JWT access token** and **refresh token** are issued
- Refresh tokens are:
    - Stored in MongoDB (hashed)
    - Auto-expire via TTL indexing
    - Single-use (deleted on refresh)

---

## 🧪 Validation Rules

### Email
- Required
- Must match format: `example@domain.com`

### Password
- Required
- 6–10 characters
- At least 1 uppercase, 1 lowercase, and 1 special character
- No spaces allowed

---

## 🐳 Docker

### 📁 Required Setup
Make sure to create a `.env` file in the root of your project with the following content:

```env
MONGO_URI=mongodb://localhost:27017/tasky
JWT_SECRET=your_jwt_secret_key
```

---

## 📁 Project Stucture
```
src/
└── main/
    ├── kotlin/
    │   └── com/
    │       └── khcodings/
    │           └── tasky/
    │               ├── TaskyApplication.kt
    │
    │               ├── application/
    │               │   ├── auth/
    │               │   │   ├── dto/
    │               │   │   │   ├── AuthRequest.kt
    │               │   │   │   ├── RefreshRequest.kt
    │               │   │   │   └── TokenPair.kt
    │               │   │   └── AuthService.kt
    │               │   └── tasks/
    │               │       ├── dto/
    │               │       │   ├── TaskMapper.kt
    │               │       │   ├── TaskRequest.kt
    │               │       │   └── TaskResponse.kt
    │               │       └── TaskService.kt
    │
    │               ├── domain/
    │               │   ├── task/
    │               │   │   ├── Task.kt
    │               │   │   └── TaskRepository.kt
    │               │   └── user/
    │               │       ├── User.kt
    │               │       └── UserRepository.kt
    │
    │               ├── infrastructure/
    │               │   ├── config/
    │               │   │   └── SecurityConfig.kt
    │               │   ├── persistence/
    │               │   │   └── refresh/
    │               │   │       ├── RefreshToken.kt
    │               │   │       └── RefreshTokenRepository.kt
    │               │   └── security/
    │               │       ├── CustomAuthEntryPoint.kt
    │               │       ├── HashEncoder.kt
    │               │       ├── JwtAuthFilter.kt
    │               │       └── JwtService.kt
    │
    │               ├── shared/
    │               │   ├── interfaces/
    │               │   │   └── ValidationGroups.kt
    │               │   └── response/
    │               │       ├── ActionResponse.kt
    │               │       ├── ApiResponse.kt
    │               │       ├── ErrorResponse.kt
    │               │       └── SuccessResponse.kt
    │
    │               └── web/
    │                   ├── advice/
    │                   │   └── exceptions/
    │                   │       ├── AuthException.kt
    │                   │       └── GlobalExceptionHandler.kt
    │                   ├── auth/
    │                   │   └── AuthController.kt
    │                   └── task/
    │                       └── TasksController.kt

```
---

