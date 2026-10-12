package org.example.CRUD;

import org.example.Course;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class CourseCrud {

    public void createCourse(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.print("Enter Title : ");
        String title =  sc.next();

        IO.print("Enter Credits : ");
        int credit = sc.nextInt();

        try(Session session = factory.openSession()){
            Transaction tx =  session.beginTransaction();
            Course course = new Course(title,credit);
            session.persist(course);
            tx.commit();
        }
    }

    public void deleteCourse(SessionFactory factory){
        Scanner sc = new Scanner(System.in);

        IO.print("Enter Course id : ");
        int id = sc.nextInt();

        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();
            Course course = session.find(Course.class,id);
            if(course == null){
                IO.println("Course not found !!");
                return;
            }
            session.remove(course);
            tx.commit();
        }
    }

    public void assignCourseToStudent(SessionFactory factory){
        Scanner sc = new Scanner(System.in);

        IO.print("Enter Student Id : ");
        Long studentId = sc.nextLong();

        IO.print("Enter course Id : ");
        int courseId = sc.nextInt();

        Transaction tx = null;
        try(Session session = factory.openSession()){
            tx = session.beginTransaction();

            Student student = session.find(Student.class,studentId);
            Course course = session.find(Course.class,courseId);

            if(student == null || course == null){
                System.out.println("Student or Course not found.");
                tx.rollback();
                return;
            }

            student.addCourse(course);

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void getStudentsByCourse(SessionFactory factory) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Course ID: ");
        int courseId = sc.nextInt();

        try (Session session = factory.openSession()) {

            Course course = session.find(Course.class, courseId);

            if (course == null) {
                System.out.println("Course not found.");
                return;
            }

            List<Student> students = session.createQuery(
                            "SELECT s FROM Student s JOIN s.courses c " +
                                    "WHERE c.id = :courseId",
                            Student.class
                    ).setParameter("courseId", courseId)
                    .getResultList();

            if (students.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                return;
            }

            System.out.println("Course: " + course.getTitle());

            for (Student student : students) {
                System.out.println("ID: " + student.getId());
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
            }
        }
    }

    public void getCoursesByStudent(SessionFactory factory) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();

        try (Session session = factory.openSession()) {

            Student student = session.find(Student.class, studentId);

            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            List<Course> courses = session.createQuery(
                            "SELECT c FROM Course c JOIN c.students s " +
                                    "WHERE s.id = :studentId",
                            Course.class
                    ).setParameter("studentId", studentId)
                    .getResultList();

            if (courses.isEmpty()) {
                System.out.println("Student is not enrolled in any course.");
                return;
            }

            System.out.println("Student: " + student.getName());

            for (Course course : courses) {
                System.out.println("Course ID: " + course.getId());
                System.out.println("Course Title: " + course.getTitle());
            }
        }
    }

}
