package TP4.algorithms;
import TP4.Particle;

public class GearPredictorCorrector {

    private Particle particle;  // Instancia de la clase Particle
    private float timeStep;     // Paso temporal
    private float k;            // Constante del resorte
    private float gamma;        // Coeficiente de amortiguamiento
    float[] derivatives = new float[5];

    public GearPredictorCorrector(Particle particle) {
        this.particle = particle;
        this.timeStep = particle.getTimeStep();
        this.k = particle.getK();
        this.gamma = particle.getGamma();
        calculateDerivatives();
    }

    public void runAlgorithm(){
        predictor();
        corrector();
    }

    // Método que calcula la fuerza en función de la posición y la velocidad actuales
    private float calculateForce(float position, float velocity) {
        return -k * position - gamma * velocity;
    }

    private void calculateDerivatives() {

        // Derivada r_0 (posición)
        derivatives[0] = particle.getPosition();

        // Derivada r_1 (velocidad)
        derivatives[1] = particle.getV();

        // Derivada r_2 (aceleración)
        derivatives[2] = calculateForce(particle.getPosition(), particle.getV()) / particle.getM();

        // Derivada r_3 = d(r_2)/dt (proporcional a la velocidad)
        derivatives[3] = -k * derivatives[1] - gamma * derivatives[2];

        // Derivada r_4 = d(r_3)/dt (proporcional a la aceleración)
        derivatives[4] = -k * derivatives[2] - gamma * derivatives[3];
    }

    // Método que realiza el paso predictor del algoritmo Gear
    public void predictor() {

        // Predicción utilizando las derivadas (Taylor expansion)
        particle.setPosition(
                particle.getPosition()
                        + timeStep * derivatives[1]
                        + (timeStep * timeStep / 2) * derivatives[2]
                        + (timeStep * timeStep * timeStep / 6) * derivatives[3]
                        + (timeStep * timeStep * timeStep * timeStep / 24) * derivatives[4]
        );
        particle.setV(
                particle.getV()
                        + timeStep * derivatives[2]
                        + (timeStep * timeStep / 2) * derivatives[3]
                        + (timeStep * timeStep * timeStep / 6) * derivatives[4]
        );
    }

    // Método que realiza el paso corrector del algoritmo Gear
    public void corrector() {
        // Recalcular la fuerza
        float force = calculateForce(particle.getPosition(), particle.getV());
        // Delta a: diferencia entre la predicción y la nueva aceleración calculada
        float deltaA = (force - derivatives[2]) / particle.getM();

        // Corrección de la posición y las derivadas
        derivatives[0] += deltaA * 3.0f / 16.0f;
        derivatives[1] += deltaA * 251.0f / 360.0f;
        derivatives[2] += deltaA;
        derivatives[3] += deltaA * 11.0f / 18.0f;
        derivatives[4] += deltaA * 1.0f / 6.0f;
    }

    // Imprime la posición actual de la partícula
    public void printPosition(float t) {
        System.out.printf("Tiempo: %.3f s -> Posición: %.5f%n", t, particle.getPosition());
    }
}

