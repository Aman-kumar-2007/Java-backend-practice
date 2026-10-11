package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_profile_id",nullable = false)
    private Long id;

    @Column(name = "student_address")
    private String address;

    @Column(name = "student_phone_no")
    private int Phone;

    @OneToOne(
            mappedBy = "profiles"
    )
    private Student student;

    public StudentProfile() {
    }

    public StudentProfile(String address, int phone) {
        this.address = address;
        Phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return Phone;
    }

    public Student getStudent() {
        return student;
    }
}
