package TP3;

import java.util.ArrayList;
import java.util.List;

public class Obstacle {

    private float x,y,r;
    private long totalCollisions;

    private List<Integer> alreadyCollided;

    public Obstacle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.alreadyCollided = new ArrayList<>();
        this.totalCollisions = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public long getTotalCollisions() { return totalCollisions; }

    public long getOnlyFirstCollisions() {
        return alreadyCollided.size();
    }

    public void addCollision(Particle p) {
        if (!alreadyCollided.contains(p.getId())) {
            alreadyCollided.add(p.getId());
        }
        totalCollisions++;
    }

}
