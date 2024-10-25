package TP5;

import TP5.Jugador.Jugador;
import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;

import java.util.Vector;

public class Utils {

    private static final float DA = 10.0f; // Avoidance distance parameter
    private static final float A = 0.5f;   // Sigmoid parameter
    private static final float B = 0.5f;   // Offset for sigmoid

    // Update function to calculate the goal vector considering elusion
    public static void calcularVectorObjetivo(JugadorRojo jugadorRojo, List<JugadorAzul> jugadoresAzules, float deltaTiempo) {
        // Long-term goal (x = 0, projected in the y-coordinate of the red player)
        float objetivoLargoX = 0;
        float objetivoLargoY = jugadorRojo.getPosY();

        // Short-term target direction towards long-term goal
        float direccionLargoX = objetivoLargoX - jugadorRojo.getPosX();
        float direccionLargoY = objetivoLargoY - jugadorRojo.getPosY();
        float magnitudLargo = (float) Math.sqrt(direccionLargoX * direccionLargoX + direccionLargoY * direccionLargoY);

        if (magnitudLargo > 0) {
            direccionLargoX /= magnitudLargo;
            direccionLargoY /= magnitudLargo;
        }

        // Find the first potential collision and define avoidance goal g^a
        JugadorAzul jugadorCercano = null;
        float menorTiempoColision = Float.MAX_VALUE;
        Optional<Float> tiempoColisionOpt = Optional.empty();

        for (JugadorAzul jugadorAzul : jugadoresAzules) {
            boolean colisionPredicha = predecirColision(jugadorRojo, jugadorAzul, deltaTiempo);
            if (colisionPredicha) {
                tiempoColisionOpt = calcularTiempoColision(jugadorRojo, jugadorAzul);
                if (tiempoColisionOpt.isPresent()) {
                    float tiempoColision = tiempoColisionOpt.get();
                    if (tiempoColision < menorTiempoColision) {
                        menorTiempoColision = tiempoColision;
                        jugadorCercano = jugadorAzul;
                    }
                }
            }
        }

        // Now calculate n_i^a, g^a if a collision is predicted
        float direccionElusionX = 0, direccionElusionY = 0;

        if (jugadorCercano != null && tiempoColisionOpt.isPresent()) {
            float distX = jugadorRojo.getPosX() - jugadorCercano.getPosX();
            float distY = jugadorRojo.getPosY() - jugadorCercano.getPosY();
            float dist = (float) Math.sqrt(distX * distX + distY * distY);

            // Calculate n_j^p (perpendicular vector from the blue player)
            float perpendicularX = -distY;
            float perpendicularY = distX;
            float magnitudPerpendicular = (float) Math.sqrt(perpendicularX * perpendicularX + perpendicularY * perpendicularY);

            if (magnitudPerpendicular > 0) {
                perpendicularX /= magnitudPerpendicular;
                perpendicularY /= magnitudPerpendicular;
            }

            // Place the avoidance goal g^a
            float gaX = jugadorCercano.getPosX() + DA * perpendicularX;
            float gaY = jugadorCercano.getPosY() + DA * perpendicularY;

            direccionElusionX = gaX - jugadorRojo.getPosX();
            direccionElusionY = gaY - jugadorRojo.getPosY();
            float magnitudElusion = (float) Math.sqrt(direccionElusionX * direccionElusionX + direccionElusionY * direccionElusionY);

            if (magnitudElusion > 0) {
                direccionElusionX /= magnitudElusion;
                direccionElusionY /= magnitudElusion;
            }
        }

        // Calculate the sigmoid factor
        float dc = menorTiempoColision; // dc is the time-to-collision (or distance in some contexts)
        float sa = calcularSigmoid(dc);

        // Calculate the final direction (n_i^t)
        float direccionFinalX = sa * direccionElusionX + (1 - sa) * direccionLargoX;
        float direccionFinalY = sa * direccionElusionY + (1 - sa) * direccionLargoY;

        // Update the red player's velocity according to the new vector direction
        jugadorRojo.calcularVelocidadElusion(direccionFinalX, direccionFinalY);
    }

    // Calculate sigmoid function
    public static float calcularSigmoid(float x) {
        return 1.0f / (1.0f + (float) Math.exp(A * (x + B)));
    }

    // Predict collision between two players
    public static boolean predecirColision(Jugador jugador1, Jugador jugador2, float tiempo) {
        float posFuturaX1 = jugador1.getPosX() + jugador1.getVelX() * tiempo;
        float posFuturaY1 = jugador1.getPosY() + jugador1.getVelY() * tiempo;

        float posFuturaX2 = jugador2.getPosX() + jugador2.getVelX() * tiempo;
        float posFuturaY2 = jugador2.getPosY() + jugador2.getVelY() * tiempo;

        float distX = posFuturaX1 - posFuturaX2;
        float distY = posFuturaY1 - posFuturaY2;
        float distancia = (float) Math.sqrt(distX * distX + distY * distY);

        return distancia < (jugador1.getRadio() + jugador2.getRadio());
    }

    // Calculate time-to-collision between two players
    public static Optional<Float> calcularTiempoColision(Jugador jugador1, Jugador jugador2) {
        float relativeVelX = jugador1.getVelX() - jugador2.getVelX();
        float relativeVelY = jugador1.getVelY() - jugador2.getVelY();
        float relativePosX = jugador1.getPosX() - jugador2.getPosX();
        float relativePosY = jugador1.getPosY() - jugador2.getPosY();

        float a = relativeVelX * relativeVelX + relativeVelY * relativeVelY;
        float b = 2 * (relativeVelX * relativePosX + relativeVelY * relativePosY);
        float c = relativePosX * relativePosX + relativePosY * relativePosY - (jugador1.getRadio() + jugador2.getRadio()) * (jugador1.getRadio() + jugador2.getRadio());

        float discriminante = b * b - 4 * a * c;

        if (discriminante >= 0) {
            float t1 = (-b - (float) Math.sqrt(discriminante)) / (2 * a);
            float t2 = (-b + (float) Math.sqrt(discriminante)) / (2 * a);

            if (t1 > 0) return Optional.of(t1);
            if (t2 > 0) return Optional.of(t2);
        }

        return Optional.empty();
    }
}
