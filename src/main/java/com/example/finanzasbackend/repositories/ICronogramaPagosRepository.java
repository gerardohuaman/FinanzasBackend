package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.CronogramaPagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICronogramaPagosRepository extends JpaRepository<CronogramaPagos, Integer> {
    @Query(value = "select c from CronogramaPagos c where c.simulacion.id_simulacion = :idSimulacion")
    List<CronogramaPagos> findBySimulacionIdSimulacion(@Param("idSimulacion") int idSimulacion);
}
