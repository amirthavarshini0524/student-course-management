package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="enrollment")
public class Enrollment {
    @EmbeddedId
    private EnrollmentId enrollmentId;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name="course_id")
    private Course course;

    @Column(name="enrolledDate")
    private LocalDate enrollment_date;

    public Enrollment(){}
    public Enrollment(Student student, Course course, LocalDate enrollment_date) {
        this.student = student;
        this.course = course;
        this.enrollment_date = enrollment_date;
        this.enrollmentId = new EnrollmentId(student.getStudentId(), course.getCourseId());
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(LocalDate enrollment_date) {
        this.enrollment_date = enrollment_date;
    }
}
