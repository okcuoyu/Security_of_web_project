# Secure Note-Taking Application (Spring Boot)

It is a secure backend application that allows users to register, log in, and manage their personal notes. It has been
hardened with advanced security features including session management, secure headers, and protected admin access.

## 📋 Project Overview

The application is built using **Spring Boot** and follows the MVC (Model-View-Controller) architecture. It uses *
*SQLite** for data storage and **Spring Security** for authentication and authorization.

### Key Features

* **User System:** Registration, Login, and Logout functionality.
* **Note Management:** Users can add, view, and edit their private notes.
* **Admin Panel:** Special dashboard for admins to view and manage users.
* **Database Migration:** Automatic table creation using **Flyway**.
* **Security:** Implements "App Hardening" techniques from Lab 13.

## 🛠️ Tech Stack

* **Java 17 & Spring Boot 3**
* **Spring Security 6**
* **Thymeleaf** (Frontend Engine)
* **SQLite** (Embedded Database)
* **Flyway** (Database Migration)
* **Maven** (Build Tool)

## 📂 Project Structure

The project follows a standard layered architecture:

```text
src/main
 ├─ java/com/example/demo
 │   ├─ controller/               
 │   │   ├─ AdminController.java
 │   │   ├─ NoteController.java
 │   │   └─ WebController.java
 │   ├─ dto/                      
 │   │   ├─ CreateUserRequest.java
 │   │   └─ NoteRequest.java
 │   ├─ model/                    
 │   │   ├─ Note.java
 │   │   └─ User.java
 │   ├─ repository/               
 │   │   ├─ NoteRepository.java
 │   │   └─ UserRepository.java
 │   ├─ security/                 
 │   │   ├─ AuthenticationEvents.java
 │   │   ├─ CustomLoginSuccessHandler.java
 │   │   └─ SecurityConfig.java
 │   ├─ service/                  
 │   │   ├─ CustomUserDetailsService.java
 │   │   ├─ NoteService.java
 │   │   └─ UserService.java
 │   ├─ DataInitializer.java      
 │   └─ Lab10Application.java     
 │
 └─ resources
     ├─ db/migration/             
     │   └─ V1__create_users_table.sql
     ├─ static/css/               
     │   └─ style.css
     ├─ templates/                
     │   ├─ admin/
     │   │   └─ users.html
     │   ├─ user/
     │   │   ├─ edit_note.html
     │   │   ├─ home.html
     │   │   └─ notes.html
     │   ├─ login.html
     │   └─ register.html
     └─ application.properties    