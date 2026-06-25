package com.example.finanzasbackend.serviceinterfaces;

import com.example.finanzasbackend.entities.Cliente;

import java.util.List;

public interface IClienteService {
    public void insert(Cliente cliente);  //Create
    public List<Cliente> list();            //Read
    public void update(Cliente cliente);          //Update
    public Cliente listId(int id);
    public Cliente findByNombres(String name);
}
