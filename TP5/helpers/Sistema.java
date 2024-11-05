package TP5.helpers;
import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;
import java.util.List;

public class Sistema {
    private JugadorRojo jugadorRojo;
    private List<JugadorAzul> jugadoresAzules;

    public Sistema(JugadorRojo jugadorRojo, List<JugadorAzul> jugadoresAzules) {
        this.jugadorRojo = jugadorRojo;
        this.jugadoresAzules = jugadoresAzules;
    }

    public JugadorRojo getJugadorRojo() {
        return jugadorRojo;
    }

    public List<JugadorAzul> getJugadoresAzules() {
        return jugadoresAzules;
    }
}
