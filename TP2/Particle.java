package TP2;


import java.util.ArrayList;
import java.util.Objects;


public class Particle {
    private Integer id;
    private float x,y,r,rc;
    private ArrayList<Particle> vecinas = new ArrayList<>();
    private Square square;

    public Particle(int id, float x, float y, float r, float rc) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.rc = rc;
    }

    public void set_square(Square s) {
        this.square = s;
    }
    public void checkVecinas() {
        for(Square s : square.getInvertedL()) {
            for (Particle p : s.getParticles()) {
                if (p != this) {
                    checkVecina(p,s.getVirtualX(),s.getVirtualY());
                }
            }
        }
    }

    public void checkVecina(Particle p, Integer virtualX, Integer virtualY){
        double x_diff = Math.pow(this.x - (p.getX() + virtualX),2);
        double y_diff = Math.pow(this.y - (p.getY() + virtualY),2);
        double ans = Math.sqrt(x_diff+y_diff) - (r+p.getR()) - rc;
        if(ans <= 0){
            add_vecina(p);
        }
    }

    public void add_vecina(Particle p){
        if (!vecinas.contains(p)) {
            vecinas.add(p);
            p.add_vecina(this);
        }
    }

    public int getSquareId() {
        return square.getId();
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

    public float getRC() {
        return rc;
    }

    public ArrayList<Particle> getVecinas() {
        return vecinas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Particle other = (Particle) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return this.r == other.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }


    @Override
    public String toString() {
        return id.toString();
    }

    public int getId() {
        return id;
    }
}
