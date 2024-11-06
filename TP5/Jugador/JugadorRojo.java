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

        @Override
        public void calcularVectorObjetivo(Sistema sistema) {
            JugadorAzul jugadorMasCercano = null;
            JugadorAzul segundoJugadorMasCercano = null;
            double menorDistancia = Double.MAX_VALUE;
            double[] direccionIngoal = {-1, 0};


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

            if (jugadorMasCercano == null){
                this.setTarget(direccionIngoal[0], direccionIngoal[1]);
                return;
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

            if (checkOutOfBounds(opcion1) || checkGoesBackwards(opcion1)){
                if (checkOutOfBounds(opcion2) || checkGoesBackwards(opcion2)){
                    opcionFinal = direccionIngoal;
                }else{
                    opcionFinal = opcion2;
                }
            }else if (checkOutOfBounds(opcion2) || checkGoesBackwards(opcion2)){
                opcionFinal = opcion1;
            }else {
                if(distanciaParedInferior() < DA) {
                    opcionFinal = opcion2;
                } else if (distanciaParedSuperior() < DA) {
                    opcionFinal = opcion1;
                } else {
                    double distancia1 = Math.sqrt(Math.pow(segundoJugadorMasCercano.getPosX() - (this.posX + opcion1[0]), 2) + Math.pow(segundoJugadorMasCercano.getPosY() - (this.posY + opcion1[1]), 2));
                    double distancia2 = Math.sqrt(Math.pow(segundoJugadorMasCercano.getPosX() - (this.posX + opcion2[0]), 2) + Math.pow(segundoJugadorMasCercano.getPosY() - (this.posY + opcion2[1]), 2));

                    if (distancia1 >= distancia2){
                        opcionFinal = opcion1;
                    }else {
                        opcionFinal = opcion2;
                    }
                }
            }



            System.out.println("Dirección de evasión: " + Arrays.toString(opcionFinal));

            double menorTiempoColision = predecirTiempoColision(jugadorMasCercano);

            // Paso 5: Calcular el factor sigmoidal s_a(d_c) usando la distancia al punto de colisión
            double sa = Utils.calcularSigmoid(menorTiempoColision);

            double magnitude = Math.sqrt(Math.pow(opcionFinal[0], 2) + Math.pow(opcionFinal[1], 2));
            if (magnitude != 0) { // Evitar división por cero
                opcionFinal[0] /= magnitude;
                opcionFinal[1] /= magnitude;
            }

            // Paso 6: Calcular la dirección final (n_i^t) como combinación ponderada de la dirección al ingoal y la de evasión
            double direccionFinalX = sa * opcionFinal[0] + (1 - sa) * direccionIngoal[0];
            double direccionFinalY = sa * opcionFinal[1] + (1 - sa) * direccionIngoal[1];

            // Paso 7: Actualizar el objetivo temporal del jugador rojo (g_i^t)
            this.setTarget(direccionFinalX, direccionFinalY);
        }

    private boolean checkGoesBackwards(double[] opcion) {
        return (this.posX + opcion[0]) > this.posX;
    }

    private boolean checkOutOfBounds(double[] opcion) {
        return (this.posX + opcion[0]) > 100 || (this.posY + opcion[1]) < 0 || (this.posY + opcion[1]) > 70;
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

//    private int countAzulesEnArea(Sistema sistema, double[] vector) {
//        int count = 0;
//        for(JugadorAzul j : sistema.getJugadoresAzules()) {
//            //aqui debbo implementar la logica para saber si el jugador se encuentra en el rectanulo.
//            if()
//        }
//
//    }


}


