package TP3.Collision;

import TP3.Particle;
import TP3.WallType;

import java.util.ArrayList;
import java.util.List;

public class ParticleWithWallCollision extends Collision {

    private Particle particle;
    private WallType wall;

    public ParticleWithWallCollision(Particle particle, WallType wall, float tc, float l) {
        super(CollisionType.PARTICLE_WALL, tc, l);
        this.particle = particle;
        this.wall = wall;
    }

    @Override
    public List<Particle> getParticlesInvolved() {
        List<Particle> particles = new ArrayList<>();
        particles.add(particle);
        return particles;
    }

    @Override
    public void collide() {
        if(wall == WallType.HORIZONTAL) {
            particle.setVy(-particle.getVy());
        } else {
            particle.setVx(-particle.getVx());
        }
    }

}
