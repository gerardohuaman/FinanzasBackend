package com.example.finanzasbackend.repositories;

import com.example.finanzasbackend.entities.Historial_TipoCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IHistorial_TipoCambioRepository extends JpaRepository<Historial_TipoCambio, Integer> {
    //JPQL: Obtener el tipo de cambio mas reciente ingresado en el sistema
    @Query(value = "select h from Historial_TipoCambio h order by h.fecha desc, h.id_tipo_cambio desc limit 1")
    Optional<Historial_TipoCambio> findLastestTipoCambio();
}
