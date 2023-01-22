package com.example.application.data.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name= "movimiento")
@Table(name= "movimiento")
public class Movimiento extends AbstractEntity {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "tarjeta_id")
    private Tarjeta tarjeta;
	
	@NotNull
    private boolean retenido;
	
	private String concepto;
    
    @NotNull
    private String tipo;
    
    @NotNull
    private double cantidad;
    
    @NotNull
    private LocalDate fecha_op;
        
    public Cuenta getCuenta() {
        return cuenta;
    }
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public Tarjeta getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    public boolean getRetenido() {
        return retenido;
    }
    public void setRetenido(boolean retenido) {
    	this.retenido = retenido;
    }
    
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    public double getCantidad() {
        return cantidad;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    public LocalDate getFecha_op() {
        return fecha_op;
    }
    public void setFecha_op(LocalDate fecha_op) {
        this.fecha_op = fecha_op;
    }
}