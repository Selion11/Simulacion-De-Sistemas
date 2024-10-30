package TP5.Jugador;

import java.util.Vector;

public class Jugador {
    protected double posX, posY;
    protected double velX, velY;
    protected double radio;
    protected double velocidadMaxima;

    public Jugador(double posX, double posY, double radio, double velocidadMaxima) {
        this.posX = posX;
        this.posY = posY;
        this.radio = radio;
        this.velocidadMaxima = velocidadMaxima;
        this.velX = 0;
        this.velY = 0;
    }

    public void actualizarPosicion(double deltaTiempo) {
        this.posX += this.velX * deltaTiempo;
        this.posY += this.velY * deltaTiempo;
    }

    public void actualizarVelocidad(double nuevaVelX, double nuevaVelY) {
        double magnitud =  Math.sqrt(nuevaVelX * nuevaVelX + nuevaVelY * nuevaVelY);
        if (magnitud > this.velocidadMaxima) {
            this.velX = (nuevaVelX / magnitud) * this.velocidadMaxima;
            this.velY = (nuevaVelY / magnitud) * this.velocidadMaxima;
        } else {
            this.velX = nuevaVelX;
            this.velY = nuevaVelY;
        }
    }

    public double getPosX() { return posX; }
    public double getPosY() { return posY; }
    public double getVelX() { return velX; }
    public double getVelY() { return velY; }
    public double getRadio() { return radio; }

    public double getVelocidadMaxima() { return velocidadMaxima; }
}
