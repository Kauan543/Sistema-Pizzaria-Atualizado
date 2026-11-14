package com.mycompany.projetofinalpizzaria.Model;

public class TipoPizza {
    private int idTipoPizza;
    private String categoria;
    private double precoPorCentimentroQuadrado;

    public TipoPizza(String categoria, double precoPorCentimentroQuadrado) throws Exception {
        validarPreco(precoPorCentimentroQuadrado);
        this.categoria = categoria;
        this.precoPorCentimentroQuadrado = precoPorCentimentroQuadrado;
    }

    public TipoPizza(int idTipoPizza, String categoria, double precoPorCentimentroQuadrado) throws Exception {
        this(categoria, precoPorCentimentroQuadrado);
        this.idTipoPizza = idTipoPizza;
    }

    private void validarPreco(double preco) throws Exception {
        String precoTeste = String.valueOf(preco);
        if (!precoTeste.matches("\\d+(\\.\\d{1,2})?")) {
            throw new Exception("Só permite até dois números depois da vírgula");
        }
    }

    public int getIdTipoPizza() {
        return idTipoPizza;
    }

    public void setIdTipoPizza(int idTipoPizza) {
        this.idTipoPizza = idTipoPizza;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecoPorCentimentroQuadrado() {
        return precoPorCentimentroQuadrado;
    }

    public void setPrecoPorCentimentroQuadrado(double precoPorCentimentroQuadrado) throws Exception {
        validarPreco(precoPorCentimentroQuadrado);
        this.precoPorCentimentroQuadrado = precoPorCentimentroQuadrado;
    }
}