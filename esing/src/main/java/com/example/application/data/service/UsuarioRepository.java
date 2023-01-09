package com.example.application.data.service;

import com.example.application.data.entity.Usuario;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	
    @Query("select u from usuario u "+
    		"where lower(u.username) like lower(concat('%', :searchTerm, '%')) "+
    		"or lower(u.nombre) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(u.dni) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(u.email) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(u.telefono) like lower(concat('%', :searchTerm, '%')) ")
    	List<Usuario> search(@Param("searchTerm") String searchTerm);

}