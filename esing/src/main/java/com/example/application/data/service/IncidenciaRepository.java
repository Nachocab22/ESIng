package com.example.application.data.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Incidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, UUID> {

    Incidencia findByMotivo(String motivo);
}