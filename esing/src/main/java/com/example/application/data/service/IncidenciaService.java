package com.example.application.data.service;

import com.example.application.data.entity.Incidencia;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncidenciaService {

    private final IncidenciaRepository repository;

    @Autowired
    public IncidenciaService(IncidenciaRepository repository) {
        this.repository = repository;
    }

    public Optional<Incidencia> get(UUID id) {
        return repository.findById(id);
    }

    public Incidencia update(Incidencia entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Incidencia> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
