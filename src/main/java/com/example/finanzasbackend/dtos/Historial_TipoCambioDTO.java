package com.example.finanzasbackend.dtos;

import java.time.LocalDate;
public class Historial_TipoCambioDTO {

    private int id_tipo_cambio;

    private LocalDate fecha;

    private double valor_compra;

    private double valor_venta;

    private int id_moneda;

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

    public int getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(int id_moneda) {
        this.id_moneda = id_moneda;
    }
}
