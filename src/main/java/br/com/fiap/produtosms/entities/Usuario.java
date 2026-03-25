package br.com.fiap.produtosms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    private String login;

    private String nome;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String role;

    public Usuario() {
    }

    public Usuario(String login, String nome, String avatarUrl, String role) {
        this.login = login;
        this.nome = nome;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}