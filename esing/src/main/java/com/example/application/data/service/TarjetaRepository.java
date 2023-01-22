package com.example.application.data.service;
import com.example.application.data.entity.Tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID> {
	
	@Query("select t from tarjeta t "+
    		"where lower(t.numero) like lower(concat('%', :searchTerm, '%')) "+
    		"or lower(t.titular) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(t.cuenta) like lower(concat('%', :searchTerm, '%')) ")
    	List<Tarjeta> search(@Param("searchTerm") String searchTerm); 
}