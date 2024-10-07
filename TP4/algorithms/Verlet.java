package TP4.algorithms;
import TP4.Particle;

public class Verlet {
    private Particle particle;
    private float timeStep;
    private float k;  // Constante del resorte
    private float gamma;  // Coeficiente de amortiguamiento

    public Verlet(Particle particle, float timeStep, float k, float gamma) {
        this.particle = particle;
        this.timeStep = timeStep;
        this.k = k;
        this.gamma = gamma;
    }

    // Método que calcula la fuerza en función de la posición y la velocidad actuales
    private float calculateForce(float position, float velocity) {
        return -k * position - gamma * velocity;
    }

    // Método que ejecuta un paso de Verlet
    public void verletStep() {
        float force = calculateForce(particle.getPosition(), particle.getV());
        float newPosition = 2 * particle.getPosition() - particle.getPrevPosition() + (force / particle.getM()) * timeStep * timeStep;

        // Actualizamos la posición previa y la posición actual
        particle.setPosition(newPosition);
    }

    // Método para actualizar la velocidad, que no es necesaria para Verlet pero se usa para la comparación
    public void updateVelocity() {
        float force = calculateForce(particle.getPosition(), particle.getV());
        particle.setV(particle.getV() + ((force / particle.getM()) * timeStep));
    }

    // Solución analítica para el oscilador amortiguado
    public float analyticalSolution(float t, float initialPosition, float initialVelocity) {
        float omega0 = (float) Math.sqrt(k / particle.getM());  // Frecuencia natural
        float gamma2 = gamma / (2 * particle.getM());   // Amortiguamiento
        float omegaD = (float) Math.sqrt(omega0 * omega0 - gamma2 * gamma2);  // Frecuencia amortiguada

        float A = initialPosition;
        float B = (initialVelocity + gamma2 * initialPosition) / omegaD;

        return (float) (Math.exp(-gamma2 * t) * (A * Math.cos(omegaD * t) + B * Math.sin(omegaD * t)));
    }

    // Imprime la posición actual de la partícula
    public void printPosition(float t) {
        System.out.printf("Tiempo: %.3f s -> Posición: %.5f%n", t, particle.getPosition());
    }
}
