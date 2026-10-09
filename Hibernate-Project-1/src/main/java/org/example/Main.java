package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();

        try {
            Department department = new Department("IT");

            Employee aman = new Employee("Aman", 50000.00);
            Employee rahul = new Employee("Rahul", 45000.00);
            Employee priya = new Employee("Priya", 60000.00);

            department.addEmployee(aman);
            department.addEmployee(rahul);
            department.addEmployee(priya);

            session.persist(department);
            
            session.persist(aman);
            session.persist(rahul);
            session.persist(priya);


            tx.commit();

            System.out.println("Department: " + department.getName());

            for (Employee employee : department.getEmployees()) {
                System.out.println(employee.getName());
            }

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }

    }
}
