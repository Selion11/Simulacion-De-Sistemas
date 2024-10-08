package TP4.algorithms;

public interface Algorithm {
    public void runAlgorithm();
    default float OscillatorForce(float r, float v, float m, float k, float gamma) {
        return (-k*r -gamma*v)/m;
    }

    default float EulerPosition(float r, float v, float a, float dt) {
        return r + dt*v + dt*dt*a/2;
    }

    default float EulerVelocity(float v, float a, float dt) {
        return v + dt*a;
    }
}
