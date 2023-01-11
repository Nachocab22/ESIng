package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "incidencia")
public class Incidencia extends AbstractEntity {
	
	@NotNull
	private String autor;
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
    
    public String getAutor() {
    	return autor;
    }
    
    public void setAutor (String autor) {
    	this.autor = autor;
    }

}
