package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.entities.CronogramaPagos;

import java.util.List;

public interface ICronogramaPagosService {
    void guardarCronograma(List<CronogramaPagos> cronograma);

    List<CronogramaPagos> obtenerPorSimulacion(int idSimulacion);
}
