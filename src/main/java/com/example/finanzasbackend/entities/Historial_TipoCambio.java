package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "historial_tipo_cambio")
public class Historial_TipoCambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipo_cambio;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "valor_compra", nullable = false)
    private double valor_compra;

    @Column(name = "valor_venta", nullable = false)
    private double valor_venta;

    @ManyToOne
    @JoinColumn(name = "id_moneda")
    private Moneda moneda;

    public Historial_TipoCambio(){}

    public Historial_TipoCambio(int id_tipo_cambio, LocalDate fecha, double valor_compra, double valor_venta, Moneda moneda) {
        this.id_tipo_cambio = id_tipo_cambio;
        this.fecha = fecha;
        this.valor_compra = valor_compra;
        this.valor_venta = valor_venta;
        this.moneda = moneda;
    }

    public int getId_tipo_cambio() {
        return id_tipo_cambio;
    }

    public void setId_tipo_cambio(int id_tipo_cambio) {
        this.id_tipo_cambio = id_tipo_cambio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getValor_compra() {
        return valor_compra;
    }

    public void setValor_compra(double valor_compra) {
        this.valor_compra = valor_compra;
    }

    public double getValor_venta() {
        return valor_venta;
    }

    public void setValor_venta(double valor_venta) {
        this.valor_venta = valor_venta;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
