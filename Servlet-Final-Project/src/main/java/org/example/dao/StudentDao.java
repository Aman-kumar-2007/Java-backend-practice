package org.example.dao;

import org.example.model.Student;
import org.example.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDao {

    public static void addStudent(SessionFactory factory, Student student) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            session.persist(student);

            tx.commit();
            System.out.println("Student added successfully!");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Student getStudentById(SessionFactory factory, Long id) {
        try (Session session = factory.openSession()) {
            return session.find(Student.class, id);
        }
    }

    public static List<Student> getAllStudents(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            return session.createSelectionQuery(
                    "FROM Student s ORDER BY s.id", Student.class
            ).getResultList();
        }
    }

    public List<Student> searchStudentsByName(
            SessionFactory factory, String name) {

        try (Session session = factory.openSession()) {
            return session.createSelectionQuery(
                            "FROM Student s WHERE LOWER(s.name) LIKE LOWER(:name)",
                            Student.class
                    ).setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }

    public List<Student> getStudentsByDepartment(
            SessionFactory factory, Long departmentId) {

        try (Session session = factory.openSession()) {
            return session.createSelectionQuery(
                            "FROM Student s WHERE s.department.id = :deptId",
                            Student.class
                    ).setParameter("deptId", departmentId)
                    .getResultList();
        }
    }

    public boolean assignStudentToDepartment(
            SessionFactory factory,
            Long studentId,
            Long departmentId) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Student student = session.find(Student.class, studentId);
            Department department =
                    session.find(Department.class, departmentId);

            if (student == null || department == null) {
                tx.rollback();
                return false;
            }

            student.setDepartment(department);

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

    public boolean updateStudent(
            SessionFactory factory,
            Long id,
            String name,
            String email) {

        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Student student = session.find(Student.class, id);

            if (student == null) {
                tx.rollback();
                return false;
            }

            student.setName(name);
            student.setEmail(email);

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

    public boolean deleteStudent(SessionFactory factory, Long id) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Student student = session.find(Student.class, id);

            if (student == null) {
                tx.rollback();
                return false;
            }

            session.remove(student);

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