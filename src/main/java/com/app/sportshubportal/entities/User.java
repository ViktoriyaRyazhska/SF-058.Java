package com.app.sportshubportal.entities;

import com.app.sportshubportal.roles.Role;
import com.app.sportshubportal.validations.EmailValidator;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank
    private String password;
    @EmailValidator
    @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profilePicturePath;

//    public User(String username, String password, String email, Role role, String profilePicturePath) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.profilePicturePath = profilePicturePath;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
