package com.example.application.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.application.data.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
	
	@Query("select c from cuenta c "+
    		"where lower(c.iban) like lower(concat('%', :searchTerm, '%')) "+
    		"or lower(c.titular) like lower(concat('%', :searchTerm, '%')) ")
    	List<Cuenta> search(@Param("searchTerm") String searchTerm);  
}