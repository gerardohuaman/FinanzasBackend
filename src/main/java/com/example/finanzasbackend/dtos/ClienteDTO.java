package com.example.finanzasbackend.dtos;

import com.example.finanzasbackend.entities.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ClienteDTO {
    private int id_cliente;
    private String dni;
    private String nombres;
    private String apellidos;
    private String email;
    private double ingresos_mensuales;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getIngresos_mensuales() {
        return ingresos_mensuales;
    }

    public void setIngresos_mensuales(double ingresos_mensuales) {
        this.ingresos_mensuales = ingresos_mensuales;
    }
}
