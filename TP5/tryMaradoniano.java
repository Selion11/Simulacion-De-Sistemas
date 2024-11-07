package TP5;

import TP5.Jugador.Jugador;
import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;
import TP5.helpers.Acceleration;
import TP5.helpers.Sistema;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class tryMaradoniano {
    public static void main(String[] args) {
        // Configuración de propiedades y variables iniciales
        Properties properties = new Properties();
        double totalTime = 0, auxTime = 0;

        try (FileInputStream config = new FileInputStream("TP5/configs/config.config")) {
            properties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Variables del sistema
        double vmaxRojo = Double.parseDouble(properties.getProperty("vmaxrojo"));
        double vmaxAzul = Double.parseDouble(properties.getProperty("vmaxazul"));
        double treacAzul = Double.parseDouble(properties.getProperty("treacazul"));
        double treacRojo = Double.parseDouble(properties.getProperty("treacrojo"));
        double n = Double.parseDouble(properties.getProperty("n"));
        double largo = Double.parseDouble(properties.getProperty("largo"));
        double ancho = Double.parseDouble(properties.getProperty("ancho"));
        double radio = Double.parseDouble(properties.getProperty("radio"));
        double rojoXInicial = Double.parseDouble(properties.getProperty("rojoxinicial"));
        double rojoYInicial = Double.parseDouble(properties.getProperty("rojoyinicial"));
        double dt = Double.parseDouble(properties.getProperty("dt"));
        double m = Double.parseDouble(properties.getProperty("m"));
        double kn = Double.parseDouble(properties.getProperty("kn"));
        double kt = Double.parseDouble(properties.getProperty("kt"));
        double dt2 = Double.parseDouble(properties.getProperty("dt2"));
        // Archivo de salida
        File output = new File("TP5/output/output.csv");
        try {
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Escritura de los datos de simulación
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("x;y;vx;vy;tiempo;run;tackled\n");
            for (int i = 0; i < 500; i++) {
                // Inicialización de los jugadores
                JugadorRojo jugadorRojo = new JugadorRojo(rojoXInicial, rojoYInicial, radio, vmaxRojo, -vmaxRojo, 0, m, treacRojo, 10.0);
                List<JugadorAzul> jugadoresAzules = generarJugadoresAzules(n, vmaxAzul, radio, largo, ancho, m, treacAzul);
                Sistema sistema = new Sistema(jugadorRojo, jugadoresAzules);
                boolean tackled = false;
                writeState(writer, totalTime, jugadorRojo, jugadoresAzules,i,tackled);

                // Bucle de simulación
                while (!tackled && !jugadorRojo.hizoTry()) {
                    if (auxTime >= dt2) {
                        writeState(writer, totalTime, jugadorRojo, jugadoresAzules,i,tackled);
                        auxTime = 0;
                    }

                    // Calcular el vector objetivo y aceleración del jugador rojo
                    jugadorRojo.calcularVectorObjetivo(sistema);
                    Double[] redA = Utils.calculateAcceleration(jugadorRojo, new ArrayList<>(), kn, kt);
                    jugadorRojo.beemanIntegration(redA[0], redA[1], dt);

                    // Verificar colisión con las paredes
                    if (jugadorRojo.getPosY() >= 70 || jugadorRojo.getPosY() <= 0) {
                        tackled = true;
                        break;
                    }

                    // Calcular el vector objetivo y actualizar los jugadores azules
                    for (JugadorAzul jugadorAzul : jugadoresAzules) {
                        List<JugadorAzul> inContact = filtrarJugadoresEnContacto(jugadorAzul, jugadoresAzules);
                        jugadorAzul.calcularVectorObjetivo(sistema);
                        Double[] azulA = Utils.calculateAcceleration(jugadorAzul, inContact, kn, kt);
                        jugadorAzul.beemanIntegration(azulA[0], azulA[1], dt);

                        // Verificar colisión con el jugador rojo
                        if (Utils.detectarColision(jugadorRojo, jugadorAzul)) {
                            tackled = true;
                            break;
                        }
                    }

                    totalTime += dt;
                    auxTime += dt;
                }

                // Escribir el estado final
                writeState(writer, totalTime, jugadorRojo, jugadoresAzules,i,tackled);
                System.out.println("TACKLED: " + tackled);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para escribir el estado del jugador rojo y los jugadores azules en el archivo
    private static void writeState(BufferedWriter writer, double time, JugadorRojo rojo, List<JugadorAzul> azules,int i,boolean t) throws IOException {
        writePlayer(writer, time, rojo,i,t);
        for (JugadorAzul azul : azules) {
            writePlayer(writer, time, azul,i,!t);
        }
    }

    private static void writePlayer(BufferedWriter writer, double time, Jugador jugador,int i,boolean t) throws IOException {
        writer.write(jugador.getPosX() + ";" + jugador.getPosY() + ";" + jugador.getVelX() + ";" + jugador.getVelY() + ";" + time + ";" + i +  ";" + t + "\n");
    }

    // Filtrar jugadores en contacto con un jugador azul específico
    private static List<JugadorAzul> filtrarJugadoresEnContacto(JugadorAzul jugador, List<JugadorAzul> jugadoresAzules) {
        List<JugadorAzul> inContact = new ArrayList<>();
        for (JugadorAzul j : jugadoresAzules) {
            if (j.getId() != jugador.getId() && Utils.contactVariable(j, jugador) < 0) {
                inContact.add(j);
            }
        }
        return inContact;
    }

    private static List<JugadorAzul> generarJugadoresAzules(double cantidad, double vmax, double radio, double largo, double ancho,double weight,double tau) {
        List<JugadorAzul> jugadoresAzules = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            double x, y;
            boolean posicionValida;

            do {
                x = random.nextDouble() * (0.7*largo - 2 * radio) + radio; // Genera dentro del campo y evita bordes
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

            jugadoresAzules.add(new JugadorAzul(x, y, radio, vmax, 0, 0, weight,tau,i));
        }
        return jugadoresAzules;
    }



}
