package com.example.finanzasbackend.controllers;

import com.example.finanzasbackend.dtos.ClienteDTO;
import com.example.finanzasbackend.dtos.MonedaDTO;
import com.example.finanzasbackend.serviceinterfaces.IMonedaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/moneda")
public class MonedaController {
    @Autowired
    private IMonedaService service;
    @GetMapping
    public List<MonedaDTO> list(){
        return service.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, MonedaDTO.class);
        }).collect(Collectors.toList());
    }
}
