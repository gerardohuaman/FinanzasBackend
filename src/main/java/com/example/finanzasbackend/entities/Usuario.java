package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(name = "username", nullable = false, length = 50)
    private String username; //Por lo general toman el correo corporativo del asesor

    @Column(name = "password", nullable = false, length = 30)
    private String password; // Se guardara encriptado con BCrypt

    @Column(name = "nombreCompleto", nullable = false, length = 150)
    private String nombreCompleto;



    public Usuario() {}

    public Usuario(int id_usuario, String username, String password, String nombreCompleto) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
