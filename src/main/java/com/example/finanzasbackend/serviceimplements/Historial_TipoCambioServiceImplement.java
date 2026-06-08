package com.example.finanzasbackend.serviceimplements;

import com.example.finanzasbackend.entities.Historial_TipoCambio;
import com.example.finanzasbackend.repositories.IHistorial_TipoCambioRepository;
import com.example.finanzasbackend.serviceinterfaces.IHistorial_TipoCambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Historial_TipoCambioServiceImplement implements IHistorial_TipoCambioService {
    @Autowired
    private IHistorial_TipoCambioRepository repository;

    @Override
    public Historial_TipoCambio obtenerUltimoTipoCambio() {
        return repository.findLastestTipoCambio().orElse(null);
    }
}
