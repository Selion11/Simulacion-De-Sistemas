package TP4;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Particle {
    private float y,a,v;
    private final float m,k,gamma;

    public Particle(float y, int v0, float m,float k,float gamma) {
        this.y = y;
        this.m = m;
        this.v = v0;
        this.k = k;
        this.gamma = gamma;
    }

    public void calculateA(){

    }

    public float getY() {
        return y;
    }

    public float getM() {
        return m;
    }

    public void calculateVelocity(float deltaT){
        v += a * deltaT;
    }

    public void calculatePosition(float deltaT){
        y += v * deltaT;
    }

    @Override
    public String toString() {
        return String.format("Position: " + y );
    }
}
