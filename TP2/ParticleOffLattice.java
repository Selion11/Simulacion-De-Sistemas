package TP2;


import java.util.ArrayList;
import java.util.Objects;


public class ParticleOffLattice {
    private Integer id;
    private float x,y,rc;

    private Velocity actualV, nextV;
    private ArrayList<ParticleOffLattice> vecinas = new ArrayList<>();
    private Square square;

    public ParticleOffLattice(int id, float x, float y, float vx, float vy, float rc) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.actualV = new Velocity(vx, vy);
        this.nextV = new Velocity(0,0);
        this.rc = rc;
    }

    public void set_square(Square s) {
        this.square = s;
    }
    public void checkVecinas() {
        for(Square s : square.getInvertedL()) {
            for (ParticleOffLattice p : s.getParticles()) {
                if (p != this) {
                    checkVecina(p,s.getVirtualX(),s.getVirtualY());
                }
            }
        }
    }

    public void checkVecina(ParticleOffLattice p, Integer virtualX, Integer virtualY){
        double x_diff = Math.pow(this.x - (p.getX() + virtualX),2);
        double y_diff = Math.pow(this.y - (p.getY() + virtualY),2);
        double ans = Math.sqrt(x_diff+y_diff) - rc;
        if(ans <= 0){
            add_vecina(p);
        }
    }

    public void add_vecina(ParticleOffLattice p){
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


    public float getY() {
        return y;
    }



    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Velocity getActualV() {
        return actualV;
    }

    public Velocity getNextV() {
        return nextV;
    }

    public void setNextV(Velocity v) {
        this.nextV = v;
    }

    public void updateVel() {
        this.actualV = this.nextV;
    }

    public double getTheta() {
        return Math.atan(actualV.getVy()/actualV.getVx());
    }

    public float getRC() {
        return rc;
    }

    public ArrayList<ParticleOffLattice> getVecinas() {
        return vecinas;
    }

    private float getSinAvg() {
        float sum = 0;
        for (ParticleOffLattice p : vecinas) {
            sum += (float) Math.sin(p.getTheta());
        }
        return sum/vecinas.size();
    }

    private float getCosAvg() {
        float sum = 0;
        for (ParticleOffLattice p : vecinas) {
            sum += (float) Math.cos(p.getTheta());
        }
        return sum/vecinas.size();
    }

    public double getThetaAvg() {
        return Math.atan(getSinAvg()/getCosAvg());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParticleOffLattice other = (ParticleOffLattice) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return id.toString();
    }

    public int getId() {
        return id;
    }
}
