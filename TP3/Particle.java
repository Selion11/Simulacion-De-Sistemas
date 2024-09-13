package TP3;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Particle {

    private float r, x, y, vx, vy, m;
    private int id;

    public Particle(int id, float x, float y,float theta, float r,int v0, float m) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.r = r;
        this.vx = (float) cos(theta) * v0;
        this.vy = (float) sin(theta) * v0;
        this.m = m;
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

    public float getR() {
        return r;
    }

    public void setVx(float x) {
        vx = x;
    }

    public void setVy(float v) {
        vy = v;
    }

    public float getM() {
        return m;
    }

    @Override
    public String toString() {
        return String.format(id +":" + x + ":" + y + ":" + m );
    }
}

