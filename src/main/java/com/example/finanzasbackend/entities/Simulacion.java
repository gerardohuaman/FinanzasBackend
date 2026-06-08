package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "simulacion")
public class Simulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_simulacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cambio")
    private Historial_TipoCambio historialTipoCambio;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fecha_registro;

    @Column(name = "porcentaje_inicial", nullable = false)
    private double porcentaje_inicial;

    @Column(name = "porcentaje_balon", nullable = false)
    private double porcentaje_balon;

    @Column(name = "plazo_meses", nullable = false)
    private int plazo_meses;

    @Column(name = "tipo_tasa", nullable = false, length = 100)
    private String tipo_tasa;

    @Column(name = "valor_tasa", nullable = false)
    private double valor_tasa;

    @Column(name = "capitalizacion", length = 100)
    private String capitalizacion;

    @Column(name = "meses_gracia", nullable = false)
    private int meses_gracia;

    @Column(name = "tipo_gracia", nullable = false, length = 100)
    private String tipo_gracia;

    @Column(name = "tasa_desgravamen", nullable = false)
    private double tasa_desgravamen;

    @Column(name = "tasa_vehicular", nullable = false)
    private double tasa_vehicular;

    @Column(name = "van", nullable = false)
    private double van;

    @Column(name = "tir", nullable = false)
    private double tir;

    @Column(name = "cok", nullable = false)
    private double cok;

    @Column(name = "tcea", nullable = false)
    private double tcea;

    public Simulacion(){}

    public Simulacion(int id_simulacion, Usuario usuario, Cliente cliente, Vehiculo vehiculo, Historial_TipoCambio historialTipoCambio, LocalDate fecha_registro, double porcentaje_inicial, double porcentaje_balon, int plazo_meses, String tipo_tasa, double valor_tasa, String capitalizacion, int meses_gracia, String tipo_gracia, double tasa_desgravamen, double tasa_vehicular, double van, double tir, double cok, double tcea) {
        this.id_simulacion = id_simulacion;
        this.usuario = usuario;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.historialTipoCambio = historialTipoCambio;
        this.fecha_registro = fecha_registro;
        this.porcentaje_inicial = porcentaje_inicial;
        this.porcentaje_balon = porcentaje_balon;
        this.plazo_meses = plazo_meses;
        this.tipo_tasa = tipo_tasa;
        this.valor_tasa = valor_tasa;
        this.capitalizacion = capitalizacion;
        this.meses_gracia = meses_gracia;
        this.tipo_gracia = tipo_gracia;
        this.tasa_desgravamen = tasa_desgravamen;
        this.tasa_vehicular = tasa_vehicular;
        this.van = van;
        this.tir = tir;
        this.cok = cok;
        this.tcea = tcea;
    }

    public int getId_simulacion() {
        return id_simulacion;
    }

    public void setId_simulacion(int id_simulacion) {
        this.id_simulacion = id_simulacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Historial_TipoCambio getHistorialTipoCambio() {
        return historialTipoCambio;
    }

    public void setHistorialTipoCambio(Historial_TipoCambio historialTipoCambio) {
        this.historialTipoCambio = historialTipoCambio;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public double getPorcentaje_inicial() {
        return porcentaje_inicial;
    }

    public void setPorcentaje_inicial(double porcentaje_inicial) {
        this.porcentaje_inicial = porcentaje_inicial;
    }

    public double getPorcentaje_balon() {
        return porcentaje_balon;
    }

    public void setPorcentaje_balon(double porcentaje_balon) {
        this.porcentaje_balon = porcentaje_balon;
    }

    public int getPlazo_meses() {
        return plazo_meses;
    }

    public void setPlazo_meses(int plazo_meses) {
        this.plazo_meses = plazo_meses;
    }

    public String getTipo_tasa() {
        return tipo_tasa;
    }

    public void setTipo_tasa(String tipo_tasa) {
        this.tipo_tasa = tipo_tasa;
    }

    public double getValor_tasa() {
        return valor_tasa;
    }

    public void setValor_tasa(double valor_tasa) {
        this.valor_tasa = valor_tasa;
    }

    public String getCapitalizacion() {
        return capitalizacion;
    }

    public void setCapitalizacion(String capitalizacion) {
        this.capitalizacion = capitalizacion;
    }

    public int getMeses_gracia() {
        return meses_gracia;
    }

    public void setMeses_gracia(int meses_gracia) {
        this.meses_gracia = meses_gracia;
    }

    public String getTipo_gracia() {
        return tipo_gracia;
    }

    public void setTipo_gracia(String tipo_gracia) {
        this.tipo_gracia = tipo_gracia;
    }

    public double getTasa_desgravamen() {
        return tasa_desgravamen;
    }

    public void setTasa_desgravamen(double tasa_desgravamen) {
        this.tasa_desgravamen = tasa_desgravamen;
    }

    public double getTasa_vehicular() {
        return tasa_vehicular;
    }

    public void setTasa_vehicular(double tasa_vehicular) {
        this.tasa_vehicular = tasa_vehicular;
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

    public double getCok() {
        return cok;
    }

    public void setCok(double cok) {
        this.cok = cok;
    }

    public double getTcea() {
        return tcea;
    }

    public void setTcea(double tcea) {
        this.tcea = tcea;
    }
}