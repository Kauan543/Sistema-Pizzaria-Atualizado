package com.mycompany.projetofinalpizzaria.Model;

import java.util.ArrayList;

public class Pizza {
    private int idPizza;
    private double area;
    private double valorUnitario;
    private Forma forma;
    private ArrayList<Sabor> sabor;

    public Pizza(double area, Forma forma, ArrayList<Sabor> sabor) {
        this.area = area;
        this.forma = forma;
        this.sabor = sabor;
    }

    public Pizza(double area, double valorUnitario, Forma forma, ArrayList<Sabor> sabor) {
        this(area, forma, sabor);
        this.valorUnitario = valorUnitario;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public ArrayList<Sabor> getSabor() {
        return sabor;
    }

    public void setSabor(ArrayList<Sabor> sabor) {
        this.sabor = sabor;
    }
}