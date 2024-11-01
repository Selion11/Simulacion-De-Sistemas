package TP5.helpers;

public class Acceleration {
    protected double xComponent;
    protected double yComponent;
    public Acceleration(){
        this.xComponent = 0;
        this.yComponent = 0;
    }

    public double getX() {return xComponent; }
    public void setX(double xComponent) {this.xComponent = xComponent;}

    public double getY() {return yComponent;}
    public void setY(double yComponent) {this.yComponent = yComponent;}
}
