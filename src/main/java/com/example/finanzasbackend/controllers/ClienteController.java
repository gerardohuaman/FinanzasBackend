package com.example.finanzasbackend.controllers;

import com.example.finanzasbackend.dtos.ClienteDTO;
import com.example.finanzasbackend.entities.Cliente;
import com.example.finanzasbackend.serviceinterfaces.IClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private IClienteService service;

    @PostMapping("/registrar")
    public void insertar(@RequestBody ClienteDTO dto){
        ModelMapper m = new ModelMapper();
        Cliente cliente = m.map(dto, Cliente.class);
        service.insertar(cliente);
    }

    @GetMapping
    public List<ClienteDTO> listar(){
        return service.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, ClienteDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody ClienteDTO c){
        Cliente existente = service.listId(c.getId_cliente());
        if(existente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + c.getId_cliente());
        }

        ModelMapper m = new ModelMapper();
        Cliente clienteActualizado = m.map(c, Cliente.class);
        service.update(clienteActualizado);
        return ResponseEntity.ok("Registro con ID: " + c.getId_cliente() + " modificado correctamente");
    }
}
