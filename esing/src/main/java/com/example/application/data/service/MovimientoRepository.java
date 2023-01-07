package com.example.application.data.service;

import com.example.application.data.entity.Movimiento;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {

	Optional<Movimiento> findByCuenta(String cuenta);

}