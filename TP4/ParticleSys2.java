package TP4;

import TP3.ParticleSystem;

public class ParticleSys2 extends Particle{
    private static int id;
    public ParticleSys2(double position, double v0, double m, double k, double gamma, double timeStep,int pid) {
        super(position, v0, m, k, gamma, timeStep);
        id = pid;
    }

    public int getId(){ return id;}
}
