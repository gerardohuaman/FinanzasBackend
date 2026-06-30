package com.example.finanzasbackend.serviceimplements;

import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;
import com.example.finanzasbackend.entities.Simulacion;
import com.example.finanzasbackend.repositories.ISimulacionRepository;
import com.example.finanzasbackend.serviceinterfaces.ISimulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulacionServiceImplement implements ISimulacionService {
    @Autowired
    private ISimulacionRepository repository;


    @Override
    public SimulacionResponseDTO calcularYRegistrarSimulacion(SimulacionInputDTO inputDTO) {

        SimulacionResponseDTO response = new SimulacionResponseDTO();

        return response;
    }

    @Override
    public List<Simulacion> list() {
        return repository.findAll();
    }
}
