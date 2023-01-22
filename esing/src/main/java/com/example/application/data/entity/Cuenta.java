package com.example.application.data.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "cuenta")
@Table(name = "cuenta")
public class Cuenta extends AbstractEntity{

    @NotNull
    private String iban;
    
    @NotNull
    private String mote;
    
    @NotNull
    private double saldo;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User titular;
    
    @OneToMany(mappedBy = "cuenta")
    private Set<Tarjeta> tarjetas;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getMote() {
		return mote;
	}

	public void setMote(String mote) {
		this.mote = mote;
	}

	public User getTitular() {
		return titular;
	}

	public void setTitular(User titular) {
		this.titular = titular;
	}

	public Set<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(Set<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}
	
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}