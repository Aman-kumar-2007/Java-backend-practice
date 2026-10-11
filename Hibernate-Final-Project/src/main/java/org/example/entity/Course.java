package org.example;

import jakarta.persistence.*;
import org.example.entity.Student;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private int credits;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    public Course() {

    }

    public Course(String title,int credits) {
        this.title = title;
        this.credits  = credits;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public Set<Student> getStudents() {
        return students;
    }
}