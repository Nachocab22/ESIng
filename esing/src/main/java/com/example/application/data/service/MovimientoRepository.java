package com.example.application.data.service;

import com.example.application.data.entity.Movimiento;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {

}