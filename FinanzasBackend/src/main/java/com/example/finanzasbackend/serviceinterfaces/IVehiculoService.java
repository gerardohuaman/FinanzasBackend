package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.entities.Vehiculo;

import java.util.List;

public interface IVehiculoService {
    public void insertar(Vehiculo vehiculo);//Create
    public List<Vehiculo> list();           //Read
    public Vehiculo listId(int id);
    public void update(Vehiculo vehiculo);
}
