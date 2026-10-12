package org.example.CRUD;

import org.example.entity.Department;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class StudentCrud {

    public void addStudent(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.println("Enter name : ");
        String name = sc.next();
        IO.println("Enter email : ");
        String email = sc.next();

        try(Session session = factory.openSession()){

            Transaction tx = session.beginTransaction();

            Student student = new Student(name,email);
            session.persist(student);

            tx.commit();

            System.out.println("Student added successfully!");
            System.out.println("Student ID: " + student.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStudentById(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.println("Enter Student Id : ");
        Long id = sc.nextLong();

        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();
            Student student = session.find(Student.class,id);
            tx.commit();

            if(student != null){
              IO.println("Name : " + student.getName());
              IO.println("Email : " + student.getEmail());
            } else {
                IO.println("Student not Found");
            }
        }
    }

    public void getAllStudent(SessionFactory factory){
        try(Session session = factory.openSession()){

            List<Student> studentList = session
                    .createQuery("FROM student", Student.class)
                    .getResultList();

            if (studentList.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            for (Student student : studentList) {
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
            }
        }
    }

    public void searchStudentsByName(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.println("Enter Student name : ");
        String name = sc.next();

        try(Session session = factory.openSession()){

            List<Student> studentList = session
                    .createQuery("FROM student WHERE s.name = :name", Student.class)
                    .setParameter("name",name)
                    .getResultList();

            if (studentList.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            for (Student student : studentList) {
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
            }

        }
    }

    public void searchStudentsByDepartment(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.println("Enter Student department : ");
        String depart = sc.next();

        try(Session session = factory.openSession()){

            List<Student> studentList = session
                    .createQuery("FROM student WHERE s.department.name = :depart", Student.class)
                    .setParameter("depart",depart)
                    .getResultList();

            if (studentList.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            for (Student student : studentList) {
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
            }

        }

    }

    public void removeStudent(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.println("Enter Student Id : ");
        Long id = sc.nextLong();

        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();

            Student student = session.find(Student.class,id);
            if(student == null){
                IO.println("Student not found");
                return;
            }

            session.remove(student);
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        try(SessionFactory factory = cfg.buildSessionFactory()) {

        }
    }
}
