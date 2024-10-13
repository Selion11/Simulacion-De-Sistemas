package TP4.algorithms;

public interface Algorithm {
    public void runAlgorithm();
    public void runAlgorithm2(double time);
    default double OscillatorForce(double r, double v, double m, double k, double gamma) {
        return (-k*r -gamma*v)/m;
    }

    default double EulerPosition(double r, double v, double a, double dt) {
        return r + dt*v + dt*dt*a/2;
    }

    default double EulerVelocity(double v, double a, double dt) {
        return v + dt*a;
    }

    //TODO REVISAR CUENTA
    default double calculateForce(double curr,double next,double prev,double k,double m){
        double a =  -k*(curr-prev) -k*(curr-next);
        if (Double.isInfinite(a) || Double.isNaN(a)) {
            return 0;
        }
        return a;
    }

    //TODO REVISAR CUENTA
    default double lastParticle(double amplitud, double omega,double time){
        return amplitud * Math.sin(omega * time);
    }

}
