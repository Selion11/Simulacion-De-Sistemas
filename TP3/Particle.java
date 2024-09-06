package TP3;

public class Particle {

    private float x, y, vx, vy, r, m;
    private int id;

    public Particle(int id, float x, float y, float vx, float vy, float r, float m) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
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

    public float getM() {
        return m;
    }

    public void setVx(float x) {
        vx = x;
    }

    public void setVy(float v) {
        vy = v;
    }
}

