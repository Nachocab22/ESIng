package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "incidencia")
public class Incidencia extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User autor;
	
	@NotNull
    private String motivo;
	@NotNull
    private Boolean incidencia;
	//@NotNull
    private String descripcion;
    
    public String getMotivo() {
    	return motivo;
    }
    
    public void setMotivo(String motivo) {
    	this.motivo = motivo;
    }
    
    public Boolean getIncidencia() {
    	return incidencia;
    }
    
    public void setIncidencia(Boolean incidencia) {
    	this.incidencia = incidencia;
    }
    
    public String getDescripcion() {
    	return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
    	this.descripcion = descripcion;
    }
    
    public User getAutor() {
    	return autor;
    }
    
    public void setAutor (User autor) {
    	this.autor = autor;
    }

}
