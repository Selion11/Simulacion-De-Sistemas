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
        int v0 = 1;
        float l = 0.1F;
        int collisionAmount = 3000;


        particles = fileProcessor.readFile("TP3/dynamic_input.txt", "TP3/static_input.txt", v0);


        // Para correr como obstaculo fijo
        Obstacle obstacle = new Obstacle(l/2,l/2,0.005F);
        ParticleSystem particleSystem =  new ParticleSystem(particles, obstacle,l);

        // Para correr como particula mas grande
//        Particle obstacle = new Particle(100000, l/2,l/2,0,0.005F,0,3F );
//        ParticleSystem particleSystem =  new ParticleSystem(particles, obstacle,l);


//        File myFile = new File("TP3/Times/system_with_big_particle.txt");
        File myFile = new File("TP3/Times/system_with_obstacle.txt");
        File pressuresOnWallsFile = new File("TP3/Times/pressuresOnWalls.txt");
        File obstacleCollisionsCount = new File("TP3/Times/obstacle_collision_count_v0_"+v0+".txt");
        File pressuresOnObjectFile = new File("TP3/Times/pressuresOnObject.txt");


        long interval = 200; // 0.1 seconds (100 milliseconds)
        long lastTime = System.currentTimeMillis();
        Float pressureOnWalls = 0F;
        Float pressureOnObject = 0F;
        long timePassed = 0;

        try{
            myFile.createNewFile();
            pressuresOnWallsFile.createNewFile();
            obstacleCollisionsCount.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));
             BufferedWriter pressureOnWallsWriter = new BufferedWriter(new FileWriter(pressuresOnWallsFile));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(obstacleCollisionsCount));
             BufferedWriter pressureOnObjectWriter = new BufferedWriter(new FileWriter(pressuresOnObjectFile))) {
            while(collisionAmount > 0){
                long currentTime = System.currentTimeMillis(); // Get the current time in the loop

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

                // Check if the interval (t) has passed
                if (currentTime - lastTime >= interval) {
                    // Perform the action
                    pressureOnWalls = pressureOnWalls /(4F*l*(interval/1000F));
                    pressureOnWallsWriter.write(pressureOnWalls.toString());
                    pressureOnWallsWriter.newLine();

                    pressureOnObject =  pressureOnObject /((float)(2F*Math.PI*obstacle.getR())*(interval/1000F));
                    pressureOnObjectWriter.write(pressureOnObject.toString());
                    pressureOnObjectWriter.newLine();

                    // Reset the lastTime to the current time
                    timePassed += interval;
                    lastTime = currentTime;
                    pressureOnWalls = 0F;
                    pressureOnObject = 0F;
                }else {
                    if (particleSystem.lastCollision.getType() == Collision.CollisionType.PARTICLE_WALL) {
                        pressureOnWalls += particleSystem.lastCollision.getPressure();
                    }else if (particleSystem.lastCollision.getType() == Collision.CollisionType.PARTICLE_OBSTACLE){
                        pressureOnObject += particleSystem.lastCollision.getPressure();
                    }
                }

                try {
                    writer2.write(obstacle.getTotalCollisions() + "," + obstacle.getOnlyFirstCollisions() + "," + particleSystem.tcsum);
                    writer2.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                collisionAmount -=1;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Particle> particles;
    private Obstacle obstacle;
    private CollisionUtils collisionUtils;
    private float l;
    private float tcsum = 0;
    private Collision lastCollision;
    private List<Wall> walls;

    public ParticleSystem(List<Particle> particles, Obstacle obstacle, float l) {
        this.particles = particles;
//        this.particles.add(obstacle);
        this.obstacle = obstacle;
        this.l = l;
        this.collisionUtils = new CollisionUtils(l);

        Wall left = new Wall(1,WallType.VERTICAL,l);
        Wall right = new Wall(2,WallType.VERTICAL,l);
        Wall top = new Wall(3,WallType.HORIZONTAL,l);
        Wall bottom = new Wall(4,WallType.HORIZONTAL,l);

        this.walls = new ArrayList<>();

        this.walls.add(left);
        this.walls.add(right);
        this.walls.add(top);
        this.walls.add(bottom);
    }

    public void updateSystem() {
        TreeSet<Collision> possibleCollisions = new TreeSet<>();
        Collision auxWalls, auxObstacle, auxParticles;

        for (Particle p : this.particles) {

            auxWalls = collisionUtils.getTcWalls(p,this.walls);
            if (auxWalls != null &&
                    !(lastCollision != null && lastCollision.getType() == auxWalls.getType() && lastCollision.particlesInvolved().contains(p)) ){
                possibleCollisions.add(auxWalls);
            }

            auxObstacle = collisionUtils.getTcObstacle(p, obstacle);
            if (auxObstacle != null &&
                    !(lastCollision != null && lastCollision.getType() == auxObstacle.getType() && lastCollision.particlesInvolved().contains(p))) {
                possibleCollisions.add(auxObstacle);
            }

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
        tcsum += tc;

        for(Particle p : this.particles) {
            p.move(tc);
        }

        System.out.println(nextCollision);
        nextCollision.collide();
        lastCollision = nextCollision;

    }

}
