package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        cfg.addAnnotatedClass(Employee.class);

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Employee employee = new Employee("Aman",70000.0);
        session.persist(employee);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        System.out.println("Employee saved successfully");

    }
}
