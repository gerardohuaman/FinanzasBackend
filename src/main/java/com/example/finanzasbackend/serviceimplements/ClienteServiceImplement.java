package com.example.finanzasbackend.serviceimplements;

import com.example.finanzasbackend.entities.Cliente;
import com.example.finanzasbackend.repositories.IClienteRepository;
import com.example.finanzasbackend.serviceinterfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImplement  implements IClienteService {
    @Autowired
    private IClienteRepository repository;
    @Override
    public void insert(Cliente cliente) {
        repository.save(cliente);
    }

    @Override
    public List<Cliente> list() {
        return repository.findAll();
    }

    @Override
    public void update(Cliente cliente) {
        repository.save(cliente);
    }

    @Override
    public Cliente listId(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Cliente findByNombreCompleto(String name) {
        return repository.findByNombreCompleto(name);
    }
}
