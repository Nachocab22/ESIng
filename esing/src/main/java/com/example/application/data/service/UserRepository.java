package com.example.application.data.service;

import com.example.application.data.entity.User;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
    		
    
    @Query("select u from user u "+
    		"where lower(u.username) like lower(concat('%', :searchTerm, '%')) "+
    		"or lower(u.name) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(u.dni) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(u.email) like lower(concat('%', :searchTerm, '%')) " +
    		"or lower(u.address) like lower(concat('%', :searchTerm, '%')) ")
    	List<User> search(@Param("searchTerm") String searchTerm);
    	
}