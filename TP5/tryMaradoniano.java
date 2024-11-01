package TP5;

import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TryMaradoniano {
    public static void main(String[] args) {
        double vmaxRojo, vmaxAzul, treacAzul, treacRojo, n, largo, ancho, radio, rojoXInicial, rojoYInicial, dt;
        double totalTime = 0;

        Properties properties = new Properties();
        try {
            FileInputStream config = new FileInputStream("TP4/configs/conf.config");
            properties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        vmaxRojo = Double.parseDouble(properties.getProperty("vmaxrojo"));
        vmaxAzul = Double.parseDouble(properties.getProperty("vmaxazul"));
        treacAzul = Double.parseDouble(properties.getProperty("treacazul"));
        treacRojo = Double.parseDouble(properties.getProperty("treacrojo"));
        n = Double.parseDouble(properties.getProperty("n"));
        largo = Double.parseDouble(properties.getProperty("largo"));
        ancho = Double.parseDouble(properties.getProperty("ancho"));
        radio = Double.parseDouble(properties.getProperty("radio"));
        rojoXInicial = Double.parseDouble(properties.getProperty("rojoxinicial"));
        rojoYInicial = Double.parseDouble(properties.getProperty("rojoyinicial"));
        dt = Double.parseDouble(properties.getProperty("dt"));

        // Inicializar jugador rojo y lista de jugadores azules
        JugadorRojo jugadorRojo = new JugadorRojo(rojoXInicial, rojoYInicial, radio, vmaxRojo);
        List<JugadorAzul> jugadoresAzules = generarJugadoresAzules(n, vmaxAzul, radio, largo, ancho);

        boolean tackled = false;
        while (!tackled && !jugadorRojo.hizoTry()) {

            Utils.calcularVectorObjetivo(jugadorRojo, jugadoresAzules, dt);
            Utils.actualizarPosicion(jugadorRojo, dt);

            // Actualizar la posición de cada jugador azul y verificar colisiones
            for (JugadorAzul jugadorAzul : jugadoresAzules) {
                jugadorAzul.perseguirJugadorRojo(jugadorRojo, dt);
                Utils.actualizarPosicion(jugadorAzul, dt);
                if (Utils.detectarColision(jugadorRojo, jugadorAzul)) {
                    tackled = true;
                    break;
                }
            }

            totalTime += dt;
        }

        if (jugadorRojo.hizoTry()) {
            System.out.println("¡Try logrado en tiempo: " + totalTime + " segundos!");
        } else {
            System.out.println("Jugador rojo fue detenido en tiempo: " + totalTime + " segundos.");
        }
    }

    private static List<JugadorAzul> generarJugadoresAzules(double cantidad, double vmax, double radio, double largo, double ancho) {
        List<JugadorAzul> jugadoresAzules = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            double x, y;
            boolean posicionValida;

            do {
                x = random.nextDouble() * (largo - 2 * radio) + radio; // Genera dentro del campo y evita bordes
                y = random.nextDouble() * (ancho - 2 * radio) + radio;
                posicionValida = true;

                // Verificar que no haya superposición inicial con otros jugadores
                for (JugadorAzul ja : jugadoresAzules) {
                    double distX = x - ja.getPosX();
                    double distY = y - ja.getPosY();
                    double distancia = Math.sqrt(distX * distX + distY * distY);

                    if (distancia < 2 * radio) {
                        posicionValida = false;
                        break;
                    }
                }
            } while (!posicionValida);

            jugadoresAzules.add(new JugadorAzul(x, y, radio, vmax));
        }
        return jugadoresAzules;
    }
}
