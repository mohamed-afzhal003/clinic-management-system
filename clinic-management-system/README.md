# Clinic Management System

A web-based clinic management system to digitize patient registration, appointment scheduling, and doctor consultation records.

## Features

- **Patient Management** – register, search, update, and view patient records (name, age, contact details, medical history)
- **Doctor Management** – maintain doctor profiles with specialization and available hours
- **Appointment Booking** – book appointments between patients and doctors, with **automatic conflict detection** to prevent double-booking a doctor's time slot
- Responsive front-end built with HTML5, CSS3, and JavaScript
- Server-side validation (Java/Spring) plus client-side validation (JavaScript) on form submission

## Tech Stack

| Layer          | Technology                              |
|----------------|------------------------------------------|
| Front-end      | HTML5, CSS3, JavaScript, Thymeleaf       |
| Back-end       | Java 17, Spring Boot 3, Spring MVC       |
| Data access    | Spring Data JPA / Hibernate              |
| Database       | H2 (in-memory, default) / MySQL (optional) |
| Build tool     | Maven                                    |

## Project Structure

```
src/main/java/com/clinic/
  ├── model/          # Patient, Doctor, Appointment entities
  ├── repository/     # Spring Data JPA repositories
  ├── service/        # Business logic (appointment conflict detection)
  └── controller/      # Spring MVC controllers

src/main/resources/
  ├── templates/       # Thymeleaf HTML views
  ├── static/css/       # Stylesheet
  ├── static/js/        # Client-side validation
  └── application.properties
```

## How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.8+

### Steps

1. Clone the repository:
   ```
   git clone https://github.com/<your-username>/clinic-management-system.git
   cd clinic-management-system
   ```

2. Run the application:
   ```
   mvn spring-boot:run
   ```

3. Open your browser and go to:
   ```
   http://localhost:8080
   ```

The app runs with an in-memory H2 database by default, so no database setup is required to try it out. Sample data isn't pre-loaded — add a doctor and a patient first, then book an appointment.

### Using MySQL instead of H2

Edit `src/main/resources/application.properties`: comment out the H2 config block and uncomment the MySQL block, then update the username/password to match your local MySQL setup.

## Key Design Decisions

- **Appointment conflict detection**: before saving a new appointment, `AppointmentService` checks whether the selected doctor already has a booking at the same date and time, and rejects the booking with a clear error message if so.
- **Layered architecture**: controllers handle HTTP requests, services hold business rules, repositories handle persistence — keeping the codebase organized and testable.
- **Validation**: required fields (patient name, phone, doctor specialization, etc.) are validated both server-side (Jakarta Bean Validation) and client-side (JavaScript) for a smoother user experience.

## Future Improvements

- Role-based login for doctors/admin staff
- Billing and invoice generation
- Email/SMS appointment reminders
- Pagination for large patient lists
