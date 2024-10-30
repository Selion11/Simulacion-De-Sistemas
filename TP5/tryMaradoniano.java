package TP5;

import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class tryMaradoniano {
    public static void main(String[] args) {
        double vmaxrojo, vmaxazul,treacazul,treacrojo,n,largo, ancho,radio,rojoxinicial,rojoyinicial,objetivoinicialx,objetivoinicialy,dt ;
        double totalTime = 0;

        Properties properties = new Properties();

        try {
            FileInputStream config = new FileInputStream("TP4/configs/conf.config");
            properties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        vmaxrojo = Double.parseDouble(properties.getProperty("vmaxrojo"));
        vmaxazul = Double.parseDouble(properties.getProperty("vmaxazul"));
        treacazul = Double.parseDouble(properties.getProperty("treacazul"));
        treacrojo = Double.parseDouble(properties.getProperty("treacrojo"));
        n = Double.parseDouble(properties.getProperty("n"));
        largo = Double.parseDouble(properties.getProperty("largo"));
        ancho = Double.parseDouble(properties.getProperty("ancho"));
        radio = Double.parseDouble(properties.getProperty("radio"));
        rojoxinicial = Double.parseDouble(properties.getProperty("rojoxinicial"));
        rojoyinicial = Double.parseDouble(properties.getProperty("rojoyinicial"));
        objetivoinicialx = Double.parseDouble(properties.getProperty("objetivoinicialx"));
        objetivoinicialy = Double.parseDouble(properties.getProperty("objetivoinicialy"));
        dt = Double.parseDouble(properties.getProperty("dt"));


        JugadorRojo jugadorRojo = new JugadorRojo(rojoxinicial,rojoyinicial,radio,vmaxrojo);

        List<JugadorAzul> jugadoresAzules = new ArrayList<>();

        Random random = new Random();
        double x,y;

        // TODO: No esta implementado para que no caigan dos azules en el mismo lugar o esten bajo el mismo radio
        for (int i = 0; i < n; i++) {
            x = random.nextDouble(1,99);
            y = random.nextDouble(1,34);
            jugadoresAzules.add(new JugadorAzul(x,y, vmaxazul,radio));
        }

        boolean tackled = false;
        while (!tackled && !jugadorRojo.hizoTry()){

            Utils.calcularVectorObjetivo(jugadorRojo,jugadoresAzules,totalTime);

            for (JugadorAzul ja:
                 jugadoresAzules) {
                tackled = ja.perseguirJugadorRojo(jugadorRojo,totalTime);
                if (tackled){
                    break;
                }
            }

            totalTime += dt;
        }
    }
}
