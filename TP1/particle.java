package TP1;

public class particle {
    private float x,y,r,rc;
    int id;

    public particle(){
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRc(float rc) {
        this.rc = rc;
    }

    public float getRc() {
        return rc;
    }

    public int getId() {
        return id;
    }

    public float getX(){
        return x;
    }

    public float getR() {
        return r;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("TP1.particle " + id +"{ "+ "x=" + x + ", y=" + y + ", r=" + r + '}');
    }
}
