package com.example.JP02.entity;

import jakarta.persistence.*;

@Entity
public class Laptop
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long   id;

    private String Brand;

    @OneToOne
    @JoinColumn(name = "Student_id")
    private Student student;

}
