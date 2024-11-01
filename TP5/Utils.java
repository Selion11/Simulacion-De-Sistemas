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
    private static final double FIELD_HEIGHT = 70.0; // Altura del campo (paredes en y = 0 y y = 70)

    public static void calcularVectorObjetivo(JugadorRojo jugadorRojo, List<JugadorAzul> jugadoresAzules, double deltaTiempo) {

        double direccionLargoX = -jugadorRojo.getPosX();
        double direccionLargoY = 0;
        double magnitudLargo = Math.sqrt(direccionLargoX * direccionLargoX + direccionLargoY * direccionLargoY);

        if (magnitudLargo > 0) {
            direccionLargoX /= magnitudLargo;
            direccionLargoY /= magnitudLargo;
        }

        // Encontrar la primera colisión con un jugador azul
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

        // Verificar colisión con paredes laterales
        double tiempoColisionParedInferior = calcularTiempoColisionPared(jugadorRojo, 0);
        double tiempoColisionParedSuperior = calcularTiempoColisionPared(jugadorRojo, FIELD_HEIGHT);

        if (tiempoColisionParedInferior < menorTiempoColision) {
            menorTiempoColision = tiempoColisionParedInferior;
            direccionElusionY = 1; // Evitar la pared inferior dirigiéndose hacia arriba
            direccionElusionX = 0;
        }
        if (tiempoColisionParedSuperior < menorTiempoColision) {
            menorTiempoColision = tiempoColisionParedSuperior;
            direccionElusionY = -1; // Evitar la pared superior dirigiéndose hacia abajo
            direccionElusionX = 0;
        }

        // Lógica de evasión contra el jugador azul más cercano
        double direccionElusionX = 0, direccionElusionY = 0;

        if (jugadorCercano != null && tiempoColisionOpt.isPresent()) {
            double distX = jugadorRojo.getPosX() - jugadorCercano.getPosX();
            double distY = jugadorRojo.getPosY() - jugadorCercano.getPosY();
            double dist = Math.sqrt(distX * distX + distY * distY);

            double perpendicularX = -distY;
            double perpendicularY = distX;
            double magnitudPerpendicular = Math.sqrt(perpendicularX * perpendicularX + perpendicularY * perpendicularY);

            if (magnitudPerpendicular > 0) {
                perpendicularX /= magnitudPerpendicular;
                perpendicularY /= magnitudPerpendicular;
            }

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

        // Calcular el factor sigmoidal
        double dc = menorTiempoColision;
        double sa = calcularSigmoid(dc);

        // Calcular la dirección final (n_i^t)
        double direccionFinalX = sa * direccionElusionX + (1 - sa) * direccionLargoX;
        double direccionFinalY = sa * direccionElusionY + (1 - sa) * direccionLargoY;

        // Actualizar la velocidad del jugador rojo con la dirección del nuevo vector
        jugadorRojo.calcularVelocidadElusion(direccionFinalX, direccionFinalY);
    }

    public static double calcularSigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(A * (x + B)));
    }

    // Predecir colisión entre jugador y pared (devuelve el tiempo hasta la colisión)
    public static double calcularTiempoColisionPared(Jugador jugador, double yPared) {
        if (jugador.getVelY() == 0) return Float.MAX_VALUE;
        double tiempo = (yPared - jugador.getPosY()) / jugador.getVelY();
        return tiempo > 0 ? tiempo : Float.MAX_VALUE;
    }

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

    public static Optional<Double> calcularTiempoColision(Jugador jugador1, Jugador jugador2) {
        double relativeVelX = jugador1.getVelX() - jugador2.getVelX();
        double relativeVelY = jugador1.getVelY() - jugador2.getVelY();
        double relativePosX = jugador1.getPosX() - jugador2.getPosX();
        double relativePosY = jugador1.getPosY() - jugador2.getPosY();
        double a = relativeVelX * relativeVelX + relativeVelY * relativeVelY;
        double b = 2 * (relativeVelX * relativePosX + relativeVelY * relativePosY);
        double c = relativePosX * relativePosX + relativePosY * relativePosY - Math.pow(jugador1.getRadio() + jugador2.getRadio(), 2);
        double discriminante = b * b - 4 * a * c;

        if (discriminante >= 0) {
            double t1 = (-b - Math.sqrt(discriminante)) / (2 * a);
            double t2 = (-b + Math.sqrt(discriminante)) / (2 * a);
            if (t1 > 0) return Optional.of(t1);
            if (t2 > 0) return Optional.of(t2);
        }
        return Optional.empty();
    }

    // Actualizar la posición del jugador según su velocidad y el tiempo transcurrido
    public static void actualizarPosicion(Jugador jugador, double deltaTiempo) {
        jugador.setPosX(jugador.getPosX() + jugador.getVelX() * deltaTiempo);
        jugador.setPosY(jugador.getPosY() + jugador.getVelY() * deltaTiempo);
    }

    // Verificar si dos jugadores colisionaron (sus radios se superponen)
    public static boolean detectarColision(Jugador jugador1, Jugador jugador2) {
        double distX = jugador1.getPosX() - jugador2.getPosX();
        double distY = jugador1.getPosY() - jugador2.getPosY();
        double distancia = Math.sqrt(distX * distX + distY * distY);
        return distancia < (jugador1.getRadio() + jugador2.getRadio());
    }

    private double playerDistance(Jugador p1,Jugador p2){
        double x = Math.pow(p1.getPosX() - p2.getPosX(),2);
        double y = Math.pow(p1.getPosY() - p2.getPosY(),2);
        double r = Math.sqrt(x + y);
        return r - (p1.getRadio()*2);
    }

    //
    private Double[] calculateNormal(Jugador p1,Jugador p2){

    }

    public Double[] granularForces(Jugador p1,Jugador p2) {
        Double[] force = new Double[2];
        double xComponent = 0;
        double yComponent = 0;
            //normal es pos relativa dividido la distancia


        return force;
    }

    public Double[] wishForce(double tempX, double tempY,double maxV, double xVel,double yVel,double w,double tau){
        Double[] force = new Double[2];//0 x component | 1 y component
        double constant = (w/tau);
        double xComponent = constant*(maxV*tempX-xVel );
        double yComponent = constant*(maxV*tempY-yVel);
        force[0] = xComponent;
        force[1] = yComponent;
        return force;
    }
}

