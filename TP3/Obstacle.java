package TP3;

public class Obstacle {

    private float x,y,r;

    public Obstacle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public float getDCM(double L){
        return (float) Math.sqrt(Math.pow(x-L/2,2) + Math.pow(y-L/2, 2));
    }

}
