package com.app.sportshubportal.services;

import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.exception.BusinessException;
import com.app.sportshubportal.exception.UserAlreadyExistsException;
import com.app.sportshubportal.exception.UserNotFoundException;
import com.app.sportshubportal.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(@Valid User user) {
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
        Optional<User> optionalUser = userRepo.findById(user.getId());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with ID " + user.getId() + " does not exist.");
        }
        return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id).orElseThrow(() ->
                new UserNotFoundException("User with id:" + id + " was not found!"));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepo.deleteUserById(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException(username));

    }
    @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistsException.class})
    public BusinessException handleExceptions(RuntimeException e) {
        return new BusinessException(e.getMessage());
    }
}
