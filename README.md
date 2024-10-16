# LibraryManagementSystem


A simple **Library Management System** built using **Java** and **PostgreSQL**. This project allows you to manage books, users, and basic library operations such as adding, removing, issuing, and returning books. It is a console-based application for now, with the possibility to extend it into a full-fledged web-based application later.

## Table of Contents
1. [Features](#features)
2. [Project Structure](#project-structure)
3. [Technologies Used](#technologies-used)
4. [Database Setup](#database-setup)
5. [How to Run](#how-to-run)
6. [Future Enhancements](#future-enhancements)

## Features

- **Add Books**: Add new books to the library's collection.
- **Remove Books**: Remove books from the library.
- **Register Users**: Register users to the library system.
- **Issue Books**: Issue books to users.
- **Return Books**: Return issued books.
- **Show Available Books**: View a list of books that are currently available (not fully issued).

## Project Structure

```
LibraryManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── chandumummadi/
│   │   │           └── library/
│   │   │               ├── model/
│   │   │               │   ├── Book.java
│   │   │               │   └── User.java
│   │   │               ├── service/
│   │   │               │   └── LibraryService.java
│   │   │               ├── util/
│   │   │               │   └── DatabaseConnection.java
│   │   │               └── Main.java
│   └── resources/
│       └── application.properties
├── test/
├── target/
├── .gitignore
├── pom.xml
└── README.md
```

- **model/**: Contains the data models for `Book` and `User`.
- **service/**: The business logic layer, containing `LibraryService.java`, where the core operations (like issuing, returning, and managing books) are performed.
- **util/**: Utility classes, including `DatabaseConnection.java`, which manages the connection to the PostgreSQL database.
- **resources/**: Contains configuration files like `application.properties` for database setup.
- **Main.java**: The main entry point where the console interface is handled.

## Technologies Used

- **Java**: The core language for building the application.
- **PostgreSQL**: A relational database used to store information about books and users.
- **Maven**: For managing project dependencies and building the project.
- **JDBC**: For interacting with the PostgreSQL database using Java.
- **IntelliJ IDEA**: The integrated development environment (IDE) used for development.

## Database Setup

1. Install **PostgreSQL** if you haven't already.
2. Create a new database for the library system:

   ```sql
   CREATE DATABASE library_management;
   ```

3. Create the `books` and `users` tables using the following SQL commands:

   ```sql
   CREATE TABLE books (
       id SERIAL PRIMARY KEY,
       title VARCHAR(100) NOT NULL,
       author VARCHAR(100) NOT NULL,
       isbn VARCHAR(20) NOT NULL,
       genre VARCHAR(50),
       total_copies INT NOT NULL,
       borrowed_copies INT NOT NULL DEFAULT 0
   );

   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       user_id VARCHAR(20) NOT NULL,
       name VARCHAR(100) NOT NULL,
       contact_info VARCHAR(100)
   );
   ```

4. Update your `application.properties` file in `src/main/resources/` with the database connection details:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/library_management
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   ```

   Replace `yourusername` and `yourpassword` with your PostgreSQL credentials.

## How to Run

1. **Clone the repository**:

   ```bash
   git clone https://github.com/chandumummadi/LibraryManagementSystem.git
   ```

2. **Navigate to the project directory**:

   ```bash
   cd LibraryManagementSystem
   ```

3. **Build the project** using Maven:

   ```bash
   mvn clean install
   ```

4. **Run the application**:

   ```bash
   mvn exec:java -Dexec.mainClass="com.chandumummadi.library.Main"
   ```

5. The application will start, and you can interact with it via the console to perform various operations (like adding books, issuing books, etc.).

## Future Enhancements

- **Switch to Spring Boot**: Convert the project to a Spring Boot-based application with a RESTful API back-end.
- **Web Interface**: Build a React-based front-end to interact with the back-end via REST APIs.
- **User Authentication**: Add a login system for admin and regular users.
- **Improved Reporting**: Add features to generate reports for issued books, overdue books, etc.
- **Notifications**: Implement a system for sending notifications when a book is overdue.

## License

This project is open-source and available under the MIT License.

```bash
### How to Customize

1. **GitHub URL**: git clone https://github.com/chandumummadi/LibraryManagementSystem.git.
2. **Database Details**: Update any specific database details or connection settings you may have in your `application.properties` file.
3. **Additional Features**: Feel free to add any other features you’ve implemented or plan to implement.

Let me know if you need any adjustments to this README or further customization for your project!
```
