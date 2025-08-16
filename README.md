# 🎓 Student-Course Management System

A simple **Hibernate ORM** based Java application to manage Students, Courses, and their Enrollments.  
This project demonstrates **One-to-Many** and **Many-to-Many (via Enrollment entity)** mappings using Hibernate.

---

## 🚀 Features
- ➕ Add, Update, View, Delete **Students**
- ➕ Add, Update, View, Delete **Courses**
- ➕ Add, Update, View, Delete **Enrollments** (with enrollment date)
- 📑 Demonstrates Hibernate relationships:
  - `One-to-Many` → Student → Enrollment
  - `One-to-Many` → Course → Enrollment
  - `Many-to-Many` → Student ↔ Course (via Enrollment)
- 📌 Console menu for CRUD operations

---

## 🛠️ Tech Stack
- **Java 17**
- **Hibernate 7.x**
- **Jakarta Persistence (JPA)**
- **PostgreSQL / MySQL** (any relational DB)
- **Maven**

---

## 🗄️ Database Schema
```sql
CREATE TABLE student (
  student_id INT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  marks DOUBLE
);

CREATE TABLE course (
  course_id INT PRIMARY KEY,
  course_name VARCHAR(100) NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE enrollment (
  student_id INT,
  course_id INT,
  enrolled_date DATE,
  PRIMARY KEY(student_id, course_id),
  FOREIGN KEY(student_id) REFERENCES student(student_id),
  FOREIGN KEY(course_id) REFERENCES course(course_id)
);
