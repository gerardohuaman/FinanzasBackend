package com.example.finanzasbackend.serviceimplements;

import com.example.finanzasbackend.entities.Vehiculo;
import com.example.finanzasbackend.repositories.IVehiculoRepository;
import com.example.finanzasbackend.serviceinterfaces.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImplement implements IVehiculoService {
    @Autowired
    private IVehiculoRepository repository;


    @Override
    public void insertar(Vehiculo vehiculo) {
        repository.save(vehiculo);
    }

    @Override
    public List<Vehiculo> list() {
        return repository.findAll();
    }

    @Override
    public Vehiculo listId(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void update(Vehiculo vehiculo) {
        repository.save(vehiculo);
    }
}
