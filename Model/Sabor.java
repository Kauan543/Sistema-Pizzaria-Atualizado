package com.mycompany.projetofinalpizzaria.Model;

public class Sabor {
    private int idSabor;
    private String sabor;
    private TipoPizza tipo;

    public Sabor(String sabor) {
        this.sabor = sabor;
    }

    public Sabor(String sabor, TipoPizza tipo) throws Exception {
        if (!sabor.matches("[a-zA-ZÀ-ÿ ]+")) {
            throw new Exception("Sabor só permite letras");
        }
        this.sabor = sabor;
        this.tipo = tipo;
    }

    public Sabor(int idSabor, String sabor, TipoPizza tipo) throws Exception {
        this(sabor, tipo);
        this.idSabor = idSabor;
    }

    public int getIdSabor() {
        return idSabor;
    }

    public void setIdSabor(int idSabor) {
        this.idSabor = idSabor;
    }

    public TipoPizza getTipo() {
        return tipo;
    }

    public void setTipo(TipoPizza tipo) {
        this.tipo = tipo;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) throws Exception {
        if (!sabor.matches("[a-zA-ZÀ-ÿ ]+")) {
            throw new Exception("Sabor só permite letras");
        }
        this.sabor = sabor;
    }
}