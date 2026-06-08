package com.example.finanzasbackend.dtos;

public class SimulacionInputDTO {
    // Relaciones
    private int id_usuario;
    private int id_cliente;
    private int id_vehiculo;
    private Integer id_tipo_cambio;

    // Configuración del Crédito
    private double porcentaje_inicial; // Ej: 20.0 (para 20%)
    private double porcentaje_balon;   // Ej: 40.0
    private int plazo_meses;           // Ej: 24, 36 meses

    // Configuración de Tasas
    private String tipo_tasa;       // "TEA", "TNA", "TEM", "TNM"
    private double valor_tasa;      // Ej: 12.5 (12.5%)
    private String capitalizacion;

    // Periodos de Gracia
    private int meses_gracia;
    private String tipo_gracia;     // "TOTAL", "PARCIAL", "SIN_GRACIA"

    // Seguros y Costos
    private double tasa_desgravamen; // % Mensual
    private double tasa_vehicular;   // % Anual comercial

    // Costo de Oportunidad (Falta en tu BD)
    private double cok;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public Integer getId_tipo_cambio() {
        return id_tipo_cambio;
    }

    public void setId_tipo_cambio(Integer id_tipo_cambio) {
        this.id_tipo_cambio = id_tipo_cambio;
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

    public double getCok() {
        return cok;
    }

    public void setCok(double cok) {
        this.cok = cok;
    }
}
