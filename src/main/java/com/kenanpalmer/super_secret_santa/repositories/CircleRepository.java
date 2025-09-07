package com.kenanpalmer.super_secret_santa.repositories;

import com.kenanpalmer.super_secret_santa.models.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CircleRepository extends JpaRepository<Circle, Long> {
    Optional<Circle> findByName(String name);
    Optional<Circle> findById(long id);
}
