package com.mycompany.projetofinalpizzaria.Model;

public class Cliente {
    private int idCliente;
    private String nome;
    private String sobrenome;
    private String telefone;

    public Cliente(String nome, String sobrenome, String telefone) throws Exception {
        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone deve conter apenas números e ter entre 10 e 11 caracteres");
        } else if (!nome.matches("[a-zA-ZÀ-ÿ]+") || !sobrenome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            throw new Exception("Nome e Sobrenome só permitem letras");
        }
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
    }

    public Cliente(int idCliente, String nome, String sobrenome, String telefone) throws Exception {
        this(nome, sobrenome, telefone);
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.matches("[a-zA-ZÀ-ÿ]+")) {
            this.nome = nome;
        } else {
            throw new Exception("Nome e Sobrenome só permitem letras");
        }
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws Exception {
        if (sobrenome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            this.sobrenome = sobrenome;
        } else {
            throw new Exception("Nome e Sobrenome só permitem letras");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone.matches("\\d{10,11}")) {
            this.telefone = telefone;
        } else {
            throw new IllegalArgumentException("Telefone deve conter apenas números e ter entre 10 e 11 caracteres");
        }
    }
}