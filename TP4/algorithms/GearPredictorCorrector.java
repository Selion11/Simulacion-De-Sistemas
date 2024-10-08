package TP4.algorithms;
import TP4.Particle;

public class GearPredictorCorrector implements Algorithm {

    private double [] rs;

    private final Particle p;

    private final double [] coeff = new double[]{3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};

    public GearPredictorCorrector(Particle p) {

        this.p = p;

        double m = p.getM();
        double k = p.getK();
        double gamma = p.getGamma();

        double r2 = OscillatorForce(p.getPosition(), p.getV(),m,k,gamma);
        double r3 = OscillatorForce(p.getV(),r2,m,k,gamma);
        double r4 = OscillatorForce(r2,r3,m,k,gamma);
        double r5 = OscillatorForce(r3,r4,m,k,gamma);

        this.rs = new double[]{p.getPosition(),p.getV(),r2,r3,r4,r5};
    }

    @Override
    public void runAlgorithm() {

        double dt = p.getTimeStep();

        double r0 = predict(dt, rs[0], rs[1], rs[2], rs[3], rs[4], rs[5]);
        double r1 = predict(dt, rs[1], rs[2], rs[3], rs[4], rs[5], 0.0);
        double r2 = predict(dt,  rs[2], rs[3], rs[4], rs[5], 0.0, 0.0);
        double r3 = predict(dt, rs[3], rs[4], rs[5], 0.0, 0.0, 0.0);
        double r4 = predict(dt, rs[4], rs[5], 0.0, 0.0, 0.0, 0.0);
        double r5 = predict(dt, rs[5], 0.0, 0.0, 0.0, 0.0, 0.0);

        double a = OscillatorForce(r0, r1, p.getM(), p.getK(), p.getGamma());
        double R2 = (a - r2)*dt*dt/2;

        rs = correct(dt, r0, r1, r2, r3, r4, r5, R2);

        p.setPosition((float) rs[0]);
    }

    private double predict(double dt, double t0, double t1, double t2, double t3, double t4, double t5) {

        double tt1, tt2, tt3, tt4, tt5;
        double fact = 1;

        tt1 = t1*dt;
        fact *= 2;
        tt2 = t2*Math.pow(dt, 2)/fact;
        fact *= 3;
        tt3 = t3*Math.pow(dt, 3)/fact;
        fact *= 4;
        tt4 = t4*Math.pow(dt, 4)/fact;
        fact *= 5;
        tt5 = t5*Math.pow(dt, 5)/fact;

        return t0 + tt1 + tt2 + tt3 + tt4 + tt5;
    }

    private double[] correct(double dt,double p0, double p1, double p2, double p3, double p4, double p5, double R2) {
        double r0, r1, r2, r3, r4, r5;
        double fact = 1;

        r0 = p0 + coeff[0]*R2;
        r1 = p1 + coeff[1]*R2/dt;
        fact *= 2;
        r2 = p2 + coeff[2]*R2*fact/Math.pow(dt, 2);
        fact *= 3;
        r3 = p3 + coeff[3]*R2*fact/Math.pow(dt, 3);
        fact *= 4;
        r4 = p4 + coeff[4]*R2*fact/Math.pow(dt, 4);
        fact *= 5;
        r5 = p5 + coeff[5]*R2*fact/Math.pow(dt, 5);

        return new double[]{r0, r1, r2, r3, r4, r5};
    }

}


