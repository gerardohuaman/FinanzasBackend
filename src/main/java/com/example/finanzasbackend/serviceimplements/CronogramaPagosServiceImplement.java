package com.example.finanzasbackend.serviceimplements;

import com.example.finanzasbackend.entities.CronogramaPagos;
import com.example.finanzasbackend.repositories.ICronogramaPagosRepository;
import com.example.finanzasbackend.serviceinterfaces.ICronogramaPagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CronogramaPagosServiceImplement implements ICronogramaPagosService {
    @Autowired
    private ICronogramaPagosRepository repository;

    @Override
    @Transactional
    public void guardarCronograma(List<CronogramaPagos> cronograma) {
        repository.saveAll(cronograma);
    }

    @Override
    public List<CronogramaPagos> obtenerPorSimulacion(int idSimulacion) {
        return repository.findBySimulacionIdSimulacion(idSimulacion);
    }
}
