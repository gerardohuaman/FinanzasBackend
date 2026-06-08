package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.CronogramaPagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICronogramaPagosRepository extends JpaRepository<CronogramaPagos, Integer> {
    List<CronogramaPagos> findBySimulacionIdSimulacion(int idSimulacion);
}
