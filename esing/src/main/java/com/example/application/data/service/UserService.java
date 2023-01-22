package com.example.application.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.User;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User findUserByUsername(String username) {
    	return repository.findByUsername(username);
    }
    
    public User getCurrentUser() {
    	return findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public Optional<User> get(UUID id) {
        return repository.findById(id);
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    } 
    
    public List<User> findAllUsers(String stringFilter){
    	if(stringFilter == null || stringFilter.isEmpty()) {
    		return repository.findAll();
    	}else {
    		return repository.search(stringFilter);
    	}
    }
    
    public long countUsers() {
    	return repository.count();	
    }
    
    public void deleteUser(User user) {
    	repository.delete(user);
    }
    
    public void saveUser(User user) {
    	if(user == null) {
    		System.err.println("Contact is null.");
    		return;
    	}
    	
    	repository.save(user);
    }
    
}