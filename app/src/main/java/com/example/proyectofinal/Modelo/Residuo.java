package com.example.proyectofinal.Modelo;

public class Residuo {
    private int id;
    private String tipo;
    private double peso;

    public Residuo(int id, String tipo, double  peso) {
        this.id = id;
        this.tipo = tipo;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public double  getPeso() {
        return peso;
    }
}
