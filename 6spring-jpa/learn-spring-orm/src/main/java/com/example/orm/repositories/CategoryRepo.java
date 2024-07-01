package com.example.orm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.orm.entities.Catogory;

public interface CategoryRepo extends JpaRepository<Catogory,String> {
}
