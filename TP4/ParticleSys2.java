package TP4;

public class ParticleSys2 {
    private double position,v,prevPosition, prevV;
    private int id;
    public ParticleSys2(double position, double v0,int id) {
        this.position = position;
        this.prevPosition = position;
        this.v = v0;
        this.prevV = v0;
        this.id = id;
    }

    public double getPosition() {
        return position;
    }

    public void setV(double v) {
        this.prevV = this.v;
        this.v = v;
    }

    public int getId(){
        return id;
    }

    public double getV() {
        return v;
    }

    public double getPrevPosition() {
        return prevPosition;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void setPrevPosition(double position){
        this.prevPosition = position;
    }

}
