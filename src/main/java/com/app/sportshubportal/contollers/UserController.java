package com.app.sportshubportal.contollers;

import com.app.sportshubportal.dto.UserDTO;
import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User newUser = userService.registerUser(user);
        return convertToDto(newUser);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> users() {
        List<User> users = userService.findAllUsers();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return convertToDto(userService.findUserById(id));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User updateUser = userService.updateUser(user);
        return convertToDto(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setProfilePicturePath(user.getProfilePicturePath());
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setProfilePicturePath(userDTO.getProfilePicturePath());
        return user;

    }

}
