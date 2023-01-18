package com.example.application.data.service;

import com.example.application.data.entity.Incidencia;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncidenciaRepository extends JpaRepository<Incidencia, UUID> {

    Incidencia findByMotivo(String motivo);
}