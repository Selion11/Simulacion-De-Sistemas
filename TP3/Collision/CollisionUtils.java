package TP3.Collision;

import TP3.Particle;
import TP3.WallType;
import TP3.Obstacle;

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

    public float getJx(Particle p1, Particle p2) {
        return getJ(p1,p2) * getDeltaX(p1,p2) / getOmega(p1,p2);
    }

    public float getJy(Particle p1, Particle p2) {
        return getJ(p1,p2) * getDeltaY(p1,p2) / getOmega(p1,p2);
    }

    public float getAlpha(Particle p1, Obstacle obstacle) {
        return (float) Math.asin(Math.abs(p1.getY() - obstacle.getY()) / (obstacle.getR()+p1.getR()));
    }

    public Collision getTcObstacle(Particle p, Obstacle obstacle) {

        float x0 = p.getX();
        float y0 = p.getY();

        float vx = p.getVx();
        float vy = p.getVy();

        float x_obst = obstacle.getX();
        float y_obst = obstacle.getY();
        float r_obst = obstacle.getR() + p.getR();  // Sumar radios para considerar la colisión

        // Ecuación de la trayectoria y del círculo del obstáculo
        float dx = x0 - x_obst;
        float dy = y0 - y_obst;

        // Coeficientes de la ecuación cuadrática: a*t^2 + b*t + c = 0
        float a = vx * vx + vy * vy;
        float b = 2 * (dx * vx + dy * vy);
        float c = dx * dx + dy * dy - r_obst * r_obst;

        float discriminant = b * b - 4 * a * c;

        // Si el discriminante es negativo, no hay colisión
        if (discriminant < 0) {
            return new ParticleWithObstacleCollision(p, obstacle, -1, l);
        }

        // Calcular los tiempos de colisión
        float sqrtDiscriminant = (float) Math.sqrt(discriminant);
        float t1 = (-b - sqrtDiscriminant) / (2 * a);
        float t2 = (-b + sqrtDiscriminant) / (2 * a);

        // Elegir el tiempo de colisión positivo más pequeño
        float tc = Math.min(t1, t2);
        if (tc < 0) {
            tc = Math.max(t1, t2);  // Si t1 es negativo, probamos con t2
        }
        if (tc < 0) {
            return new ParticleWithObstacleCollision(p, obstacle, -1, l);  // No hay colisión con obstaculo
        }

        // Si llegamos aquí, significa que hay colisión con el obstáculo
        return new ParticleWithObstacleCollision(p, obstacle, tc, l);
    }


}
