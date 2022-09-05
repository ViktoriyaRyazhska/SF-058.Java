package com.app.sportshubportal.repository;

import com.app.sportshubportal.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findUserByUsernameIgnoreCase(String username);
}