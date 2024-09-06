package TP3;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Particle {

    private float x, y, vx, vy;
    private int id;
    private double r;

    public Particle(int id, float x, float y,float theta, double r,int v0) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.r = r;
        this.vx = (float) cos(theta) * v0;
        this.vy = (float) sin(theta) * v0;
    }

    public void move(float t) {
        x += vx * t;
        y += vy * t;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public double getR() {
        return r;
    }

    public void setVx(float x) {
        vx = x;
    }

    public void setVy(float v) {
        vy = v;
    }
}

