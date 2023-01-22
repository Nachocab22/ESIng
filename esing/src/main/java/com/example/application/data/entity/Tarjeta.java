package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "tarjeta")
@Table(name = "tarjeta")
public class Tarjeta extends AbstractEntity{	
	
    @NotNull
    private String numero;
    
    @NotNull
    private String cvv;
    
    @NotNull
    private String caducidad;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User titular;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}

	public User getTitular() {
		return titular;
	}

	public void setTitular(User titular) {
		this.titular = titular;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
}