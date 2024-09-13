package TP3;

import TP3.Collision.Collision;
import TP3.Collision.CollisionUtils;

import java.util.List;
import java.util.TreeSet;

public class System {

    private List<Particle> particles;
    private Obstacle obstacle;
    private CollisionUtils collisionUtils;
    private float l;

    public System(List<Particle> particles, Obstacle obstacle, float l) {
        this.particles = particles;
        this.obstacle = obstacle;
        this.l = l;
        this.collisionUtils = new CollisionUtils(l);
    }

    public void updateSystem() {
        TreeSet<Collision> possibleCollisions = new TreeSet<>();
        for (Particle p : particles) {
            possibleCollisions.add(collisionUtils.getTcWalls(p));
            possibleCollisions.add(collisionUtils.getTcObstacle(p, obstacle));
            for (Particle p2 : particles) {
                if (!p.equals(p2)) {
                    possibleCollisions.add(collisionUtils.getTcParticles(p, p2));
                }
            }
        }
        Collision nextCollision = possibleCollisions.pollFirst();
        assert nextCollision != null;
        for(Particle p : particles) {
            p.move(nextCollision.getTc());
        }
        nextCollision.collide();
    }

}
