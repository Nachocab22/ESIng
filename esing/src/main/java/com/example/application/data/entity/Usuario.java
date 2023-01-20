package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.example.application.data.Role;

@Entity(name = "usuario")
@Table(name = "usuario")
public class Usuario extends AbstractEntity {
	
	@NotNull
    private String nombre;
	@NotNull
	private String apellidos;
	@NotNull
    private String username;
	@Column(length = 9)
    private String dni;
	@Column(length = 9)
    private String telefono;
    @Email
    private String email;
    @NotNull
    private String hashedPassword;
    @NotNull
    private Role rol;
    

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
    	return apellidos;
    }
    public void setApellidos(String apellidos) {
    	this.apellidos = apellidos;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    
    public Role getRole() {
    	return rol;
    }
    public void setRole(Role rol) {
    	this.rol = rol;
    }
}
