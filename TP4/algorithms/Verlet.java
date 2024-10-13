package TP4.algorithms;
import TP4.Particle;

public class Verlet implements Algorithm{
    private Particle particle;
    double m;
    double k;
    double gamma;
    double dt;

    double a;

    public Verlet(Particle particle) {
        this.particle = particle;;
        this.m = particle.getM();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        this.dt = particle.getTimeStep();

        this.a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
        this.particle.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
    }


    @Override
    public void runAlgorithm() {
        double rAfter = 2*particle.getPosition() - particle.getPrevPosition() + dt* dt*a;
        particle.setV ((rAfter - particle.getPrevPosition())/(2* particle.getTimeStep()));

        this.particle.setPosition(rAfter);
        a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
    }

    @Override
    public void runAlgorithm2(double time) {

    }

}
