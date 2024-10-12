package TP4.algorithms;
import TP4.Particle;
import TP4.ParticleSys2;

public class Verlet implements Algorithm{
    private Particle particle;
    private Particle prevP;
    private Particle nextP;
    double m;
    double k;
    double gamma;
    double dt;

    double a;

    public Verlet(Particle particle) {
        this.particle = particle;
        prevP = null;
        nextP = null;
        this.m = particle.getM();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        this.dt = particle.getTimeStep();

        this.a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
        this.particle.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
    }

    public Verlet(Particle particle,Particle prevP,Particle nextP){
        this.particle = particle;
        this.prevP = prevP;
        this.nextP = nextP;
        this.m = particle.getM();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        this.dt = particle.getTimeStep();

        this.a = GroupOscillatorForce(k,particle.getPosition(), prevP.getPosition(),nextP.getPosition());
        this.particle.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
    }

    @Override
    public void runAlgorithm() {
        double rAfter = 2*particle.getPosition() - particle.getPrevPosition() + dt* dt*a;
        particle.setV ((rAfter - particle.getPrevPosition())/(2* particle.getTimeStep()));

        this.particle.setPosition(rAfter);
        a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
    }

    public void runAlogrithmSys2(){
        double rAfter = 2*particle.getPosition() - particle.getPrevPosition() + dt* dt*a;
        particle.setV ((rAfter - particle.getPrevPosition())/(2* particle.getTimeStep()));

        this.particle.setPosition(rAfter);
        a = GroupOscillatorForce(k,particle.getPosition(), prevP.getPosition(),nextP.getPosition());
    }


}
