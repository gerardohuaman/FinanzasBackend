package com.example.finanzasbackend.dtos;

import java.util.List;

public class SimulacionResponseDTO {
    private int id_simulacion;
    private String moneda_simulacion;
    private double precio_vehiculo_calculado;
    private double cuota_inicial;
    private double monto_financiado;
    private double cuota_balon;

    //Indicadores Financieros
    private double van;
    private double tir;
    private double tcea;

    //Totales informativos
    private double total_intereses;
    private double total_seguros;

    private List<CronogramaPagosDTO> cronograma;

    public int getId_simulacion() {
        return id_simulacion;
    }

    public void setId_simulacion(int id_simulacion) {
        this.id_simulacion = id_simulacion;
    }

    public String getMoneda_simulacion() {
        return moneda_simulacion;
    }

    public void setMoneda_simulacion(String moneda_simulacion) {
        this.moneda_simulacion = moneda_simulacion;
    }

    public double getPrecio_vehiculo_calculado() {
        return precio_vehiculo_calculado;
    }

    public void setPrecio_vehiculo_calculado(double precio_vehiculo_calculado) {
        this.precio_vehiculo_calculado = precio_vehiculo_calculado;
    }

    public double getCuota_inicial() {
        return cuota_inicial;
    }

    public void setCuota_inicial(double cuota_inicial) {
        this.cuota_inicial = cuota_inicial;
    }

    public double getMonto_financiado() {
        return monto_financiado;
    }

    public void setMonto_financiado(double monto_financiado) {
        this.monto_financiado = monto_financiado;
    }

    public double getCuota_balon() {
        return cuota_balon;
    }

    public void setCuota_balon(double cuota_balon) {
        this.cuota_balon = cuota_balon;
    }

    public double getVan() {
        return van;
    }

    public void setVan(double van) {
        this.van = van;
    }

    public double getTir() {
        return tir;
    }

    public void setTir(double tir) {
        this.tir = tir;
    }

    public double getTcea() {
        return tcea;
    }

    public void setTcea(double tcea) {
        this.tcea = tcea;
    }

    public double getTotal_intereses() {
        return total_intereses;
    }

    public void setTotal_intereses(double total_intereses) {
        this.total_intereses = total_intereses;
    }

    public double getTotal_seguros() {
        return total_seguros;
    }

    public void setTotal_seguros(double total_seguros) {
        this.total_seguros = total_seguros;
    }

    public List<CronogramaPagosDTO> getCronograma() {
        return cronograma;
    }

    public void setCronograma(List<CronogramaPagosDTO> cronograma) {
        this.cronograma = cronograma;
    }
}
