package com.example.JP02.repo;

import com.example.JP02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StduentRepo  extends JpaRepository<Student,Long>
{

}
