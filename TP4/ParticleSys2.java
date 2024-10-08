package TP4;

import TP3.ParticleSystem;

public class ParticleSys2 extends Particle{
    private static int id;
    private static float l0;

    public ParticleSys2(float position, float v0, float m, float k, float gamma, float timeStep,int pid,float dist) {
        super(position, v0, m, k, gamma, timeStep);
        id = pid;
        l0 = dist;
    }

    public int getId(){ return id;}
}
