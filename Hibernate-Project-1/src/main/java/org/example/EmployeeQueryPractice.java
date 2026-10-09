package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeQueryPractice {

    public static void main(String[] args) {

        // Apni existing SessionFactory reuse karo
        SessionFactory factory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        try (Session session = factory.openSession()) {

            // 1. All employees in ID order
            String hql1 = "FROM Employee e ORDER BY e.id ASC";

            List<Employee> employees1 =
                    session.createQuery(hql1, Employee.class)
                            .getResultList();

            employees1.forEach(System.out::println);


            // 2. Salary greater than 50000
            String hql2 =
                    "FROM Employee e WHERE e.salary > :salary";

            List<Employee> employees2 =
                    session.createQuery(hql2, Employee.class)
                            .setParameter("salary", 50000.0)
                            .getResultList();

            employees2.forEach(System.out::println);


            // 3. Employees of IT department
            String hql3 =
                    "FROM Employee e WHERE e.department.name = :dept";

            List<Employee> employees3 =
                    session.createQuery(hql3, Employee.class)
                            .setParameter("dept", "IT")
                            .getResultList();

            employees3.forEach(System.out::println);


            // 4. Only employee names
            String hql4 = "SELECT e.name FROM Employee e";

            List<String> names =
                    session.createQuery(hql4, String.class)
                            .getResultList();

            names.forEach(System.out::println);


            // 5. First 5 employees
            String hql5 = "FROM Employee e ORDER BY e.id ASC";

            List<Employee> employees5 =
                    session.createQuery(hql5, Employee.class)
                            .setMaxResults(5)
                            .getResultList();

            employees5.forEach(System.out::println);


            // 6. Average salary
            String hql6 = "SELECT AVG(e.salary) FROM Employee e";

            Double averageSalary =
                    session.createQuery(hql6, Double.class)
                            .getSingleResult();

            System.out.println("Average Salary: " + averageSalary);

        } finally {
            factory.close();
        }
    }
}