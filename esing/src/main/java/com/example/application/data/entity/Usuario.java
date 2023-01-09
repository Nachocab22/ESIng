package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity(name = "usuario")
@Table(name = "usuario")
public class Usuario extends AbstractEntity {

    private String nombre;
    private String username;
    private String dni;
    private String telefono;
    @Email
    private String email;
    private String hashedPassword;
    

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
}
