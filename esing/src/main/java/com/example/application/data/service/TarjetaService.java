package com.example.application.data.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.Tarjeta;

@Service
public class TarjetaService {

    private final TarjetaRepository repository;

    @Autowired
    public TarjetaService(TarjetaRepository repository) {
        this.repository = repository;
    }

    public Optional<Tarjeta> get(UUID id) {
        return repository.findById(id);
    }

    public Tarjeta update(Tarjeta entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
    
    public List<Tarjeta> findAllCuentas(String stringFilter){
    	if(stringFilter == null || stringFilter.isEmpty()) {
    		return repository.findAll();
    	}else {
    		return repository.search(stringFilter);
    	}
    }

    public Page<Tarjeta> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}