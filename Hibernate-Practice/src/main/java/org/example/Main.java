package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        cfg.addAnnotatedClass(Employee.class);

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        /*

//        Employee employee = new Employee("Aman",70000.0);
//        session.persist(employee);

        Employee emp = session.find(Employee.class, 1L);

        if (emp != null) {
            System.out.println(emp.getName());
            emp.setSalary(89000.00);

//            session.remove(emp);

        } else {
            System.out.println("Employee not found");
        }

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        System.out.println("Employee saved successfully");


         */

        /*

        Employee emp = new Employee("Test", 50000.0);

// Part 1
        System.out.println("Transient:");
        System.out.println(emp);

// Part 2
        session.beginTransaction();

        session.persist(emp);

        System.out.println("After persist:");
        System.out.println(emp);

// Part 3
        emp.setSalary(60000.0);

        session.flush();

        System.out.println("After salary change:");
        System.out.println(emp);

// Part 4
        session.clear();

// Part 5
        Employee emp2 = session.find(Employee.class, emp.getId());

        System.out.println("After find:");
        System.out.println(emp2);

        session.getTransaction().commit();


         */

        /*

        Employee emp = session.find(Employee.class, 1);

        System.out.println("Before refresh: " + emp.getSalary());

        System.out.println("Change salary in MySQL, then press Enter...");
        new Scanner(System.in).nextLine();

        session.refresh(emp);

        System.out.println("After refresh: " + emp.getSalary());

         */
    }
}
