package TP4.algorithms;

import TP4.Particle;

public class Beeman {
    private Particle p;
    private float a;
    public float time = 0;

    public Beeman(Particle particle) {
        this.p = particle;
        evaluate();
    }

    public void runAlgorithm(){
        calculate();
        evaluate();
        calculate();
    }

    private void calculate(){
        p.setV(p.getV() + a * p.getTimeStep());
        p.setPosition(p.getPosition()+ p.getV() * p.getTimeStep());
    }

    private void evaluate(){
        a = (-(p.getK()* p.getPosition()) - (p.getGamma()* p.getV()))/ p.getM();
    }

    public float getParticlePosition() {
        return p.getPosition();
    }



}
