package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "moneda")
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_moneda;

    @Column(name = "nombre", nullable = false, length = 15)
    private String nombre;

    @Column(name = "simbolo", nullable = false, length = 5)
    private String simbolo; // S/ - $

    @Column(name = "codigo_iso", nullable = false, length = 5)
    private String codigo_iso; //PEN - USD

    public Moneda(){}

    public Moneda(int id_moneda, String nombre, String simbolo, String codigo_iso) {
        this.id_moneda = id_moneda;
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.codigo_iso = codigo_iso;
    }

    public int getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(int id_moneda) {
        this.id_moneda = id_moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getCodigo_iso() {
        return codigo_iso;
    }

    public void setCodigo_iso(String codigo_iso) {
        this.codigo_iso = codigo_iso;
    }
}
