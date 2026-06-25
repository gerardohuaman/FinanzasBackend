package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.entities.Rol;

import java.util.List;

public interface IRolService {
    public List<Rol> findAll();
    public Rol findById(Integer id);
    public void insert(Rol rol);
    public void deleteById(Integer id);
    public void update(Rol rol);
}
