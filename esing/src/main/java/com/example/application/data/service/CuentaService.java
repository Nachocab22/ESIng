package com.example.application.data.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;

@Service
public class CuentaService {

    private final CuentaRepository repository;

    @Autowired
    public CuentaService(CuentaRepository repository) {
        this.repository = repository;
    }

    public Optional<Cuenta> get(UUID id) {
        return repository.findById(id);
    }

    public Cuenta update(Cuenta entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Cuenta> list(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    public List<Cuenta> findAllCuentas(String stringFilter){
    	if(stringFilter == null || stringFilter.isEmpty()) {
    		return repository.findAll();
    	}else {
    		return repository.search(stringFilter);
    	}
    }
    
    public void actualizarSaldo(Movimiento movimiento, Cuenta cuenta) {
		if(movimiento.getTipo() == "Gasto")
			cuenta.setSaldo(cuenta.getSaldo() - movimiento.getCantidad());
		else
			cuenta.setSaldo(cuenta.getSaldo() + movimiento.getCantidad());
	}

    public int count() {
        return (int) repository.count();
    }
}