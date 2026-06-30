package com.example.finanzasbackend.serviceimplements;

import com.example.finanzasbackend.entities.Moneda;
import com.example.finanzasbackend.repositories.IMonedaRepository;
import com.example.finanzasbackend.serviceinterfaces.IMonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MonedaServiceImplement implements IMonedaService {
    @Autowired
    private IMonedaRepository repository;
    @Override
    public List<Moneda> list() {
        return repository.findAll();
    }
}
