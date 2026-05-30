package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Integer> {
}
