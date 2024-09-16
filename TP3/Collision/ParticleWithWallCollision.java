package TP3.Collision;

import TP3.Particle;
import TP3.Wall;
import TP3.WallType;

import java.util.ArrayList;
import java.util.List;

public class ParticleWithWallCollision extends Collision {

    private Particle particle;
    private Wall wall;

    public ParticleWithWallCollision(Particle particle, Wall wall, float tc, float l) {
        super(CollisionType.PARTICLE_WALL, tc, l);
        this.particle = particle;
        this.wall = wall;
    }
    

    @Override
    public void collide() {
        if(wall.getType() == WallType.HORIZONTAL) {
            particle.setVy(-particle.getVy());
        } else {
            particle.setVx(-particle.getVx());
        }
    }

    @Override
    public List<Particle> particlesInvolved() {
        List<Particle> involved = new ArrayList<>();
        involved.add(particle);
        return involved;
    }
    @Override
    public String toString() {
        return String.format( "particle:" +particle.getId() + " with wall:" + wall+ "with tc:" + super.getTc());
    }

    public float getPressure() {
        float toReturn;
        if (wall.getType() == WallType.HORIZONTAL){
            toReturn = 2*Math.abs(particle.getVy())*particle.getM();
        }else {
            toReturn = 2*Math.abs(particle.getVx())*particle.getM();
        }

        return toReturn;
    }
}
