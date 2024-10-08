package TP4.algorithms;
import TP4.Particle;
import TP4.ParticleSys2;

public class Verlet implements Algorithm{
    private Particle particle;
    private ParticleSys2 particleSys2;
    private ParticleSys2 prevP;
    private ParticleSys2 nextP;
    float m;
    float k;
    float gamma;
    float dt;

    float a;

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
        this.particle.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
    }

    @Override
    public void runAlgorithm() {
        float rAfter = 2*particle.getPosition() - particle.getPrevPosition() + dt* dt*a;
        particle.setV ((rAfter - particle.getPrevPosition())/(2* particle.getTimeStep()));

        this.particle.setPosition(rAfter);
        a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);
    }


}
