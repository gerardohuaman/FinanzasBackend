package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;

public interface ISimulacionService {
    public SimulacionResponseDTO calcularYRegistrarSimulacion(SimulacionInputDTO inputDTO);
}
