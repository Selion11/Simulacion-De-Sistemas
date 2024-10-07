package TP4;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Particle {
    private float x, y, vx, vy, m;
    private int id;

    public Particle(int id, float x, float y,float theta, int v0, float m) {
        this.x = x;
        this.y = y;
        this.id = id;
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

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public float getM() {
        return m;
    }

    @Override
    public String toString() {
        return String.format(id +":" + x + ":" + y );
    }
}
