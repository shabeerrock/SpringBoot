package com.example.JP02.repo;

import com.example.JP02.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaptopRepo  extends JpaRepository<Laptop,Long>
{

}
