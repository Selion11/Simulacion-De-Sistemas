package TP3.Collision;

import TP3.Particle;

import java.util.List;

public abstract class Collision implements Comparable<Collision> {

    private CollisionType type;
    private float tc;
    protected CollisionUtils collisionUtils;


    public enum CollisionType {
        PARTICLE_PARTICLE,
        PARTICLE_WALL,
        PARTICLE_OBSTACLE
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
        if (this.tc == -1 && o.getTc() >= 0) {
            return 1;
        }
        if (o.getTc() == -1 && this.tc >= 0) {
            return -1;
        }
        if (this.tc == -1 && o.getTc() == -1) {
            return 0;
        }
        return Float.compare(this.tc, o.getTc());
    }

    public CollisionType getType() {
        return type;
    }

    public float getTc() {
        return tc;
    }

}
