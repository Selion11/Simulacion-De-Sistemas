package TP5;

import TP5.Jugador.Jugador;
import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class Utils {

    private static final double DA = 10.0; // Avoidance distance parameter
    private static final double A = 0.5;   // Sigmoid parameter
    private static final double B = 0.5;   // Offset for sigmoid

    // Update function to calculate the goal vector considering elusion
    public static void calcularVectorObjetivo(JugadorRojo jugadorRojo, List<JugadorAzul> jugadoresAzules, double deltaTiempo) {
        // Long-term goal (x = 0, projected in the y-coordinate of the red player)
        double objetivoLargoX = 0;
        double objetivoLargoY = jugadorRojo.getPosY();

        // Short-term target direction towards long-term goal
        double direccionLargoX = objetivoLargoX - jugadorRojo.getPosX();
        double direccionLargoY = objetivoLargoY - jugadorRojo.getPosY();
        double magnitudLargo =  Math.sqrt(direccionLargoX * direccionLargoX + direccionLargoY * direccionLargoY);

        if (magnitudLargo > 0) {
            direccionLargoX /= magnitudLargo;
            direccionLargoY /= magnitudLargo;
        }

        // Find the first potential collision and define avoidance goal g^a
        JugadorAzul jugadorCercano = null;
        double menorTiempoColision = Float.MAX_VALUE;
        Optional<Double> tiempoColisionOpt = Optional.empty();

        for (JugadorAzul jugadorAzul : jugadoresAzules) {
            boolean colisionPredicha = predecirColision(jugadorRojo, jugadorAzul, deltaTiempo);
            if (colisionPredicha) {
                tiempoColisionOpt = calcularTiempoColision(jugadorRojo, jugadorAzul);
                if (tiempoColisionOpt.isPresent()) {
                    double tiempoColision = tiempoColisionOpt.get();
                    if (tiempoColision < menorTiempoColision) {
                        menorTiempoColision = tiempoColision;
                        jugadorCercano = jugadorAzul;
                    }
                }
            }
        }

        // Now calculate n_i^a, g^a if a collision is predicted
        double direccionElusionX = 0, direccionElusionY = 0;

        if (jugadorCercano != null && tiempoColisionOpt.isPresent()) {
            double distX = jugadorRojo.getPosX() - jugadorCercano.getPosX();
            double distY = jugadorRojo.getPosY() - jugadorCercano.getPosY();
            double dist = Math.sqrt(distX * distX + distY * distY);

            // Calculate n_j^p (perpendicular vector from the blue player)
            double perpendicularX = -distY;
            double perpendicularY = distX;
            double magnitudPerpendicular = (float) Math.sqrt(perpendicularX * perpendicularX + perpendicularY * perpendicularY);

            if (magnitudPerpendicular > 0) {
                perpendicularX /= magnitudPerpendicular;
                perpendicularY /= magnitudPerpendicular;
            }

            // Place the avoidance goal g^a
            double gaX = jugadorCercano.getPosX() + DA * perpendicularX;
            double gaY = jugadorCercano.getPosY() + DA * perpendicularY;

            direccionElusionX = gaX - jugadorRojo.getPosX();
            direccionElusionY = gaY - jugadorRojo.getPosY();
            double magnitudElusion = Math.sqrt(direccionElusionX * direccionElusionX + direccionElusionY * direccionElusionY);

            if (magnitudElusion > 0) {
                direccionElusionX /= magnitudElusion;
                direccionElusionY /= magnitudElusion;
            }
        }

        // Calculate the sigmoid factor
        double dc = menorTiempoColision; // dc is the time-to-collision (or distance in some contexts)
        double sa = calcularSigmoid(dc);

        // Calculate the final direction (n_i^t)
        double direccionFinalX = sa * direccionElusionX + (1 - sa) * direccionLargoX;
        double direccionFinalY = sa * direccionElusionY + (1 - sa) * direccionLargoY;

        // Update the red player's velocity according to the new vector direction
        jugadorRojo.calcularVelocidadElusion(direccionFinalX, direccionFinalY);
    }

    // Calculate sigmoid function
    public static double calcularSigmoid(double x) {
        return 1.0f / (1.0f +  Math.exp(A * (x + B)));
    }

    // Predict collision between two players
    public static boolean predecirColision(Jugador jugador1, Jugador jugador2, double tiempo) {
        double posFuturaX1 = jugador1.getPosX() + jugador1.getVelX() * tiempo;
        double posFuturaY1 = jugador1.getPosY() + jugador1.getVelY() * tiempo;

        double posFuturaX2 = jugador2.getPosX() + jugador2.getVelX() * tiempo;
        double posFuturaY2 = jugador2.getPosY() + jugador2.getVelY() * tiempo;

        double distX = posFuturaX1 - posFuturaX2;
        double distY = posFuturaY1 - posFuturaY2;
        double distancia = Math.sqrt(distX * distX + distY * distY);

        return distancia < (jugador1.getRadio() + jugador2.getRadio());
    }

    // Calculate time-to-collision between two players
    public static Optional<Double> calcularTiempoColision(Jugador jugador1, Jugador jugador2) {
        double relativeVelX = jugador1.getVelX() - jugador2.getVelX();
        double relativeVelY = jugador1.getVelY() - jugador2.getVelY();
        double relativePosX = jugador1.getPosX() - jugador2.getPosX();
        double relativePosY = jugador1.getPosY() - jugador2.getPosY();

        double a = relativeVelX * relativeVelX + relativeVelY * relativeVelY;
        double b = 2 * (relativeVelX * relativePosX + relativeVelY * relativePosY);
        double c = relativePosX * relativePosX + relativePosY * relativePosY - (jugador1.getRadio() + jugador2.getRadio()) * (jugador1.getRadio() + jugador2.getRadio());

        double discriminante = b * b - 4 * a * c;

        if (discriminante >= 0) {
            double t1 = (-b - Math.sqrt(discriminante)) / (2 * a);
            double t2 = (-b + Math.sqrt(discriminante)) / (2 * a);

            if (t1 > 0) return Optional.of(t1);
            if (t2 > 0) return Optional.of(t2);
        }

        return Optional.empty();
    }
}
