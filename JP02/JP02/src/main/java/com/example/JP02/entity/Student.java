package com.example.JP02.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="STD")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   private String name ;

   @OneToOne(mappedBy = "student" ,cascade = CascadeType.ALL )
   private Laptop laptop;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "teacher_id")
   private Teacher teacher;

   @ManyToMany
   @JoinTable(
           name = "student_course",
           joinColumns = @JoinColumn(name = "student_id"),
           inverseJoinColumns = @JoinColumn(name = "Course_id")
   )
   private Set<Course> course = new HashSet<>();


    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
}
