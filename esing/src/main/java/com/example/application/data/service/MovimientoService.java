package com.example.application.data.service;

import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService {

    private final MovimientoRepository repository;

    @Autowired
    public MovimientoService(MovimientoRepository repository) {
        this.repository = repository;
    }

    public Optional<Movimiento> get(UUID id) {
        return repository.findById(id);
    }

    public Movimiento update(Movimiento entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Movimiento> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Movimiento> findAllMovimientos(String stringFilter){
    	if(stringFilter == null || stringFilter.isEmpty()) {
    		return repository.findAll();
    	}else {
    		return repository.search(stringFilter);
    	}
    }
    
    public int count() {
        return (int) repository.count();
    }
}