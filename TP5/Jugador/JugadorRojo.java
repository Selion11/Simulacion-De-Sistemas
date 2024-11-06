package TP5.Jugador;
import TP5.helpers.Sistema;
import TP5.Utils;

import java.util.Arrays;

public class JugadorRojo extends Jugador {

    private double DA; //avoidance distance parameter

    public JugadorRojo(double posX, double posY, double radio, double velocidadMaxima, double velX, double velY, double weight,double tau,double DA) {
        super(posX, posY, radio, velocidadMaxima,velX, velY, weight,tau);
        this.DA = DA;
    }

    public boolean hizoTry() {
        return this.posX <= 0;
    }

//    @Override
//    public void calcularVectorObjetivo(Sistema sistema) {
//        double[] direccionIngoal = {-1, 0};
//
//        double menorTiempoColision = Double.MAX_VALUE;
//        double[] objetivoEvasion = null;
//
//        // Paso 1: Predecir el tiempo de colisión con cada jugador azul
//        for (JugadorAzul jugadorAzul : sistema.getJugadoresAzules()) {
//            double tiempoColision = predecirTiempoColision(jugadorAzul);
//            if (tiempoColision > 0 && tiempoColision < menorTiempoColision) {
//                menorTiempoColision = tiempoColision;
//                objetivoEvasion = calcularObjetivoEvasion(jugadorAzul);  // Objetivo de evasión para el jugador azul
//            }
//        }
//
//        System.out.println("Tiempo de colisión con jugador azul: " + menorTiempoColision);
//
//        // Paso 2: Predecir el tiempo de colisión con la pared inferior
//        double tiempoColisionParedInferior = predecirTiempoColisionParedInferior();
//        if (tiempoColisionParedInferior > 0 && tiempoColisionParedInferior < menorTiempoColision) {
//            menorTiempoColision = tiempoColisionParedInferior;
//            objetivoEvasion = calcularObjetivoEvasionParedInferior();
//        }
//
//        // Paso 3: Predecir el tiempo de colisión con la pared superior
//        double tiempoColisionParedSuperior = predecirTiempoColisionParedSuperior();
//        if (tiempoColisionParedSuperior > 0 && tiempoColisionParedSuperior < menorTiempoColision) {
//            menorTiempoColision = tiempoColisionParedSuperior;
//            objetivoEvasion = calcularObjetivoEvasionParedSuperior();
//        }
//
//        // Paso 4: Calcular la dirección de evasión si se ha definido un objetivo de evasión
//        double[] direccionEvasion = direccionIngoal;  // Por defecto es hacia el ingoal
//        if (objetivoEvasion != null) {
//            direccionEvasion = Utils.calcularDireccion(this.getPosX(), this.getPosY(), objetivoEvasion[0], objetivoEvasion[1]);
//        }
//
//        System.out.println("Dirección de evasión: " + Arrays.toString(direccionEvasion));
//
//        // Paso 5: Calcular el factor sigmoidal s_a(d_c) usando la distancia al punto de colisión
//        double sa = Utils.calcularSigmoid(menorTiempoColision);
//
//        // Paso 6: Calcular la dirección final (n_i^t) como combinación ponderada de la dirección al ingoal y la de evasión
//        double direccionFinalX = sa * direccionEvasion[0] + (1 - sa) * direccionIngoal[0];
//        double direccionFinalY = sa * direccionEvasion[1] + (1 - sa) * direccionIngoal[1];
//
//        // Paso 7: Actualizar el objetivo temporal del jugador rojo (g_i^t)
//        this.setTarget(direccionFinalX, direccionFinalY);
//    }

        @Override
        public void calcularVectorObjetivo(Sistema sistema) {
            JugadorAzul jugadorMasCercano = null;
            JugadorAzul segundoJugadorMasCercano = null;
            double menorDistancia = Double.MAX_VALUE;

            for (JugadorAzul jugadorAzul : sistema.getJugadoresAzules()) {
                if(jugadorAzul.getPosX() < this.getPosX()){
                    double distancia = Math.sqrt(Math.pow(jugadorAzul.getPosX() - this.getPosX(), 2) + Math.pow(jugadorAzul.getPosY() - this.getPosY(), 2));
                    if (distancia < menorDistancia) {
                        if(jugadorMasCercano == null) {
                            segundoJugadorMasCercano = jugadorAzul;
                        } else {
                            segundoJugadorMasCercano = jugadorMasCercano;
                        }
                        menorDistancia = distancia;
                        jugadorMasCercano = jugadorAzul;
                    }
                }
            }

            double dx = this.getPosX() - jugadorMasCercano.getPosX();
            double dy = this.getPosY() - jugadorMasCercano.getPosY();

            int sentido;

            if(jugadorMasCercano.getPosY() > this.getPosY()) {
                sentido = 1;
            } else {
                sentido = -1;
            }

            double[] opcion1 = { sentido*dx, sentido*(-dy)};
            double[] opcion2 = { sentido*(-dx), sentido*dy};
            double[] opcionFinal = null;

            if(distanciaParedInferior() < DA) {
                opcionFinal = opcion2;
            } else if (distanciaParedSuperior() < DA) {
                opcionFinal = opcion1;
            } else {

            }
        }


    private double predecirTiempoColision(JugadorAzul jugadorAzul) {
        // Calcular la distancia y la velocidad relativa entre los jugadores
        double relativePosX = jugadorAzul.getPosX() - this.getPosX();
        double relativePosY = jugadorAzul.getPosY() - this.getPosY();
        double relativeVelX = jugadorAzul.getVelX() - this.getVelX();
        double relativeVelY = jugadorAzul.getVelY() - this.getVelY();

        // Ecuación cuadrática para encontrar el tiempo hasta la colisión
        double a = relativeVelX * relativeVelX + relativeVelY * relativeVelY;
        double b = 2 * (relativePosX * relativeVelX + relativePosY * relativeVelY);
        double c = (relativePosX * relativePosX + relativePosY * relativePosY) - Math.pow(this.getRadio() + jugadorAzul.getRadio(), 2);

        double discriminante = b * b - 4 * a * c;

        if (discriminante < 0) {
            return -1;
        }

        // Calcular los posibles tiempos hasta la colisión
        double t1 = (-b - Math.sqrt(discriminante)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminante)) / (2 * a);

        // Retornar el menor tiempo positivo de colisión, si existe
        if (t1 > 0 && t2 > 0) {
            return Math.min(t1, t2);
        } else if (t1 > 0) {
            return t1;
        } else if (t2 > 0) {
            return t2;
        }

        return -1;
    }

    private double distanciaParedInferior() {
        return this.getPosY();
    }

    private double distanciaParedSuperior() {
        return 70 - this.getPosY();
    }

    private int countAzulesEnArea(Sistema sistema, double[] vector) {
        int count = 0;
        for(JugadorAzul j : sistema.getJugadoresAzules()) {
            //aqui debbo implementar la logica para saber si el jugador se encuentra en el rectanulo.
            if()
        }

    }


}


