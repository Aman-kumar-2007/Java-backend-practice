package org.example.dao;

import org.example.model.Student;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDao {
    public static User findByUsername(
            SessionFactory factory, String username) {

        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "FROM User u WHERE u.username = :username",
                            User.class
                    )
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }
}
