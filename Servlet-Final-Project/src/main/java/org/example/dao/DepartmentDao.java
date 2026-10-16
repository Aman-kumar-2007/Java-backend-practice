package org.example.dao;

import org.example.model.Department;
import org.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DepartmentDao {

    public void addDepartment(
            SessionFactory factory, Department department) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            session.persist(department);

            tx.commit();
            System.out.println("Department added successfully!");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Department getDepartmentById(
            SessionFactory factory, Long id) {

        try (Session session = factory.openSession()) {
            return session.find(Department.class, id);
        }
    }

    public List<Department> getAllDepartments(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            return session.createSelectionQuery(
                    "FROM Department d ORDER BY d.id",
                    Department.class
            ).getResultList();
        }
    }

    public List<Department> searchDepartmentsByName(
            SessionFactory factory, String name) {

        try (Session session = factory.openSession()) {
            return session.createSelectionQuery(
                            "FROM Department d WHERE LOWER(d.name) LIKE LOWER(:name)",
                            Department.class
                    ).setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }

    public List<Student> getStudentsByDepartment(
            SessionFactory factory, Long departmentId) {

        try (Session session = factory.openSession()) {
            Department department =
                    session.find(Department.class, departmentId);

            if (department == null) {
                return List.of();
            }

            return session.createSelectionQuery(
                            "FROM Student s WHERE s.department.id = :deptId",
                            Student.class
                    ).setParameter("deptId", departmentId)
                    .getResultList();
        }
    }

    public boolean updateDepartment(
            SessionFactory factory, Long id, String newName) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Department department =
                    session.find(Department.class, id);

            if (department == null) {
                tx.rollback();
                return false;
            }

            // Your current entity needs a setName() method.
            department.setName(newName);

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(
            SessionFactory factory, Long id) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Department department =
                    session.find(Department.class, id);

            if (department == null) {
                tx.rollback();
                return false;
            }

            Long count = session.createSelectionQuery(
                            "SELECT COUNT(s) FROM Student s " +
                                    "WHERE s.department.id = :deptId",
                            Long.class
                    ).setParameter("deptId", id)
                    .getSingleResult();

            if (count > 0) {
                tx.rollback();
                System.out.println(
                        "Cannot delete: department still has students."
                );
                return false;
            }

            session.remove(department);

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}