package TP3.Collision;

import TP3.Particle;
import TP3.WallType;

public class CollisionUtils {

    private final float l;

    public CollisionUtils(float l) {
        this.l = l;
    }

    private float getOmega(Particle p1, Particle p2) {
        return p1.getR() + p2.getR();
    }

    private float getDeltaX(Particle p1, Particle p2) {
        return p2.getX() - p1.getX();
    }

    private float getDeltaY(Particle p1, Particle p2) {
        return p2.getY() - p1.getY();
    }

    private float getDeltaVx(Particle p1, Particle p2) {
        return p2.getVx() - p1.getVx();
    }

    private float getDeltaVy(Particle p1, Particle p2) {
        return p2.getVy() - p1.getVy();
    }

    private float getDeltaMultiplication(Particle p1, Particle p2) {
        return getDeltaX(p1, p2) * getDeltaVx(p1, p2) + getDeltaY(p1, p2) * getDeltaVy(p1, p2);
    }

    private float getDeltaVSquared(Particle p1, Particle p2) {
        return (float) (Math.pow(getDeltaVx(p1, p2),2) + Math.pow(getDeltaVy(p1, p2),2));
    }

    private float getDeltaRSquared(Particle p1, Particle p2) {
        return (float) (Math.pow(getDeltaX(p1, p2), 2) + Math.pow(getDeltaY(p1, p2), 2));
    }

    private float getD(Particle p1, Particle p2) {
        return (float) (Math.pow(getDeltaMultiplication(p1,p2),2) -
                getDeltaVSquared(p1,p2) * (getDeltaRSquared(p1,p2) - Math.pow(getOmega(p1,p2),2)));
    }

    public Collision getTcParticles(Particle p1, Particle p2) {
        if(getDeltaMultiplication(p1,p2) >= 0 || getD(p1,p2) < 0) {
            return null;
        }
        float tc = (float) ((-getDeltaMultiplication(p1,p2) - Math.sqrt(getD(p1,p2))) / getDeltaVSquared(p1,p2));
        return new ParticleWithParticleCollision(p1, p2, tc, l);
    }

    public Collision getTcWalls(Particle p) {
        float tc = -1;
        WallType wallType = null;
        if(p.getVx() > 0) {
            tc = (l - p.getR() - p.getX()) / p.getVx();
            wallType = WallType.HORIZONTAL;
        } else if(p.getVx() < 0) {
            tc = (p.getR() - p.getX()) / p.getVx();
            wallType = WallType.HORIZONTAL;
        }
        if(p.getVy() > 0) {
            float aux = (l - p.getR() - p.getY()) / p.getVy();
            if(tc == -1 || aux < tc) {
                tc = aux;
                wallType = WallType.VERTICAL;
            }
        } else if(p.getVy() < 0) {
            if(tc == -1 || (p.getR() - p.getY()) / p.getVy() < tc) {
                tc = (p.getR() - p.getY()) / p.getVy();
                wallType = WallType.VERTICAL;
            }
        }
        return new ParticleWithWallCollision(p, wallType, tc, l);
    }

    private float getJ(Particle p1, Particle p2) {
        return  (2 * p1.getM() * p2.getM() * getDeltaMultiplication(p1,p2) / ((p1.getM() + p2.getM()) * getOmega(p1,p2)));
    }

    float getJx(Particle p1, Particle p2) {
        return getJ(p1,p2) * getDeltaX(p1,p2) / getOmega(p1,p2);
    }

    float getJy(Particle p1, Particle p2) {
        return getJ(p1,p2) * getDeltaY(p1,p2) / getOmega(p1,p2);
    }

}
