package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonedaRepository extends JpaRepository<Moneda, Integer> {
}
