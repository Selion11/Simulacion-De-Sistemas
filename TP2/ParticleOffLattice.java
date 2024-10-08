package TP2;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class ParticleOffLattice {
    private Integer id;
    private float x,y,rc;
    private double speed;
    private float theta;
    private float oldTheta;
    private float min;
    private float max;

    private ArrayList<ParticleOffLattice> vecinas = new ArrayList<>();
    private Square square;

    public ParticleOffLattice(int id, float x, float y, float theta, float rc,float mu) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.speed = 0.03;
        this.rc = rc;
        this.theta = theta;
        this.min = (float) (-mu/2);
        this.max = (float) (mu/2);
        this.oldTheta = theta;
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


    public float getOldTheta() { return this.oldTheta; }

    public float getRC() {
        return rc;
    }

    public ArrayList<ParticleOffLattice> getVecinas() {
        return vecinas;
    }

    private float getThetaAvg(){
        float sumcos = (float) Math.cos(this.theta);
        float sumsin = (float) Math.sin(this.theta);
        for (ParticleOffLattice p : vecinas) {
            sumcos += (float) Math.cos(p.getOldTheta());
            sumsin += (float) Math.sin(p.getOldTheta());
        }
        return (float) (Math.atan2(sumsin,sumcos));
    }

    public void updateTheta(){
        this.oldTheta = theta;
        float aux = getThetaAvg();
        Random rand = new Random();
        float randomFloat = min + rand.nextFloat() * (max - min);
        aux += randomFloat;
        if(aux > 2){
            this.theta = aux - 2;
        }else{
            this.theta = aux;
        }
    }

    public void updateX(){
        this.x += (float) (Math.cos(theta) * speed);
        if (this.x >= square.getL()){
            this.x -= square.getL();
        }else if(this.x <= 0){
            this.x += square.getL();
        }

    }

    public void updateY(){
        this.y += (float) (Math.sin(theta) * speed);
        if (this.y >= square.getL()){
            this.y -= square.getL();
        }else if(this.y <= 0){
            this.y += square.getL();
        }
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
        return String.format(id +":" + x + ":" + y + ":" + theta + ":" + rc );
    }

    public int getId() {
        return id;
    }
}
