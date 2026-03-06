# Book Store Management System 

A desktop-based Library and Book Store Management application built with Java and JavaFX. The project is based on `SOLID`, object-oriented programming, relational databases, and enterprise-level software architecture.

## Technologies Used

* **Java 21**: Core programming language.
* **JavaFX**: Graphical user interface (GUI).
* **MySQL**: Relational database management system.
* **Gradle**: Build automation and dependency management.
* **JUnit 5**: Testing framework.


## Workflows & Role-Based Access

The system enforces Role-Based Access Control (RBAC). Functionality is heavily restricted based on the logged-in user's role.

### Authentication & Security
Passwords are encrypted using SHA-256 before database storage. Includes real-time input validation.

![Invalid Email Validation]

<img width="832" height="620" alt="Screenshot 2026-03-06 at 22 49 48" src="https://github.com/user-attachments/assets/7c822536-4bc5-4ea2-91b7-fa0f36d8e9ce" />

![Successful Registration]

<img width="832" height="620" alt="Screenshot 2026-03-06 at 22 50 06" src="https://github.com/user-attachments/assets/0df2000a-7149-412f-b259-609cb2c5777d" />

![Login Screen]

<img width="832" height="620" alt="Screenshot 2026-03-06 at 23 04 43" src="https://github.com/user-attachments/assets/b30814c8-b47e-4de0-a3ef-d00c841d57fd" />

![Encrypted Passwords in DB]

<img width="972" height="611" alt="Screenshot 2026-03-06 at 22 54 47" src="https://github.com/user-attachments/assets/9f4beb78-1633-4410-9a80-25b96eacb297" />


### Administrator Role
Administrators access a dedicated dashboard, manage user roles, and delete accounts.

![Admin Dashboard]

<img width="892" height="640" alt="Screenshot 2026-03-06 at 23 10 29" src="https://github.com/user-attachments/assets/0abccd38-aee0-436e-ae43-409223a85849" />

![User Administration Initial]

<img width="832" height="620" alt="Screenshot 2026-03-06 at 23 11 00" src="https://github.com/user-attachments/assets/b3b7e755-b45f-4e8e-897d-d5ad431b6d91" />

![Role Changed to Admin]

<img width="720" height="506" alt="Screenshot 2026-03-06 at 23 11 36" src="https://github.com/user-attachments/assets/5e85ce9a-0ae7-436e-b9c8-601931e1cf2a" />

![Role Changed to Employee]

<img width="720" height="506" alt="Screenshot 2026-03-06 at 23 11 54" src="https://github.com/user-attachments/assets/f81bcafb-c486-42fd-a85d-14d00ceeb599" />

![User Administration Updated]

<img width="720" height="506" alt="Screenshot 2026-03-06 at 23 12 15" src="https://github.com/user-attachments/assets/5acb1911-52a5-4552-958a-eb8cde3a6f02" />

![User Deleted]

<img width="720" height="506" alt="Screenshot 2026-03-06 at 23 12 24" src="https://github.com/user-attachments/assets/24174061-42fd-4aa2-90e2-f2f45e819281" />

![User Administration Final]

<img width="720" height="506" alt="Screenshot 2026-03-06 at 23 12 28" src="https://github.com/user-attachments/assets/bbae9b9a-90c6-41d3-943e-da9fcdc543a6" />


### Employee Role
Employees manage library inventory, add books, and process sales to specific customers.

![Employee View Data Entry]

<img width="962" height="690" alt="Screenshot 2026-03-06 at 23 17 14" src="https://github.com/user-attachments/assets/55f1de4a-d1eb-4a88-bc84-abb51982b9a0" />

![Book Added Success]

<img width="847" height="575" alt="Screenshot 2026-03-06 at 23 18 00" src="https://github.com/user-attachments/assets/d7cfca14-9b04-4f8f-a9ff-cd7bbeb3479d" />

![Employee View Book Added]

<img width="847" height="575" alt="Screenshot 2026-03-06 at 23 18 12" src="https://github.com/user-attachments/assets/7c1c3ecd-5c27-4ef1-9838-930b4ecc6de9" />

![Sell Book Quantity Selection]

<img width="847" height="575" alt="Screenshot 2026-03-06 at 23 18 21" src="https://github.com/user-attachments/assets/29a63cb6-13f3-4d27-a891-89a0d80278be" />

![Select Customer for Sale]

<img width="832" height="620" alt="Screenshot 2026-03-06 at 23 23 39" src="https://github.com/user-attachments/assets/ed7e9af3-6554-40fc-9f2b-29ebdb33ab74" />

![Sale Complete Success]

<img width="718" height="507" alt="Screenshot 2026-03-06 at 23 24 02" src="https://github.com/user-attachments/assets/554d31f8-8c7d-4568-be94-809117c2758a" />

![Employee View Updated Stock]

<img width="851" height="576" alt="Screenshot 2026-03-06 at 23 24 20" src="https://github.com/user-attachments/assets/0c56ecfe-eea5-4c79-919c-e4a0e3aebb44" />

### Customer Role
Customers have a restricted, read-only view of the library inventory.

![Customer Read-Only View]

<img width="720" height="506" alt="Screenshot 2026-03-06 at 23 15 19" src="https://github.com/user-attachments/assets/1423ee55-902a-40cb-96a8-94b2fcdb0daf" />


## Architecture and Design Principles

The codebase focuses on clean code practices, architectural patterns, and SOLID principles for scalability and testability.

### 1. Layered Architecture
* **Presentation Layer (Controllers)**: Manages views and user inputs.
* **Business Logic Layer (Services)**: Contains core rules (e.g., stock validation).
* **Data Access Layer (Repositories)**: Handles direct database interactions.

### 2. Design Patterns
* **Repository Pattern**: Abstracts database operations. Services do not contain SQL queries, making the implementation easily interchangeable.
* **Factory Pattern**: Centralizes object creation (e.g., `SQLTableCreationFactory` for dynamic schemas, `DatabaseConnectionFactory`).
* **Dependency Injection**: Dependencies are injected via constructors, ensuring modularity and easier mocking for Unit Testing.

### 3. SOLID Principles
* **Single Responsibility Principle (SRP)**: Each class has one focus (e.g., `Bootstrap` strictly handles database initialization).
* **Open/Closed Principle (OCP)**: The system relies on interfaces and constants, making it easy to extend (e.g., adding new roles) without modifying core logic.


## Setup and Installation

1. Clone the repository.
2. Ensure Java 21 and MySQL are installed.
3. Create a MySQL user `root` with password `root`.
4. Run `Bootstrap.main()` to create schemas (`library`, `test_library`), tables, and default roles.
5. Build and run using Gradle: `./gradlew run`.
