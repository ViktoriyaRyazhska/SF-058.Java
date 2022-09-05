package com.app.sportshubportal.service;

import com.app.sportshubportal.entitites.User;
import com.app.sportshubportal.exception.UserAlreadyExistsException;
import com.app.sportshubportal.exception.UserNotFoundException;
import com.app.sportshubportal.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        if (userRepo.existsByUsernameIgnoreCase(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id).orElseThrow(() ->
                new UserNotFoundException("User by id " + id + " was not found."));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException(username));
    }

}
