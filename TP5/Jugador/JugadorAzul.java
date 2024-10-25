package TP5.Jugador;

public class JugadorAzul extends Jugador {
    public JugadorAzul(float x, float y, float velocidadMaxima, float radio) {
        super(x, y, velocidadMaxima, radio);
    }

    public void perseguirJugadorRojo(Jugador jugadorRojo, float deltaTiempo) {
        float direccionX = jugadorRojo.getPosX() - this.posX;
        float direccionY = jugadorRojo.getPosY() - this.posY;
        float magnitud = (float) Math.sqrt(direccionX * direccionX + direccionY * direccionY);

        if (magnitud > 0) {
            this.velX = (direccionX / magnitud) * this.velocidadMaxima;
            this.velY = (direccionY / magnitud) * this.velocidadMaxima;
        }

        this.actualizarPosicion(deltaTiempo);
    }
}

