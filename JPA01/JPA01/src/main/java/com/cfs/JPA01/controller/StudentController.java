package com.cfs.JPA01.controller;

import com.cfs.JPA01.enity.Student;
import com.cfs.JPA01.repo.StudentRepo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController
{
    public StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public List<Student> getallthestudent()
    {
      return studentRepo.findAll() ;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student )
    {
        return  studentRepo.save(student);
    }

    @PutMapping
    public  Student updateStudent(@RequestParam Long id, @RequestBody Student student )
    {
        Student st =  studentRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Student Not found") );

        st.setName( student.getName() );
        st.setEmail(student.getEmail());
        return studentRepo.save(st) ;
    }

    @PatchMapping
    public  Student updateid(@RequestParam Long id, @RequestParam String name)
    {
        Student st =  studentRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Student Not found") );

        st.setName( name);
        return studentRepo.save(st) ;
    }



}
