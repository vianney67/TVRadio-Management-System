# TV & Radio Management System

A distributed Java application for managing TV and Radio station operations, including programming, employees, advertisements, and financial reporting. The project is split into a Client-Server architecture using Java RMI for communication.

## Project Structure

This folder contains two main sub-projects:

1.  **TVRadioMgtSystemServer26438**: The backend server responsible for data persistence and business logic.
2.  **TVRadioMgtSystemClient26438**: The frontend client application providing a graphical user interface (GUI) for users.

## Technology Stack

### Backend (Server)
- **Java RMI**: Used for distributed communication between client and server.
- **Hibernate (JPA)**: Used for Object-Relational Mapping (ORM) and database interaction.
- **MySQL/MariaDB**: Recommended database for persistence (configured via `hibernate.cfg.xml`).
- **ActiveMQ**: Integrated for notification services.

### Frontend (Client)
- **Java Swing**: Used for building the desktop graphical user interface.
- **Java RMI Client**: Connects to the server to invoke remote services.

## Key Features

- **User Authentication**: Secure login with role-based access control (Admin/Employee).
- **Dashboard**: Specialized dashboards for Admins and Employees.
- **Management Modules**:
    - **Channel Management**: Manage TV and Radio channels.
    - **Program Scheduling**: Plan and schedule programs across channels.
    - **Employee Management**: Track employee details and assignments.
    - **Advertisement Management**: Handle ad placements and scheduling.
    - **Financial Reporting**: Track expenses and generate financial reports.
    - **Equipment Tracking**: Manage station equipment.

## Setup and Running

### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- MySQL Database server.
- Apache ActiveMQ (optional, for notifications).

### Database Setup
1. Create a MySQL database (e.g., `tvradio_db`).
2. Update the database connection settings in `TVRadioMgtSystemServer26438/src/hibernate.cfg.xml`.

### Running the Server
1. Navigate to the `TVRadioMgtSystemServer26438` directory.
2. Compile and run `controller.TVRadioServer`.
3. The server will initialize the database and bind services to the RMI registry on port `3001` by default.

### Running the Client
1. Navigate to the `TVRadioMgtSystemClient26438` directory.
2. Ensure the server is running.
3. Compile and run `view.LoginForm`.
4. Login using the default credentials:
    - **Admin**: `admin` / `admin123`
    - **Employee**: `employee` / `123`

## Configuration
- Client connection settings (host/port) can be found in `util.Config.java`.
- Server port can be overridden via command-line argument `--port=XXXX`.

