package TP5.Jugador;
public class JugadorRojo extends Jugador {

    public JugadorRojo(double posX, double posY, double radio, double velocidadMaxima,double weight,double tau) {
        super(posX, posY, radio, velocidadMaxima,weight,tau);
    }

    public void calcularVelocidadElusion(double objetivoTemporalX, double objetivoTemporalY) {
        double deltaX = objetivoTemporalX - this.posX;
        double deltaY = objetivoTemporalY - this.posY;
        this.actualizarVelocidad(deltaX, deltaY);
    }

    public boolean hizoTry() {
        return this.posX <= 0;
    }
}


