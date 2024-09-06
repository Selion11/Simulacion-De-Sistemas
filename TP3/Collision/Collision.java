package TP3.Collision;

import TP3.Particle;

import java.util.List;

public abstract class Collision implements Comparable<Collision> {

    private CollisionType type;
    private float tc;
    protected CollisionUtils collisionUtils;


    public enum CollisionType {
        PARTICLE_PARTICLE,
        PARTICLE_WALL
    }

    public Collision(CollisionType type, float tc, float l) {
        this.type = type;
        this.tc = tc;
        this.collisionUtils = new CollisionUtils(l);
    }

    public abstract void collide();

    public abstract List<Particle> getParticlesInvolved();

    @Override
    public int compareTo(Collision o) {
        return Float.compare(tc, o.getTc());
    }

    public CollisionType getType() {
        return type;
    }

    public float getTc() {
        return tc;
    }

}