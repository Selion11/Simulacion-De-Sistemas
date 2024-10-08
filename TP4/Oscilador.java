package TP4;

public class Oscilador {

    // Solución analítica para el oscilador amortiguado
    public float analyticalSolution(float m, float k, float gamma, float t) {

        return (float) (Math.exp(-t*gamma/(2*m)) * Math.cos(Math.pow(k/m - (Math.pow(gamma,2)/(4*Math.pow(m,2))), 0.5) * t));
    }
}
