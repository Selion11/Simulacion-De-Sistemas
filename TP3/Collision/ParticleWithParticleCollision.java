package TP3.Collision;

import TP3.Particle;

import java.util.ArrayList;
import java.util.List;

public class ParticleWithParticleCollision extends Collision {

    private Particle p1, p2;

    public ParticleWithParticleCollision(Particle p1, Particle p2, float tc, float l) {
        super(CollisionType.PARTICLE_PARTICLE, tc, l);
        this.p1 = p1;
        this.p2 = p2;
    }


    @Override
    public float getPressure() {
        return -1F;
    }

    @Override
    public void collide() {
        float Jx = collisionUtils.getJx(p1,p2);
        float Jy = collisionUtils.getJy(p1,p2);
        p1.setVx(p1.getVx() + Jx / p1.getM());
        p1.setVy(p1.getVy() + Jy / p1.getM());
        p2.setVx(p2.getVx() - Jx / p2.getM());
        p2.setVy(p2.getVy() - Jy / p2.getM());
    }

    @Override
    public List<Particle> particlesInvolved() {
        List<Particle> involved = new ArrayList<>();
        involved.add(p1);
        involved.add(p2);
        return involved;
    }

    @Override
    public String toString() {
        return String.format( "particle:" +p1.getId() + " with particle:" + p2.getId()+ "with tc:" + super.getTc());
    }
}
