package TP4.algorithms;
import TP4.Particle;
import TP4.ParticleSys2;

public class Verlet implements Algorithm{
    private Particle particle;
    private ParticleSys2 particleSys2;
    private ParticleSys2 prevP;
    private ParticleSys2 nextP;
    double m;
    double k;
    double gamma;
    double dt;

    double a;

    public Verlet(Particle particle) {
        this.particle = particle;
        particleSys2 = null;
        prevP = null;
        nextP = null;
        this.m = particle.getM();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        this.dt = particle.getTimeStep();

        this.a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
        this.particle.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
    }

    public Verlet(ParticleSys2 particle,ParticleSys2 prevP,ParticleSys2 nextP){
        this.particle = null;
        this.particleSys2 = particle;
        this.prevP = prevP;
        this.nextP = nextP;
        this.m = particle.getM();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        this.dt = particle.getTimeStep();

        this.a = GroupOscillatorForce(k,particle.getPosition(), prevP.getPosition(),nextP.getPosition());
        this.particleSys2.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
    }

    @Override
    public void runAlgorithm() {
        double rAfter = 2*particle.getPosition() - particle.getPrevPosition() + dt* dt*a;
        particle.setV ((rAfter - particle.getPrevPosition())/(2* particle.getTimeStep()));

        this.particle.setPosition(rAfter);
        a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
    }

    public void runAlogrithmSys2(){
        double rAfter = 2*particleSys2.getPosition() - particleSys2.getPrevPosition() + dt* dt*a;
        particleSys2.setV ((rAfter - particleSys2.getPrevPosition())/(2* particleSys2.getTimeStep()));

        this.particleSys2.setPosition(rAfter);
        a = GroupOscillatorForce(k,particleSys2.getPosition(), prevP.getPosition(),nextP.getPosition());
    }


}
