package com.davidpopayan.sena.emparejaapp;

public class Score {
    //Declaración de variables
    private String nombre;
    private int puntaje, modo, dificultad;

    //Constructor vacio
    public Score() {
    }

    //Getter and Setter para las variables de la clase Score
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }
}
