package com.app.sportshubportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class UserDTO {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String profilePicturePath;
}
