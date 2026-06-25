package com.example.finanzasbackend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username; //Por lo general toman el correo corporativo del asesor

    @Column(name = "password", nullable = false, length = 200)
    private String password; // Se guardara encriptado con BCrypt

    @Column(name = "nombreCompleto", nullable = false, length = 150)
    private String nombreCompleto;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<Rol> roles;
    @Column(nullable = false)
    private Boolean enabled;
    public Usuario() {}

    public Usuario(int id_usuario, String username, String password, String nombreCompleto, List<Rol> roles, Boolean enabled) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.roles = roles;
        this.enabled = enabled;
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
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
