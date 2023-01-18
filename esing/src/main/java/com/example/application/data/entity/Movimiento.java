package com.example.application.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name= "movimiento")
@Table(name= "movimiento")
public class Movimiento extends AbstractEntity {
	
    private String cuenta;
    private String tarjeta;
    private int retenido;
    private String concepto;
    private double cantidad;
    private LocalDate fecha_op;
    private int tipo;
    
    public String getCuenta() {
        return cuenta;
    }
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    public String getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    public int getRetenido() {
        return retenido;
    }
    public void setRetenido(int retenido) {
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
    
    public LocalDate getFecha_op() {
        return fecha_op;
    }
    public void setFecha_op(LocalDate fecha_op) {
        this.fecha_op = fecha_op;
    }
    
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}