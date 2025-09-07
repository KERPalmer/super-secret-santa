package com.kenanpalmer.super_secret_santa.repositories;

import com.kenanpalmer.super_secret_santa.models.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(@NotNull Long id);

}
