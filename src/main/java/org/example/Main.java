package org.example;
import static java.lang.System.out;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try (SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Enrollment.class)
                .buildSessionFactory()) {

            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                out.println("\n ***** School - Course Management *****");
                out.println("1.Add Student records");
                out.println("2.Add Course records");
                out.println("3.Add Enrollment records");
                out.println("4.View All Student records");
                out.println("5.View All Course records");
                out.println("6.view All Enrollment records");
                out.println("7.Update Student records");
                out.println("8.Update Course records");
                out.println("9.update Enrollment records");
                out.println("10.Delete Student records");
                out.println("11.Delete Course records");
                out.println("12.Delete Enrollment records");
                out.println("13.Exit");

                choice = sc.nextInt();
                sc.nextLine();

                Session session = sf.openSession();
                Transaction tx = session.beginTransaction();
                switch (choice) {
                    case 1 -> {
                        out.println("Enter student id :");
                        int id = sc.nextInt();
                        sc.nextLine();
                        out.println("Enter student name :");
                        String name = sc.nextLine();
                        out.println("Enter student email :");
                        String email = sc.nextLine();
                        out.println("Enter student marks :");
                        double marks = sc.nextDouble();

                        Student student = new Student(id, name, email, marks);
                        session.persist(student);
                        tx.commit();
                        out.println("Student record added.");
                    }
                    case 2 -> {
                        out.println("Enter course id :");
                        int id = sc.nextInt();
                        sc.nextLine();
                        out.println("Enter course name :");
                        String name = sc.nextLine();
                        out.println("Enter course description :");
                        String description = sc.nextLine();

                        Course course = new Course(id, name, description);
                        session.persist(course);
                        tx.commit();
                        out.println("Course record added.");
                    }
                    case 3 -> {
                        out.println("Enter student id :");
                        int sid = sc.nextInt();
                        out.println("Enter course id :");
                        int cid = sc.nextInt();

                        Student student = session.find(Student.class, sid);
                        Course course = session.find(Course.class, cid);

                        if (student != null && course != null) {
                            Enrollment enrollment = new Enrollment(student, course, LocalDate.now());
                            student.getEnrollments().add(enrollment);
                            course.getEnrollments().add(enrollment);
                            session.persist(enrollment);
                            tx.commit();
                            out.println("Enrollment record added.");
                        } else {
                            out.println("Student or course not found.");
                            tx.rollback();
                        }
                    }
                    case 4 -> {
                        session.createQuery("from Student",Student.class)
                                .list()
                                .forEach(System.out::println);
                        tx.commit();
                    }
                    case 5 -> {
                        session.createQuery("from Course",Course.class)
                            .list()
                            .forEach(out::println);
                        tx.commit();
                    }

                    case 6-> {
                        session.createQuery("from Enrollment",Enrollment.class)
                            .list()
                            .forEach(out::println);
                        tx.commit();
                    }
                    case 7->{
                        out.println("Enter student id to update :");
                        int student_id = sc.nextInt();

                        out.println("1.update student name");
                        out.println("2.update student email");
                        out.println("3.update student marks");
                        int updateChoice = sc.nextInt();
                        sc.nextLine();

                        int updated = 0;
                        switch (updateChoice){
                            case 1->{
                                out.println("Enter student name :");
                                String newName = sc.nextLine();
                                updated = session.createMutationQuery("update Student set name = :n where id = :id")
                                        .setParameter("n",newName)
                                        .setParameter("id",student_id)
                                        .executeUpdate();
                            }
                            case 2->{
                                out.println("Enter student email :");
                                String email = sc.nextLine();
                                updated = session.createMutationQuery("update Student set email = :e where id = :id")
                                        .setParameter("e",email)
                                        .setParameter("id",student_id)
                                        .executeUpdate();
                            }
                            case 3->{
                                out.println("Enter student marks :");
                                double marks = sc.nextDouble();
                                updated = session.createMutationQuery("update Student set marks = :m where id = :id")
                                        .setParameter("m",marks)
                                        .setParameter("id",student_id)
                                        .executeUpdate();
                            }
                            default -> out.println("Invalid options");
                        }
                        if(updated >0){
                            tx.commit();
                            out.println("student record Updated");
                        }else{
                            tx.rollback();
                            out.println(student_id+" student id not found");
                        }
                    }
                    case 8->{
                        out.println("Enter course id :");
                        int course_id = sc.nextInt();

                        out.println("1.Update Course name");
                        out.println("2.Update Course description");

                        int updateChoice = sc.nextInt();

                        int updated = 0;
                        switch (updateChoice){
                            case 1->{
                                out.println("Enter name to update :");
                                String newName = sc.nextLine();
                                updated = session.createMutationQuery("update Course set name = :cn where id =:id")
                                        .setParameter("cn",newName)
                                        .setParameter("id",course_id)
                                        .executeUpdate();
                            }
                            case 2->{
                                out.println("Enter description to update :");
                                String newDesc = sc.nextLine();
                                updated = session.createMutationQuery("update Course set description = :ds where id =:id")
                                        .setParameter("ds",newDesc)
                                        .setParameter("id",course_id)
                                        .executeUpdate();
                            }
                        }
                        if(updated > 0){
                            tx.commit();
                            out.println("record updated.");
                        }else{
                            tx.rollback();
                            out.println(course_id+" course id not found.");
                        }
                    }
                    case 9->{
                        out.println("Enter student id: ");
                        int sid = sc.nextInt();
                        out.println("Enter course id: ");
                        int cid = sc.nextInt();

                        EnrollmentId eid = new EnrollmentId(sid, cid);
                        Enrollment enrollment = session.find(Enrollment.class, eid);

                        if (enrollment != null) {
                            out.println("Current enrollment date: " + enrollment.getEnrollment_date());
                            out.println("Enter new enrollment date (yyyy-MM-dd): ");
                            String newDate = sc.next();
                            enrollment.setEnrollment_date(LocalDate.parse(newDate));

                            session.merge(enrollment);
                            tx.commit();
                            out.println("Enrollment updated successfully!");
                        } else {
                            out.println("Enrollment not found.");
                            tx.rollback();
                        }
                    }
                    case 10->{
                        out.println("Enter student id to delete :");
                        int sid = sc.nextInt();

                        Student student = session.find(Student.class,sid);
                        if(student != null){
                            session.remove(student);
                            tx.commit();
                            out.println("Student id deleted.");
                        }else{
                            tx.rollback();
                            out.println("Student id is not found");
                        }
                    }
                    case 11->{
                        out.println("Enter course id to delete :");
                        int cid = sc.nextInt();

                        Course course = session.find(Course.class,cid);
                        if(course != null){
                            session.remove(course);
                            tx.commit();
                            out.println("Course id deleted.");
                        }else{
                            tx.rollback();
                            out.println("Course id is not found.");
                        }
                    }
                    case 12->{
                        out.println("Enter student id:");
                        int sid = sc.nextInt();
                        out.println("Enter course id :");
                        int cid = sc.nextInt();

                        EnrollmentId eid = new EnrollmentId(sid,cid);
                        Enrollment enrollment = session.find(Enrollment.class,eid);

                        if(enrollment != null){
                            session.remove(enrollment);
                            tx.commit();
                            out.println("Enrollment record deleted.");
                        }else{
                            tx.rollback();
                            out.println("Enrollment ids not found.");
                        }
                    }
                    case 13 -> out.println("Exiting .....");
                    default -> out.println("Invalid Option ! try again.");
                }
                session.close();
            } while (choice != 13);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}