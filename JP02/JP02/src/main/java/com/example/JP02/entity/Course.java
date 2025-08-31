package com.example.JP02.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title ;

    @ManyToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private Set<Student> student =  new HashSet<>();

}
