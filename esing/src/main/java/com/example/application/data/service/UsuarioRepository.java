package com.example.application.data.service;

import com.example.application.data.entity.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

}