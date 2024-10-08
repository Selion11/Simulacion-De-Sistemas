package TP4;

public class Oscilador {

    // Solución analítica para el oscilador amortiguado
    public float analyticalSolution(float t, float initialPosition, float initialVelocity, float k , float gamma) {
        float omega0 = (float) Math.sqrt(k);  // Frecuencia natural
        float gamma2 = gamma / 2;              // Amortiguamiento
        float omegaD = (float) Math.sqrt(omega0 * omega0 - gamma2 * gamma2);  // Frecuencia amortiguada

        float A = initialPosition;
        float B = (initialVelocity + gamma2 * initialPosition) / omegaD;

        return (float) (Math.exp(-gamma2 * t) * (A * Math.cos(omegaD * t) + B * Math.sin(omegaD * t)));
    }
}
