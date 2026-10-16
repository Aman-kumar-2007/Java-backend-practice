package org.example.model;

import jakarta.persistence.*;
import org.example.model.Student;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_name")
    private String name;

    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<Student> students = new java.util.ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudents(Student student){
        students.add(student);
        student.setDepartment(this);
    }

    public void removeStudents(Student student){
        students.remove(student);
        student.setDepartment(null);
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
