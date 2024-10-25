package TP5.Jugador;
public class JugadorRojo extends Jugador {

    public JugadorRojo(float posX, float posY, float radio, float velocidadMaxima, float objetivoX, float objetivoY) {
        super(posX, posY, radio, velocidadMaxima);
    }

    public void calcularVelocidadElusion(float objetivoTemporalX, float objetivoTemporalY) {
        float deltaX = objetivoTemporalX - this.posX;
        float deltaY = objetivoTemporalY - this.posY;
        this.actualizarVelocidad(deltaX, deltaY);
    }

    public boolean hizoTry() {
        return this.posX <= 0;
    }
}


