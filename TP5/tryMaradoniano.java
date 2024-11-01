package TP5;

import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;
import TP5.helpers.Acceleration;

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
        double vmaxRojo, vmaxAzul, treacAzul, treacRojo, n, largo, ancho, radio, rojoXInicial, rojoYInicial, dt,m,kt,kn,dt2;
        double totalTime = 0;

        Properties properties = new Properties();
        try {
            FileInputStream config = new FileInputStream("TP5/configs/config.config");
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
        m = Double.parseDouble(properties.getProperty("m"));
        kn = Double.parseDouble(properties.getProperty("kn"));
        kt = Double.parseDouble(properties.getProperty("kt"));
        dt2 = Double.parseDouble(properties.getProperty("dt2"));

        // Inicializar jugador rojo y lista de jugadores azules
        JugadorRojo jugadorRojo = new JugadorRojo(rojoXInicial, rojoYInicial, radio, vmaxRojo,m,treacRojo);
        List<JugadorAzul> jugadoresAzules = generarJugadoresAzules(n, vmaxAzul, radio, largo, ancho,m,treacAzul);

        File output = new File("TP5/output/output.csv");

        try {
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("x;y;vx;vy;tiempo\n");

            boolean tackled = false;
            double auxTime = 0;
            writePlayer(totalTime, writer, jugadorRojo.getPosX(), jugadorRojo.getPosY(), jugadorRojo.getVelX(), jugadorRojo.getVelY());

            for (JugadorAzul j : jugadoresAzules) {
                writePlayer(totalTime, writer, j.getPosX(), j.getPosY(), j.getVelX(), j.getVelY());
            }
            while (!tackled && !jugadorRojo.hizoTry()) {

                if(auxTime >= dt2) {
                    writePlayer(totalTime, writer, jugadorRojo.getPosX(), jugadorRojo.getPosY(), jugadorRojo.getVelX(), jugadorRojo.getVelY());

                    for (JugadorAzul j : jugadoresAzules) {
                        writePlayer(totalTime, writer, j.getPosX(), j.getPosY(), j.getVelX(), j.getVelY());
                    }
                    auxTime = 0;
                }

                Utils.calcularVectorObjetivo(jugadorRojo, jugadoresAzules, dt);
                Double[] redA = Utils.calculateAcceleration(jugadorRojo,new ArrayList<>(),kn,kt);
                jugadorRojo.beemanIntegration(redA[0],redA[1],dt);
                if(jugadorRojo.getPosY() >= 70){
                    tackled = true;
                    break;
                }
                // Actualizar la posición de cada jugador azul y verificar colisiones
                for (JugadorAzul jugadorAzul : jugadoresAzules) {
                    List<JugadorAzul> inContact = new ArrayList<>();
                    for(JugadorAzul j: jugadoresAzules) {
                        if(j.getId() != jugadorAzul.getId()){
                            double cv = Utils.contactVariable(j,jugadorAzul);
                            if(cv < 0){
                                inContact.add(j);
                            }
                        }

                    }
                    jugadorAzul.setTarget(jugadorRojo.getPosX(), jugadorAzul.getPosY());
                    Double[] accel = Utils.calculateAcceleration(jugadorAzul,inContact,kn,kt);
                    jugadorAzul.beemanIntegration(accel[0],accel[1],dt);
                    if (Utils.detectarColision(jugadorRojo, jugadorAzul)) {
                        tackled = true;
                        break;
                    }
                }

                totalTime += dt;
                auxTime += dt;
            }
            System.out.println("TACKLED: "+tackled);
            writePlayer(totalTime, writer, jugadorRojo.getPosX(), jugadorRojo.getPosY(), jugadorRojo.getVelX(), jugadorRojo.getVelY());

            for (JugadorAzul j : jugadoresAzules) {
                writePlayer(totalTime, writer, j.getPosX(), j.getPosY(), j.getVelX(), j.getVelY());
            }
        }catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void writePlayer(double totalTime, BufferedWriter writer, double posX, double posY, double velX, double velY) throws IOException {
        writer.write(String.valueOf(posX));
        writer.write(';');
        writer.write(String.valueOf(posY));
        writer.write(';');
        writer.write(String.valueOf(velX));
        writer.write(';');
        writer.write(String.valueOf(velY));
        writer.write(';');
        writer.write(String.valueOf(totalTime));
        writer.write('\n');
    }

    private static List<JugadorAzul> generarJugadoresAzules(double cantidad, double vmax, double radio, double largo, double ancho,double weight,double tau) {
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

            jugadoresAzules.add(new JugadorAzul(x, y, radio, vmax,weight,tau,i));
        }
        return jugadoresAzules;
    }



}
