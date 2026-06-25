package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.entities.Vehiculo;

import java.util.List;

public interface IVehiculoService {
    public void insert(Vehiculo vehiculo);//Create
    public List<Vehiculo> list();           //Read
    public Vehiculo listId(int id);
    public void update(Vehiculo vehiculo);
    public Vehiculo findByMarca(String marca);
}
