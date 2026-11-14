package com.mycompany.projetofinalpizzaria.Model;

public class Estado {
    private int idEstado;
    private String nome;

    public Estado(String nome) {
        this.nome = nome;
    }

    public Estado(int idEstado, String nome) {
        this.idEstado = idEstado;
        this.nome = nome;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}