package TP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import TP1.Particle;


public class FileProcess {
    public ArrayList<Particle> readFile(String dynamicFile, String staticFile, Float rc) throws IOException {
        BufferedReader dynamicReader = new BufferedReader(new FileReader(dynamicFile));
        BufferedReader staticReader = new BufferedReader(new FileReader(staticFile));
        String staticLine;
        String dynamicLine;
        int particleId = 1;
        int N = 0;
        if((staticLine = staticReader.readLine()) != null) {
            N = Integer.parseInt(staticLine);
            System.out.println(N);
        }

        ArrayList<Particle> particles = new ArrayList<>();

        dynamicReader.readLine();
        staticReader.readLine();

        while (((dynamicLine = dynamicReader.readLine()) != null) &&((staticLine = staticReader.readLine()) != null)) {
            String[] tokens = dynamicLine.split(" ");

            float aux = Float.parseFloat(tokens[0]);

            String aux2 = String.format("%.2f", aux);

            float x = Float.parseFloat(aux2);

            aux = Float.parseFloat(tokens[1]);

            aux2 = String.format("%.2f", aux);

            float y = Float.parseFloat(aux2);
            Particle part = new Particle(particleId, x,y,Float.parseFloat(staticLine),rc);
            particleId++;
            particles.add(part);
        }
        return particles;
    }
}

