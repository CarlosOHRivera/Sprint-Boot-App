package com.oh.springbootApp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.message.Message;

import java.util.List;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotBlank
    @Size(min = 5, max = 60, message = "Debe cumplir con mínimo de Longitud")
    private String firstName;
    @Column
    @NotBlank(message = "Ingrese Su apellido")
    private String lastName;

    @Column(unique = true)
    @Email(message = "Ingrese una dirección de correo válida")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Ingrese el Nombre de Usuario")
    private String username;

    @Column
    @NotBlank(message = "Ingrese la Clave")
    private String password;

    @Column
    private String roles;

    @Transient
    @Column
    @NotBlank(message = "Ingrese de nuevo la Clave")

    private String confirmPassword;


    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String username, String password, String roles, String confirmPassword) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.confirmPassword = confirmPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> rolelist;
}
