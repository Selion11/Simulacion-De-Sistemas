package TP5.Jugador;
public class JugadorRojo extends Jugador {

    public JugadorRojo(double posX, double posY, double radio, double velocidadMaxima,double weight,double tau) {
        super(posX, posY, radio, velocidadMaxima,weight,tau);
    }

    public boolean hizoTry() {
        return this.posX >= 100;
    }
}


