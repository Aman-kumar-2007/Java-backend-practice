package org.example.CRUD;

import org.example.entity.Department;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class DepartmentCrud {

    public void createDepartment(SessionFactory factory){
        Scanner sc = new Scanner(System.in);

        IO.print("Enter Department name : ");
        String name = sc.next();

        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();
            Department department = new Department(name);
            session.persist(department);

            tx.commit();

            IO.println("Department Created !!");
        }
    }

    public void getAlLDepartments(SessionFactory factory){

        try(Session session = factory.openSession()){
            List<Department> departs = session
                    .createQuery("FROM department", Department.class)
                    .getResultList();

            if(departs.isEmpty()){
                IO.println("No department found");
                return;
            }

            for(Department dep : departs){
                IO.println("Id : " + dep.getId());
                IO.println("Name : " + dep.getName());
            }
        }
    }

    public void getDepartmentById(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        IO.println("Enter Department Id : ");
        Long id = sc.nextLong();

        try(Session session = factory.openSession()){
            Department department = session.find(Department.class,id);

            if(department != null){
                IO.println("Name : " + department.getName());
            } else {
                IO.println("Department not Found");
            }
        }
    }

    public void assignStudentToDepartment(SessionFactory factory){
        Scanner sc =  new Scanner(System.in);

        System.out.print("Enter Student ID: ");
        Long studentId = sc.nextLong();

        System.out.print("Enter Department ID: ");
        Long departmentId = sc.nextLong();

        Transaction tx = null;

        try(Session session = factory.openSession()){

            tx = session.beginTransaction();

            Student student = session.find(Student.class, studentId);
            Department department = session.find(Department.class, departmentId);

            if (student == null || department == null) {
                System.out.println("Student or Department not found.");
                tx.rollback();
                return;
            }

            student.setDepartment(department);

            tx.commit();

            System.out.println("Department assigned successfully!");
        } catch (Exception e) {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

}
