package TP5.Jugador;

import java.util.Vector;
import TP5.helpers.Acceleration;

public class Jugador {
    protected double posX, posY;
    protected double velX, velY;

    protected double targetX, targetY;
    protected double radio;
    protected double velocidadMaxima;
    protected double weight;
    protected double tau;
    public Acceleration oldAcceleration;

    public Jugador(double posX, double posY, double radio, double velocidadMaxima,double weight,double tau) {
        this.posX = posX;
        this.posY = posY;
        this.radio = radio;
        this.velocidadMaxima = velocidadMaxima;
        this.velX = 0;
        this.velY = 0;
        this.weight = weight;
        this.tau = tau;
        this.oldAcceleration = new Acceleration();
    }


    /**
     * @param currentAccY: aceleracion actual componente en y
     * @param currentAccX: aceleracion actual componente en x
     * @param dt: delta de tiempo para utilizar beeman
     */
    public void beemanIntegration(double currentAccX, double currentAccY, double dt){
        double oldPosX = posX;
        double oldPosY = posY;
        double oldVelX = velX;
        double oldVelY = velY;

        // Beeman integration for position
        // x(t + Δt) = x(t) + v(t)Δt + (2/3)a(t)Δt² - (1/6)a(t-Δt)Δt²
        posX = oldPosX + oldVelX * dt + (2.0/3.0) * currentAccX * dt * dt - (1.0/6.0) * oldAcceleration.getX() * dt * dt;

        posY = oldPosY + oldVelY * dt + (2.0/3.0) * currentAccY * dt * dt - (1.0/6.0) * oldAcceleration.getY() * dt * dt;

        // Beeman integration for velocity
        // v(t + Δt) = v(t) + (1/3)a(t + Δt)Δt + (5/6)a(t)Δt - (1/6)a(t-Δt)Δt
        velX = oldVelX + (1.0/3.0) * currentAccX * dt + (5.0/6.0) * currentAccX * dt - (1.0/6.0) * oldAcceleration.getX() * dt;

        velY = oldVelY + (1.0/3.0) * currentAccY * dt + (5.0/6.0) * currentAccY * dt - (1.0/6.0) * oldAcceleration.getY() * dt;

        // Actualizo la accel vieja a ser la que se acaba de usar para integrar
        oldAcceleration.setX(currentAccX);
        oldAcceleration.setY(currentAccY);
    }

    public double getPosX() { return posX; }
    public double getPosY() { return posY; }
    public double getVelX() { return velX; }
    public double getVelY() { return velY; }
    public double getRadio() { return radio; }
    public void setPosX(double posX) { this.posX = posX; }
    public void setPosY(double posY) { this.posY = posY; }

    public double getTargetX() { return targetX; }

    public double getTargetY() { return targetY; }

    public void setTarget(double x, double y){
        targetX = x;
        targetY = y;
    }

    public double getVelocidadMaxima() { return velocidadMaxima; }
    public double getWeight(){ return weight; }
    public double getTau() { return tau; }
}
