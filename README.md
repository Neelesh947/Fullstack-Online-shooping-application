# Fullstack-Online-shooping-application

#**Project Overview: Online Shopping Platform (Microservices Architecture)**
Building a microservices-based online shopping platform with full-fledged support for multi-role interactions and enterprise-grade features such as authentication, authorization, notifications, and containerized deployments.

🧱 **System Architecture & Technology Stack**
🔐**Authentication & Authorization**
**Keycloak** is used as the **central identity and access management system.**
Keycloak stores user credentials and handles:
**Authentication** (login)
**Authorization** (role-based access)
**Token generation** (OAuth2/OpenID Connect)
The Keycloak instance runs inside a **Docker container**, and it uses **PostgreSQL** as its database (also containerized).

👥 **User Roles Defined in Keycloak**
**super_admin**: Manages the entire platform, all stores, and user accounts.
**store_manager**: Owns and manages their specific store. Can create products and handle orders.
**customer**: Browses products, places orders, and receives notifications.
**delivery_agent**: Handles logistics and delivery, possibly tracks assigned orders.

📣 **Notification Service**
A **dedicated notification microservice** is responsible for all user-facing communication (email, possibly SMS or push).
Events that trigger notifications:
**User registratio**n: Welcome or verification email.
**Login attempts**: Alert/summary of sign-in events.
**Forgot password**: Password reset instructions.
**Order events**: Order received, dispatched, delivered.
**Daily summaries**: Store managers receive a summary of daily orders., etc

📦 **Technologies used:**
**Kafka** (message broker) handles event-driven communication.
**Zookeeper** manages Kafka's distributed coordination.
**Both Kafka and Zookeeper are running in Docker containers.**

🔧 **Microservices in the System**
I am organizing the backend as loosely coupled microservices, allowing for scalability, maintainability, and independent deployment.

✅** Currently Implemented Services:**
1. **Keycloak Service**
Manages users, authentication, and role-based authorization.
Runs in Docker and connects to PostgreSQL.

2.** Notification Service**
Listens to Kafka topics.
Sends emails based on event triggers.
Uses external mail server (possibly SMTP) configured in Docker.

3. **Shopping Service (In Progress)**
This is the core e-commerce backend for store and product management.
Exposes REST APIs for product creation, updates, retrieval, and deletion.
Each store manager can:
Create products.
Upload product images.
View their own products.
Receive notifications when a new order is placed.
Get daily order summary notifications.

**Planned Microservices:**
**Order Service**: Handles order placement, tracking, and status management.
**Payment Service (optional)**: Processes and verifies payments.
**Delivery Service**: Manages logistics and delivery agents.

🗄️ **Databases**
**MySQL** is used for core business services (e.g., shopping, orders).
**PostgreSQL** is dedicated to Keycloak.
All databases are containerized using Docker.

**Containerization with Docker**
All services, including:
Keycloak
MySQL
PostgreSQL
Kafka
Zookeeper
Notification Service
Shopping Service
Are running as Docker containers, orchestrated for local development and deployment.

Docker Compose is likely used to manage multi-container setup.


