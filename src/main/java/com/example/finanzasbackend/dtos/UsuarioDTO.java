package com.example.finanzasbackend.dtos;

import com.example.finanzasbackend.entities.Rol;
import jakarta.persistence.*;

import java.util.List;

public class UsuarioDTO {
    private int id_usuario;
    private String username; //Por lo general toman el correo corporativo del asesor
    private String password; // Se guardara encriptado con BCrypt
    private String nombreCompleto;
    private List<Rol> roles;

    public UsuarioDTO() {}

    public UsuarioDTO(int id_usuario, String username, String password, String nombreCompleto, List<Rol> roles) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.roles = roles;
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
    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
