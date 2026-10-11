package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentMain {
    public static void main(String[] args){
        Configuration cfg = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Appointment.class);

        try(SessionFactory factory = cfg.buildSessionFactory()){
            Long appointmentId = null;

            try (Session session = factory.openSession()) {

                Transaction tx = session.beginTransaction();

                try {

                    Appointment appointment = new Appointment(
                            "OJT Class",
                            LocalDate.of(2026,10,15),
                            LocalTime.of(10,30),
                            LocalDateTime.now()
                    );
                    session.persist(appointment);
                    tx.commit();

                    appointmentId = appointment.getId();

                    System.out.println("Appointment Saved !!");

                } catch (Exception e) {
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    throw new RuntimeException(e);
                }
            }

            try (Session session = factory.openSession()) {

                Appointment appointment =
                        session.find(Appointment.class, appointmentId);

                if (appointment != null) {
                    System.out.println("Title: "
                            + appointment.getTitle());

                    System.out.println("Date: "
                            + appointment.getAppointmentDate());

                    System.out.println("Time: "
                            + appointment.getAppointmentTime());

                    System.out.println("Created at: "
                            + appointment.getCreatedAt());
                }
            }
        }
    }
}
