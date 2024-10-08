package TP4.algorithms;

import TP4.Particle;

public class Beeman implements Algorithm{
    final float m;
    final float k;
    final float gamma;
    final float dt;

    float a;
    float rBefore;
    float vBefore;
    float aBefore;
    private final Particle particle;

    public Beeman(Particle particle) {
        this.particle = particle;
        this.m = particle.getM();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        this.dt = particle.getTimeStep();

        this.a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
        this.vBefore = EulerVelocity(particle.getV(), a, -dt);
        this.rBefore = EulerPosition(particle.getPosition(), particle.getV(), a, -dt);
        this.aBefore = OscillatorForce(rBefore, vBefore, m, k, gamma);
    }

    @Override
    public void runAlgorithm() {
        float rAfter = particle.getPosition() + particle.getV()*dt + a*dt*dt*2/3 - aBefore*dt*dt/6;
        float vAfter = particle.getV() + 1.5f*a*dt - 0.5f*aBefore*dt;
        float aAfter = OscillatorForce(rAfter, vAfter, m, k, gamma);
        vAfter = particle.getV() + aAfter*dt/3 + a*dt*5/6 - aBefore*dt/6;

        aBefore = a;
        this.particle.setPosition(rAfter);
        this.particle.setV(vAfter);
        a = aAfter;
    }





}
