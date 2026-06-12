package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByName(String name);
}
