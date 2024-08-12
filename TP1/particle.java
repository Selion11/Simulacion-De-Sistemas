package TP1;


import java.util.ArrayList;

public class particle {
    private float x,y,r,rc;
    private ArrayList<particle> vecinas = new ArrayList<>();
    private square square;

    public particle(float x, float y, float r, float rc) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.rc = rc;
    }

    public void set_square(square s) {
        this.square = s;
    }
    private void checkVecinas() {
        for(square s : square.getInvertedL()) {
            for(particle p : s.getParticles()) {
                if(p != this) {
                    checkVecina(p);
                }
            }
        }
    }

    public void checkVecina(particle p){
        double x_diff = Math.pow(this.x - p.getX(),2);
        double y_diff = Math.pow(this.y - p.getY(),2);
        double ans = Math.sqrt(x_diff+y_diff) - (r+p.getR()) - rc;
        //TODO CONTEMPLAR AJUSTE DE COORDENADAS PARA LOS CASOS DE PARTICULAS VIRTUALES
        if(ans <= 0){
            add_vecina(p);
        }
    }

    public void add_vecina(particle p){
        vecinas.add(p);
        p.add_vecina(this);
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

    public ArrayList<particle> getVecinas() {
        return vecinas;
    }

    @Override
    public String toString() {
        return String.format("TP1.particle "+"{ "+ "x=" + x + ", y=" + y + ", r=" + r + '}');
    }
}
