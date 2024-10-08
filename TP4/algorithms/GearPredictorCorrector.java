package TP4.algorithms;
import TP4.Particle;

public class GearPredictorCorrector implements Algorithm {

    private float [] rs;

    private final Particle p;

    private final float [] coeff = new float[]{3.0f/16, 251.0f/360, 1, 11.0f/18, 1.0f/6, 1.0f/60};

    public GearPredictorCorrector(Particle p) {

        this.p = p;

        float m = p.getM();
        float k = p.getK();
        float gamma = p.getGamma();

        float r2 = OscillatorForce(p.getPosition(), p.getV(),m,k,gamma);
        float r3 = OscillatorForce(p.getV(),r2,m,k,gamma);
        float r4 = OscillatorForce(r2,r3,m,k,gamma);
        float r5 = OscillatorForce(r3,r4,m,k,gamma);

        this.rs = new float[]{p.getPosition(),p.getV(),r2,r3,r4,r5};
    }

    @Override
    public void runAlgorithm() {

        float dt = p.getTimeStep();

        float r0 = predict(dt, rs[0], rs[1], rs[2], rs[3], rs[4], rs[5]);
        float r1 = predict(dt, rs[1], rs[2], rs[3], rs[4], rs[5], 0.0f);
        float r2 = predict(dt,  rs[2], rs[3], rs[4], rs[5], 0.0f, 0.0f);
        float r3 = predict(dt, rs[3], rs[4], rs[5], 0.0f, 0.0f, 0.0f);
        float r4 = predict(dt, rs[4], rs[5], 0.0f, 0.0f, 0.0f, 0.0f);
        float r5 = predict(dt, rs[5], 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        float a = OscillatorForce(r0, r1, p.getM(), p.getK(), p.getGamma());
        float R2 = (a - r2)*dt*dt/2;

        rs = correct(dt, r0, r1, r2, r3, r4, r5, R2);

        p.setPosition((float) rs[0]);
    }

    private float predict(float dt, float t0, float t1, float t2, float t3, float t4, float t5) {

        float tt1, tt2, tt3, tt4, tt5;
        float fact = 1;

        tt1 = t1*dt;
        fact *= 2;
        tt2 = (float) (t2*Math.pow(dt, 2)/fact);
        fact *= 3;
        tt3 = (float) (t3*Math.pow(dt, 3)/fact);
        fact *= 4;
        tt4 = (float) (t4*Math.pow(dt, 4)/fact);
        fact *= 5;
        tt5 = (float) (t5*Math.pow(dt, 5)/fact);

        return t0 + tt1 + tt2 + tt3 + tt4 + tt5;
    }

    private float[] correct(float dt,float p0, float p1, float p2, float p3, float p4, float p5, float R2) {
        float r0, r1, r2, r3, r4, r5;
        float fact = 1;

        r0 = p0 + coeff[0]*R2;
        r1 = p1 + coeff[1]*R2/dt;
        fact *= 2;
        r2 = (float) (p2 + coeff[2]*R2*fact/Math.pow(dt, 2));
        fact *= 3;
        r3 = (float) (p3 + coeff[3]*R2*fact/Math.pow(dt, 3));
        fact *= 4;
        r4 = (float) (p4 + coeff[4]*R2*fact/Math.pow(dt, 4));
        fact *= 5;
        r5 = (float) (p5 + coeff[5]*R2*fact/Math.pow(dt, 5));

        return new float[]{r0, r1, r2, r3, r4, r5};
    }

}


