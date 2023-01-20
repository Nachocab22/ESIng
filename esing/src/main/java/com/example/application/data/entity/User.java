package com.example.application.data.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "user")
@Table(name = "application_user")
public class User extends AbstractEntity {
	
	@NotNull
    private String username;
	
	@NotNull
    private String name;
	
	@NotNull
    private String surname;
	
    @NotNull
    @Column(length = 9)
    private String dni;
    
    @NotNull
    @Email
    private String email;
    
    //@NotNull
    private String phone;
    
    //@NotNull
    private String address;
    
	@NotNull
    @JsonIgnore
    private String hashedPassword;
    
    private Role rol;

    public String getSurname() {
    	return surname;
    }
    public void setSurname(String surname) {
    	this.surname = surname;
    }
    public String getDni() {
    	return dni;
    }
    public void setDni(String dni) {
    	this.dni = dni;
    }
    
    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;
    
    @OneToMany(mappedBy = "titular")
    private Set<Tarjeta> tarjetas;
    
    @OneToMany(mappedBy = "titular")
    private Set<Cuenta> cuentas;
    
    @OneToMany(mappedBy = "autor")
    private Set<Incidencia> incidencias;

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
    public Role getRol() {
    	return rol;
    }
    public void setRol(Role rol) {
    	this.rol = rol;
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