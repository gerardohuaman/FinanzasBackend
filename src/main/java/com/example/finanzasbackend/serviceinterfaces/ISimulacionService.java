package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;
import com.example.finanzasbackend.entities.Cliente;
import com.example.finanzasbackend.entities.Simulacion;

import java.util.List;

public interface ISimulacionService {
    public SimulacionResponseDTO calcularYRegistrarSimulacion(SimulacionInputDTO inputDTO);
    public List<Simulacion> list();
}
