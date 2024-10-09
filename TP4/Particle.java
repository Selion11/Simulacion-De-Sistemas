package TP4;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Particle {
    private double position,a,v,prevPosition, prevV;
    private final double m,k,gamma,timeStep;

    public Particle(double position, double v0, double m,double k,double gamma,double timeStep) {
        this.position = position;
        this.prevPosition = position;
        this.m = m;
        this.v = v0;
        this.prevV = v0;
        this.k = k;
        this.gamma = gamma;
        this.timeStep = timeStep;
    }

    public double getPosition() {
        return position;
    }

    public double getM() {
        return m;
    }

    public void setV(double v) {
        this.prevV = this.v;
        this.v = v;
    }

    public double getV() {
        return v;
    }

    public double getPrevPosition() {
        return prevPosition;
    }
    public double getPrevV(){
        return prevV;
    }

    public void setPosition(double position) {
        this.prevPosition = this.position;
        this.position = position;
    }

    public void setPrevPosition(double position){
        this.prevPosition = position;
    }

    public double getK() {
        return k;
    }

    public double getGamma() {
        return gamma;
    }

    public double getTimeStep() {
        return timeStep;
    }

    @Override
    public String toString() {
        return String.format("Position: " + position );
    }
}
