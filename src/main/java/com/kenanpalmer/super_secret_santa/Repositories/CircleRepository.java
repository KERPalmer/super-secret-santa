package com.kenanpalmer.super_secret_santa.Repositories;

import com.kenanpalmer.super_secret_santa.Models.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CircleRepository extends JpaRepository<Circle, Long> {
    Optional<Circle> findByName(String name);
}
