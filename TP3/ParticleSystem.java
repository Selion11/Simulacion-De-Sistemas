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

        // Para correr como obstaculo fijo
//        Obstacle obstacle = new Obstacle(l/2,l/2,0.005F);
//        ParticleSystem particleSystem =  new ParticleSystem(particles, obstacle,l);

        // Para correr como particula mas grande
        Particle obstacle = new Particle(100000, l/2,l/2,0,0.005F,0,3F );
        ParticleSystem particleSystem =  new ParticleSystem(particles, obstacle,l);

        int times = 2000;

        File myFile = new File("TP3/Times/system_with_obstacle.txt");

        try{
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile))) {
            while(times > 0){

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
                particleSystem.updateSystem();

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
    private Collision lastCollision;

    public ParticleSystem(List<Particle> particles, Particle obstacle, float l) {
        this.particles = particles;
        this.particles.add(obstacle);
//        this.obstacle = obstacle;
        this.l = l;
        this.collisionUtils = new CollisionUtils(l);
    }

    public void updateSystem() {
        TreeSet<Collision> possibleCollisions = new TreeSet<>();
        Collision auxWalls, auxObstacle, auxParticles;

        for (Particle p : this.particles) {

            auxWalls = collisionUtils.getTcWalls(p);
            if (auxWalls != null &&
                    !(lastCollision != null && lastCollision.getType() == auxWalls.getType() && lastCollision.particlesInvolved().contains(p)) ){
                possibleCollisions.add(auxWalls);
            }

//            auxObstacle = collisionUtils.getTcObstacle(p, obstacle);
//            if (auxObstacle != null &&
//                    !(lastCollision != null && lastCollision.getType() == auxObstacle.getType() && lastCollision.particlesInvolved().contains(p))) {
//                possibleCollisions.add(auxObstacle);
//            }

            for (Particle p2 : this.particles) {
                if (!p.equals(p2)) {
                    auxParticles = collisionUtils.getTcParticles(p, p2);
                    if (auxParticles != null &&
                            !(lastCollision != null && lastCollision.getType() == auxParticles.getType() && lastCollision.particlesInvolved().contains(p))){
                        possibleCollisions.add(auxParticles);
                    }
                }
            }
        }


        Collision nextCollision = possibleCollisions.pollFirst();
        float tc = nextCollision.getTc();

        for(Particle p : this.particles) {
            p.move(tc);
        }

        System.out.println(nextCollision);
        nextCollision.collide();
        lastCollision = nextCollision;
    }

}
