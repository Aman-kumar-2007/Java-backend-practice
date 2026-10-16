package org.example.model;

import jakarta.persistence.*;
import org.example.model.Department;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id",nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Column(name = "joining_date")
    private LocalDateTime joiningdate;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "department_id")
    private Department department;

    public Student() {
    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(LocalDateTime joiningdate) {
        this.joiningdate = joiningdate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
