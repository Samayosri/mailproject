# Mail Project

![Demo Video](INSERT_VIDEO_LINK)

A web-based email program built using Java Spring Boot for the backend and React for the frontend. This project demonstrates a functional email server application with essential features like email manipulation, attachments, and contact management.

## Features

- **Mail Manipulation**
  - Inbox, Trash (auto-deletes after 30 days), Sent, Drafts, and Custom Folders.
  - Filters for sorting emails by subject, sender, etc.
  - Search and sort emails by attributes like date, importance, and more.
  - Bulk operations: delete, move, etc.

- **Attachments**
  - Add, delete, and view attachments for emails.

- **Contact Management**
  - Add, edit, delete, and search contacts with multiple email support.

- **Additional Functionalities**
  - Pagination for email lists.
  - User-friendly UI for managing folders and emails.
## Design Patterns Used
- **Factory Design Pattern**
  - Creates objects based on mail and contact criteria.

- **Singleton Design Pattern**
  - Ensures that only one instance of a class exists throughout the application.
  Dependency injection in Spring Boot helps manage these singletons.

- **Builder Design Pattern**
  - Constructs complex objects (e.g., DTOs) step by step to allow for different representations.

- **Command Design Pattern**
  - Encapsulates operations like moving emails, sending, and saving drafts into separate command classes for modularity and scalability.

- **Prototype Design Pattern**
  - Used for cloning entities like MailEntity and AttachmentEntity for deep copy operations.

- **Filter Design Pattern**
  - Filters emails and contacts based on multiple criteria simultaneously.

- **Facade Design Pattern**
  - Simplifies complex systems by providing a unified interface (e.g., for filtering, sorting, and paginating emails).

- **Proxy Design Pattern**
  - Adds security checks for login and sign-up processes, separating validation logic from core functionalities.
  

## How to Run

1. Navigate to the backend directory (`Mail`) and ensure all dependencies are installed.
2. Start the PostgreSQL server and create a database named `email`. Configure the credentials in the `application.properties` file.
3. Alternatively, use the `recovery.txt` file to connect to an in-memory H2 database.
4. Run the backend:
   ```bash
   ./mvnw clean
   ./mvnw install
5.Navigate to the frontend directory (`Mail`):
   ```bash
    npm install
    npm run dev
    
