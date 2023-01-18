package com.example.application.data.entity;

import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity(name = "user")
@Table(name = "application_user")
public class User extends AbstractEntity {
	
	@NotNull
    private String username;
	@NotNull
    private String name;
	//@NotNull
    private String surname;
    //@NotNull
    @Column(length = 9)
    private String dni;
    //@NotNull
    @Email
    private String email;
    //@NotNull
    private String phone;
    //@NotNull
    private String address;
    //@NotNull
    private Date birthDate;
    //@Column(columnDefinition = "tinyint default null")
    //@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = false)
    //private int titular;
    @JsonIgnore
    private String hashedPassword;
    
    //Posiblemente podemos editar el modelo de datos para no tener Tipo_Usuario y tener este Set<Role>
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;
    

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getdni() {
        return dni;
    }
    public void setdni(String dni) {
        this.dni = dni;
    }
}
