package com.example.finanzasbackend.dtos;

import java.time.LocalDate;

public class CronogramaPagosDTO {
    private int numero_mes;
    private LocalDate fecha_pago;
    private double saldo_inicial;
    private double amortizacion;
    private double interes;
    private double seguro_desgravamen;
    private double seguro_vehicular;
    private double cuota_total;
    private double saldo_final;

    public int getNumero_mes() {
        return numero_mes;
    }

    public void setNumero_mes(int numero_mes) {
        this.numero_mes = numero_mes;
    }

    public LocalDate getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public double getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(double saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public double getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(double amortizacion) {
        this.amortizacion = amortizacion;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public double getSeguro_desgravamen() {
        return seguro_desgravamen;
    }

    public void setSeguro_desgravamen(double seguro_desgravamen) {
        this.seguro_desgravamen = seguro_desgravamen;
    }

    public double getSeguro_vehicular() {
        return seguro_vehicular;
    }

    public void setSeguro_vehicular(double seguro_vehicular) {
        this.seguro_vehicular = seguro_vehicular;
    }

    public double getCuota_total() {
        return cuota_total;
    }

    public void setCuota_total(double cuota_total) {
        this.cuota_total = cuota_total;
    }

    public double getSaldo_final() {
        return saldo_final;
    }

    public void setSaldo_final(double saldo_final) {
        this.saldo_final = saldo_final;
    }
}
