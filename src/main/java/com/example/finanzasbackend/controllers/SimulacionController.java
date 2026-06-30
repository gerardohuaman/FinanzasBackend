package com.example.finanzasbackend.controllers;

import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;
import com.example.finanzasbackend.entities.Simulacion;
import com.example.finanzasbackend.serviceinterfaces.ISimulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/simulaciones")
public class SimulacionController {
    @Autowired
    private ISimulacionService service;

    @PostMapping("/calcular")
    public ResponseEntity<SimulacionResponseDTO> crearSimulacion(@RequestBody SimulacionInputDTO inputDTO){
        SimulacionResponseDTO response = service.calcularYRegistrarSimulacion(inputDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Simulacion>> listarSimulaciones() {
        List<Simulacion> lista = service.list();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
