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

        float dist = particle.getR() + obstacle.getR();

        float nx = (particle.getX()- obstacle.getX())/dist;
        float ny = (particle.getY()- obstacle.getY())/dist;

        float tx = -ny;
        float ty = nx;

        float vn = particle.getVx()*nx + particle.getVy()*ny;
        float vt = particle.getVx()*tx + particle.getVy()*ty;

        float vn_after = -vn;

        float vfx = vn_after*nx +vt*tx;
        float vfy = vn_after*ny + vt*ty;

        particle.setVx(vfx);
        particle.setVy(vfy);
    }

    @Override
    public List<Particle> particlesInvolved() {
        List<Particle> involved = new ArrayList<>();
        involved.add(particle);
        return involved;
    }

    @Override
    public String toString() {
        return String.format( "particle:" + particle.getId() + " with obstacle" + "with tc:" + super.getTc());
    }

}
