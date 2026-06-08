package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.Simulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISimulacionRepository extends JpaRepository<Simulacion, Integer> {
}
