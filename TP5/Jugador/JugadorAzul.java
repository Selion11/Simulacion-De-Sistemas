package TP5.Jugador;
import TP5.Utils;
import TP5.helpers.Sistema;

public class JugadorAzul extends Jugador {
    int id;
    public JugadorAzul(double x, double y, double radio,double velocidadMaxima,double velX, double velY, double weight,double tau,int id) {
        super(x, y, radio, velocidadMaxima,velX, velY, weight,tau);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    @Override
    public void calcularVectorObjetivo(Sistema sistema) {
        JugadorRojo jugadorRojo = sistema.getJugadorRojo();

        double[] direccionRojo = Utils.calcularDireccion(this.getPosX(), this.getPosY(), jugadorRojo.getPosX(), jugadorRojo.getPosY());

        this.setTarget(direccionRojo[0], direccionRojo[1]);
    }
}

