package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class CriteriaBuild{

    public static void main(String[] args) {

        Configuration cfg = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class);

        try (SessionFactory factory = cfg.buildSessionFactory();
             Session session = factory.openSession()) {

            // 1. CriteriaBuilder obtain karo
            CriteriaBuilder cb = factory.getCriteriaBuilder();

            // 2. Employee results ke liye query banao
            CriteriaQuery<Employee> query =
                    cb.createQuery(Employee.class);

            // 3. FROM Employee
            Root<Employee> employee = query.from(Employee.class);

            // 4. Optional filters
            Double minSalary = 50000.0;
            String keyword = "a";

            List<Predicate> filters = new ArrayList<>();

            if (minSalary != null) {
                filters.add(
                        cb.greaterThanOrEqualTo(
                                employee.<Double>get("salary"),
                                minSalary
                        )
                );
            }

            if (keyword != null && !keyword.isBlank()) {
                filters.add(
                        cb.like(
                                cb.lower(employee.<String>get("name")),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
            }

            // 5. SELECT + WHERE + ORDER BY
            query.select(employee)
                    .where(filters.toArray(new Predicate[0]))
                    .orderBy(
                            cb.desc(employee.<Double>get("salary"))
                    );

            // 6. Query execute karo
            List<Employee> employees =
                    session.createQuery(query).getResultList();

            // 7. Results display karo
            for (Employee emp : employees) {
                System.out.println(
                        emp.getId() + " | " +
                                emp.getName() + " | " +
                                emp.getSalary()
                );
            }
        }
    }
}

