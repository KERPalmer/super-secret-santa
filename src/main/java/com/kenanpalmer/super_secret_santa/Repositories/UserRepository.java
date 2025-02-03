package com.kenanpalmer.super_secret_santa.Repositories;

import com.kenanpalmer.super_secret_santa.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
