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



**Frontend Overview: Angular + TypeScript**
I am building a role-based Angular application that interacts with your microservices backend, including Keycloak for authentication and Kafka-triggered notifications via the notification service.

🧩 **Frontend Architecture Breakdown**
✅ **Key Technologies Used**
**Angular** (modular SPA framework)
**TypeScript** (strongly typed JavaScript superset)
**Angular Router** (for page navigation)
**HttpClient** (for REST API integration)
**Angular Forms** (for reactive form management)
**Keycloak JavaScript Adapter or angular-oauth2-oidc** (for auth integration)
**ngx-toastr / Angular Material Snackbar** (for UI notifications)
**Bootstrap / Angular Material** (for UI styling)

🔐** Authentication with Keycloak**
**Integration Flow:**
Angular app redirects to Keycloak login page.
After successful login, user receives a JWT token.
Token is stored in memory or localStorage.
On each API request, the token is sent in the Authorization header (Bearer <token>).
Angular guards restrict or allow access to routes based on user roles.

**Tools**:
Use keycloak-angular or angular-oauth2-oidc for easy integration.
Create AuthService, AuthGuard, and RoleGuard.

👥 **Role-Based Modules and UI**
You should divide the frontend app into feature modules based on user roles.
1. **Customer Module**
🛍 Browse product catalog
🔍 View product details and images
🛒 Add to cart / checkout
📦 View past orders and order status
✉️ Receive notification alerts (order placed, shipped)
🔐 Register / login / reset password

2. **Store Manager Module**
📋 Dashboard (products count, daily orders)
➕ **Create product form**
📂 List all products (with image previews)
🖊 **Edit product form**
🗑 **Delete product**
📈 **Daily order summary**
📩 **Real-time or email notifications when an order is received** 
**🖼 Upload and delete images**
**🧮 View orders placed on their products**

3. **Super Admin Module (if applicable)**
🧑‍💼 **User management** (assign roles, view list)
🏪 **Store management** (approve/reject stores)
⚙ **Platform settings/config**
🛡 **Access audit logs or statistics**

4. **Delivery Agent Module** (if planned)
🚚 **List of assigned orders**
✅ **Update order delivery status**
🗺 Map integration for route (optional)

🔄 **Service Layer Integration**
Create Angular services to connect to backend APIs using HttpClient.
Examples:
ProductService – CRUD for products
OrderService – Place & fetch orders
NotificationService – Poll for or display messages
AuthService – Handles login, logout, token

Services should handle:
Error handling (with HttpInterceptor)
Token injection into headers
Retry logic (if needed)

🔔 **Notification Handling**
Notifications triggered by backend events (Kafka → Notification Service) will likely arrive via email or optionally WebSocket/push.
In the Angular frontend:
For **email-based** notifications: show toast/snackbar when triggered, or display past notifications in a UI panel.
For **real-time** support (future): integrate with WebSocket or SSE (Server-Sent Events).

🧰 **Development Tools & Feature**
**Environment-specific** configuration (environment.ts for dev, prod)
**Lazy loading** of feature modules (store-manager, customer, etc.)
**Route guards** to protect views per role
**Interceptors** for adding tokens and handling errors globally
**Loading indicators** (spinner while fetching data)

**Suggested Folder Structure**

src/
├── app/
│   ├── core/               → Singleton services, guards, interceptors
│   ├── shared/             → Shared components, pipes, directives
│   ├── auth/               → Login, logout, register, auth service
│   ├── store-manager/      → Product management, order tracking
│   ├── customer/           → Product catalog, order history
│   ├── admin/              → Admin controls (optional)
│   └── delivery-agent/     → Delivery panel (optional)
├── assets/                 → Static assets
├── environments/           → Dev/prod config

📈 **Future Enhancements**
**Internationalization** (i18n) for multi-language support
**PWA** support for offline capabilities
**Unit** testing with Jasmine/Karma
**E2E** testing with Cypress or Protractor
**Angular** SSR (Server-Side Rendering) for SEO (optional)

🧾 **Summary**
Building a robust and scalable Angular + microservices online shopping platform where:

Keycloak secures users and roles.
Kafka and a notification service handle asynchronous communications.
Products and orders are managed in isolated services.
Store managers and customers interact via a rich Angular UI.
Everything runs inside Docker, including databases, services, and Kafka ecosystem.
