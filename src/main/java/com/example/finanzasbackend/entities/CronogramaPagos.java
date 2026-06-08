package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cronograma_pagos")
public class CronogramaPagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cuota;

    @ManyToOne
    @JoinColumn(name = "id_simulacion")
    private Simulacion simulacion;

    @Column(name = "numero_mes", nullable = false)
    private int numero_mes;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fecha_pago;

    @Column(name = "saldo_inicial", nullable = false)
    private double saldo_inicial;

    @Column(name = "amortizacion", nullable = false)
    private double amortizacion;

    @Column(name = "interes", nullable = false)
    private double interes;

    @Column(name = "s_desgravamen", nullable = false)
    private double s_desgravamen;

    @Column(name = "s_vehicular", nullable = false)
    private double s_vehicular;

    @Column(name = "cuota_total", nullable = false)
    private double cuota_total;

    @Column(name = "saldo_final", nullable = false)
    private double saldo_final;

    public CronogramaPagos(){}

    public CronogramaPagos(int id_cuota, Simulacion simulacion, int numero_mes, LocalDate fecha_pago, double saldo_inicial, double amortizacion, double interes, double s_desgravamen, double s_vehicular, double cuota_total, double saldo_final) {
        this.id_cuota = id_cuota;
        this.simulacion = simulacion;
        this.numero_mes = numero_mes;
        this.fecha_pago = fecha_pago;
        this.saldo_inicial = saldo_inicial;
        this.amortizacion = amortizacion;
        this.interes = interes;
        this.s_desgravamen = s_desgravamen;
        this.s_vehicular = s_vehicular;
        this.cuota_total = cuota_total;
        this.saldo_final = saldo_final;
    }

    public int getId_cuota() {
        return id_cuota;
    }

    public void setId_cuota(int id_cuota) {
        this.id_cuota = id_cuota;
    }

    public Simulacion getSimulacion() {
        return simulacion;
    }

    public void setSimulacion(Simulacion simulacion) {
        this.simulacion = simulacion;
    }

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

    public double getS_desgravamen() {
        return s_desgravamen;
    }

    public void setS_desgravamen(double s_desgravamen) {
        this.s_desgravamen = s_desgravamen;
    }

    public double getS_vehicular() {
        return s_vehicular;
    }

    public void setS_vehicular(double s_vehicular) {
        this.s_vehicular = s_vehicular;
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
