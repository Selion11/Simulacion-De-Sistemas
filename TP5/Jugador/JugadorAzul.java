package TP5.Jugador;

import TP5.Utils;

public class JugadorAzul extends Jugador {
    int id;
    public JugadorAzul(double x, double y, double radio,double velocidadMaxima,double weight,double tau,int id) {
        super(x, y, velocidadMaxima, radio,weight,tau);
        this.id = id;
    }


    public int getId() {
        return id;
    }
}

