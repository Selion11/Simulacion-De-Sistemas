package TP4.algorithms;

public interface Algorithm {
    public void runAlgorithm();
    default double OscillatorForce(double r, double v, double m, double k, double gamma) {
        return (-k*r -gamma*v)/m;
    }

    default double EulerPosition(double r, double v, double a, double dt) {
        return r + dt*v + dt*dt*a/2;
    }

    default double EulerVelocity(double v, double a, double dt) {
        return v + dt*a;
    }

    default double GroupOscillatorForce(double k, double y, double prevY,double nextY){
        return-k *(y-prevY) - k * (y-nextY);
    }


}
