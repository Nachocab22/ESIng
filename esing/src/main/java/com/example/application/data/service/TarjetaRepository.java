package com.example.application.data.service;
import com.example.application.data.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID> {
}