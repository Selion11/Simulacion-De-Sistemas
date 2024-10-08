package TP4;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Particle {
    private float position,a,v,prevPosition, prevV;
    private final float m,k,gamma,timeStep;

    public Particle(float position, float v0, float m,float k,float gamma,float timeStep) {
        this.position = position;
        this.prevPosition = position;
        this.m = m;
        this.v = v0;
        this.prevV = v0;
        this.k = k;
        this.gamma = gamma;
        this.timeStep = timeStep;
    }

    public float getPosition() {
        return position;
    }

    public float getM() {
        return m;
    }

    public void setV(float v) {
        this.prevV = this.v;
        this.v = v;
    }

    public float getV() {
        return v;
    }

    public float getPrevPosition() {
        return prevPosition;
    }
    public float getPrevV(){
        return prevV;
    }

    public void setPosition(float position) {
        this.prevPosition = this.position;
        this.position = position;
    }

    public void setPrevPosition(float position){
        this.prevPosition = position;
    }

    public float getK() {
        return k;
    }

    public float getGamma() {
        return gamma;
    }

    public float getTimeStep() {
        return timeStep;
    }

    @Override
    public String toString() {
        return String.format("Position: " + position );
    }
}
