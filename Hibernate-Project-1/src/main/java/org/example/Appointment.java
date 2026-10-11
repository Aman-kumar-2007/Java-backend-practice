package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Appointment(){

    }

    public Appointment(String title, LocalDate appointmentDate, LocalTime appointmentTime, LocalDateTime createdAt) {
        this.title = title;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
