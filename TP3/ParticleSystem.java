package TP3;

import TP3.Collision.Collision;
import TP3.Collision.CollisionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ParticleSystem {
    public static void main(String[] args) throws IOException {
        FileProcess fileProcessor = new FileProcess();
        ArrayList<Particle> particles;
        particles = fileProcessor.readFile("TP3/dynamic_input.txt", "TP3/static_input.txt", 1);

        float l = 0.1F;
        Obstacle obstacle = new Obstacle(l/2,l/2,0.005F);
        ParticleSystem particleSystem =  new ParticleSystem(particles, obstacle,l);

        int times = 100;

        File myFile = new File("TP3/Times/system_with_obstacle.txt");

        try{
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile))) {
            while(times >0){
                particleSystem.updateSystem();

                for(Particle p : particles){
                    try {
                        writer.write(p.toString());
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    writer.write('-');
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                times-=1;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Particle> particles;
    private Obstacle obstacle;
    private CollisionUtils collisionUtils;
    private float l;

    public ParticleSystem(List<Particle> particles, Obstacle obstacle, float l) {
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
