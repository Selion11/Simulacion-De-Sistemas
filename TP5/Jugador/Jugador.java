package TP5.Jugador;

import java.util.Vector;

public class Jugador {
    protected float posX, posY;
    protected float velX, velY;
    protected float radio;
    protected float velocidadMaxima;

    public Jugador(float posX, float posY, float radio, float velocidadMaxima) {
        this.posX = posX;
        this.posY = posY;
        this.radio = radio;
        this.velocidadMaxima = velocidadMaxima;
        this.velX = 0;
        this.velY = 0;
    }

    public void actualizarPosicion(float deltaTiempo) {
        this.posX += this.velX * deltaTiempo;
        this.posY += this.velY * deltaTiempo;
    }

    public void actualizarVelocidad(float nuevaVelX, float nuevaVelY) {
        float magnitud = (float) Math.sqrt(nuevaVelX * nuevaVelX + nuevaVelY * nuevaVelY);
        if (magnitud > this.velocidadMaxima) {
            this.velX = (nuevaVelX / magnitud) * this.velocidadMaxima;
            this.velY = (nuevaVelY / magnitud) * this.velocidadMaxima;
        } else {
            this.velX = nuevaVelX;
            this.velY = nuevaVelY;
        }
    }

    public float getPosX() { return posX; }
    public float getPosY() { return posY; }
    public float getVelX() { return velX; }
    public float getVelY() { return velY; }
    public float getRadio() { return radio; }

    public float getVelocidadMaxima() { return velocidadMaxima; }
}
