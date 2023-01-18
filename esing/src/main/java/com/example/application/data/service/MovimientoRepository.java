package com.example.application.data.service;

import com.example.application.data.entity.Movimiento;


import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {

	Movimiento findByCuenta(String cuenta);
    		
    @Query("select u from movimiento u "+
    		"where lower(u.cuenta) like lower(concat('%', :searchTerm, '%')) ")
    	List<Movimiento> search(@Param("searchTerm") String searchTerm);

}