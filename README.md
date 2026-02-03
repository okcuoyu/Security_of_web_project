# 🔒 Secure Note Taking Application (Lab 14)

This is a secure backend application built with **Spring Boot MVC** that allows users to register, log in, and manage
their personal notes.

The project focuses on **Application Security Hardening**, implementing defenses against common vulnerabilities ,
enforcing strict **Data Isolation**, and using **Automated Security Testing**.

---

## 📋 Project Overview

The application follows the **Model-View-Controller (MVC)** architecture. It utilizes **Spring Security** for
authentication/authorization and **Thymeleaf** for server-side rendering.

### 🛡️ Key Security Features

* **🔐 Authentication & Hashing:**
    * **BCrypt Hashing:** Passwords are hashed (Strength 10) via `UserService` before storage.
    * **Session Security:** Uses `JSESSIONID` cookies (HttpOnly) with automatic **Session Fixation Protection** (ID
      rotation upon login).
    * **Custom Handlers:** Implements `CustomLoginSuccessHandler` for role-based redirection.

* **🚫 Authorization & Data Isolation:**
    * **Strict Data Isolation:** Users can **ONLY** access notes belonging to their specific `user_id`. Cross-access is
      blocked at the Service/Repository layer.
    * **Role-Based Access Control (RBAC):**
        * `/admin/**` -> Accessible only by ADMIN role.
        * `/notes/**` -> Accessible only by Authenticated Users.
        * Public -> `/login`, `/register`, `/css/**`.

* **✅ Input Validation & Error Handling:**
    * **DTO Validation:** `CreateUserRequest` and `NoteRequest` use `@NotBlank` / `@Size`.
    * **Manual Controller Validation:** `NoteController` implements manual checks for semantic errors (e.g., negative
      IDs like `/notes/-1/edit`) returning a safe **HTTP 400 Bad Request**.
    * **Safe Error Pages:** Stack traces are suppressed to prevent information leakage.

* **⚙️ App Hardening:**
    * **Security Headers:** `X-Frame-Options`, `X-Content-Type-Options`, `X-XSS-Protection`.
    * **Secure Logging:** `AuthenticationEvents` logs attempts, but **passwords are never logged**.

---

## 🛠️ Tech Stack

* **Java:** 17
* **Framework:** Spring Boot 3
* **Security:** Spring Security 6
* **Frontend:** Thymeleaf
* **Database:** Relational DB (H2/SQLite) with JPA
* **Migration:** Flyway (`db.migration`)
* **Build Tool:** Maven

---

## 📂 Project Structure

Based on the current source code organization:

```text
src/main/java/com/example/demo
 ├─ controller/
 │   ├─ AdminController.java     
 │   ├─ NoteController.java      
 │   └─ WebController.java       
 │
 ├─ dto/
 │   ├─ CreateUserRequest.java   
 │   └─ NoteRequest.java        
 │
 ├─ model/
 │   ├─ Note.java                
 │   └─ User.java              
 │
 ├─ repository/
 │   ├─ NoteRepository.java      
 │   └─ UserRepository.java      
 │
 ├─ security/
 │   ├─ AuthenticationEvents.java 
 │   ├─ CustomLoginSuccessHandler.java
 │   └─ SecurityConfig.java      
 │
 ├─ service/
 │   ├─ CustomUserDetailsService.java
 │   ├─ NoteService.java         
 │   └─ UserService.java         
 │
 └─ Lab10Application.java         

src/main/resources
 ├─ db/migration/
 │   └─ V1__create_users_table.sql
 ├─ static/css/
 │   └─ style.css
 └─ templates/
     ├─ admin/
     │   ├─ home.html
     │   └─ users.html
     └─ user/
         ├─ access-denied.html
         ├─ login.html
         ├─ register.html

src/test/java/com/example/demo/service
 ├─ Lab10ApplicationTests.java
 ├─ SecurityTests.java 
 └─ UserServiceTest.java

🚀 How to Run & Test
1. Run Tests (Security Gatekeeper)
Run the automated test suite to verify security rules and logic.

Bash
mvn clean test
Executes SecurityTests, UserServiceTest, and checks Application Context.

2. Start the Application
Bash
mvn spring-boot:run
3. Usage
URL: http://localhost:8080

Admin Access: Login with an admin account to view /admin/users.

User Access: Register a new user to manage private notes at /notes.