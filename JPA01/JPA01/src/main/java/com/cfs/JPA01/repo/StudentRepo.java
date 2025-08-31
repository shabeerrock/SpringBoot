package com.cfs.JPA01.repo;

import com.cfs.JPA01.enity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long>
{

}
