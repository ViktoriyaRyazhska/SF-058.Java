package com.app.sportshubportal.repositories;

import com.app.sportshubportal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    int deleteUserById(Long id);

    Optional<User> findUserById(Long id);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findUserByUsernameIgnoreCase(String username);
}