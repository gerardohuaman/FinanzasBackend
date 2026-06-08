package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_vehiculo;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    @Column(name = "precio_venta", nullable = false, length = 50)
    private double precio_venta;

    @ManyToOne
    @JoinColumn(name = "id_moneda")
    private Moneda moneda;

    public Vehiculo(){}

    public Vehiculo(int id_vehiculo, String marca, String modelo, double precio_venta, Moneda moneda) {
        this.id_vehiculo = id_vehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.precio_venta = precio_venta;
        this.moneda = moneda;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
