package TP5.Jugador;

public class JugadorAzul extends Jugador {
    public JugadorAzul(double x, double y, double radio,double velocidadMaxima,double weight,double tau) {
        super(x, y, velocidadMaxima, radio,weight,tau);
    }

    public boolean perseguirJugadorRojo(Jugador jugadorRojo, double deltaTiempo) {
        double direccionX = jugadorRojo.getPosX() - this.posX;
        double direccionY = jugadorRojo.getPosY() - this.posY;
        double magnitud = Math.sqrt(direccionX * direccionX + direccionY * direccionY);

        if (magnitud > 0) {
            this.velX = (direccionX / magnitud) * this.velocidadMaxima;
            this.velY = (direccionY / magnitud) * this.velocidadMaxima;
        }

        this.actualizarPosicion(deltaTiempo);

        double d = Math.sqrt(Math.pow(this.posX - jugadorRojo.posX,2) + Math.pow(this.posY - jugadorRojo.posY,2));

        return d <= this.radio + jugadorRojo.radio;
    }

}

