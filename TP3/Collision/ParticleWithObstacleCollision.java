package TP3.Collision;

import TP3.Particle;
import TP3.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class ParticleWithObstacleCollision extends Collision {

    private Particle particle;
    private Obstacle obstacle;

    public ParticleWithObstacleCollision(Particle particle, Obstacle obstacle, float tc, float l) {
        super(CollisionType.PARTICLE_OBSTACLE, tc, l);
        this.particle = particle;
        this.obstacle = obstacle;
    }

    @Override
    public void collide() {
        float alpha = collisionUtils.getAlpha(particle, obstacle);
        float Vn = particle.getVx() * (float) Math.cos(alpha) + particle.getVy() * (float) Math.sin(alpha);
        float Vt = particle.getVx() * (float) Math.sin(alpha) - particle.getVy() * (float) Math.cos(alpha);
        float newVx = (float) ((-Vn) * Math.cos(alpha) - Vt * Math.sin(alpha));
        float newVy = (float) ((-Vn) * Math.sin(alpha) + Vt * Math.cos(alpha));
        particle.setVx(newVx);
        particle.setVy(newVy);
    }

    @Override
    public String toString() {
        return String.format( "particle:" + particle.getId() + " with obstacle" + "with tc:" + super.getTc());
    }

}
