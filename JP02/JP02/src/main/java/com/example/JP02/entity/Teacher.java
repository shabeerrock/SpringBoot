package com.example.JP02.entity;

import jakarta.persistence.*;




import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name ;

    @OneToMany( mappedBy="teacher" )
    private List<Student> student =  new ArrayList<>();

}
