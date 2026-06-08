package com.example.finanzasbackend.controllers;

import com.example.finanzasbackend.dtos.VehiculoDTO;
import com.example.finanzasbackend.entities.Vehiculo;
import com.example.finanzasbackend.serviceinterfaces.IVehiculoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {
    @Autowired
    private IVehiculoService service;

    @PostMapping("/registrarVehiculo")
    public void create(@RequestBody VehiculoDTO dto){
        ModelMapper m = new ModelMapper();
        Vehiculo vehiculo = m.map(dto, Vehiculo.class);
        service.insert(vehiculo);
    }

    @GetMapping
    public List<VehiculoDTO> list(){
        return service.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, VehiculoDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody VehiculoDTO v){
        Vehiculo existente = service.listId(v.getId_vehiculo());
        if(existente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + v.getId_vehiculo());

        }

        ModelMapper m = new ModelMapper();
        Vehiculo vehiculoActualizado = m.map(v, Vehiculo.class);
        service.update(vehiculoActualizado);
        return ResponseEntity.ok("Registro con ID: " + v.getId_vehiculo() + " modificado correctamente");
    }
}
