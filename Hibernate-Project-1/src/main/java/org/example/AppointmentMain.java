package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentMain {
    public static void main(String[] args)  throws InterruptedException {
        Configuration cfg = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Appointment.class);

        try(SessionFactory factory = cfg.buildSessionFactory()){
            Long appointmentId = null;

            try (Session session = factory.openSession()) {

                Transaction tx = session.beginTransaction();

                try {
                    Appointment appointment = new Appointment(
                            "Java Class",
                            LocalDate.of(2026, 10, 15),
                            LocalTime.of(10, 30)
                    );

                    session.persist(appointment);
                    tx.commit();

                    appointmentId = appointment.getId();


                    System.out.println("Appointment created");
                    System.out.println("Created at: "
                            + appointment.getCreatedAt());
                    System.out.println("Updated at: "
                            + appointment.getUpdatedAt());
                    System.out.println("Version: "
                            + appointment.getVersion());

                    System.out.println("Appointment Saved !!");

                } catch (Exception e) {
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    throw new RuntimeException(e);
                }
            }

            // Demo ke liye timestamps mein visible difference
            Thread.sleep(1100);

            try (Session session = factory.openSession()) {

                Transaction tx = session.beginTransaction();

                try {
                    Appointment appointment =
                            session.find(Appointment.class, appointmentId);

                    appointment.setTitle("Advanced Java Class");
                    appointment.setAppointmentTime(
                            LocalTime.of(11, 0)
                    );

                    tx.commit();

                    System.out.println("\nAppointment updated");
                    System.out.println("Created at: "
                            + appointment.getCreatedAt());
                    System.out.println("Updated at: "
                            + appointment.getUpdatedAt());
                    System.out.println("Version: "
                            + appointment.getVersion());

                } catch (RuntimeException e) {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                    throw e;
                }
            }

            try (Session session = factory.openSession()) {

                Appointment appointment =
                        session.find(Appointment.class, appointmentId);

                System.out.println("\nAfter fetching again:");
                System.out.println("Title: " + appointment.getTitle());
                System.out.println("Time: "
                        + appointment.getAppointmentTime());
                System.out.println("Version: "
                        + appointment.getVersion());
            }
        }
    }
}
