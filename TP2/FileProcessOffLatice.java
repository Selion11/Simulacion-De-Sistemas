package TP2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import TP2.ParticleOffLattice;


public class FileProcessOffLatice {
    public ArrayList<ParticleOffLattice> readFile(String dynamicFile, String staticFile, Float rc) throws IOException {
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

        ArrayList<ParticleOffLattice> particles = new ArrayList<>();

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

            aux = Float.parseFloat(tokens[2]);
            aux2 = String.format("%.2f", aux);
            float theta = Float.parseFloat(aux2);
            float mu = 2.0F;

            ParticleOffLattice part = new ParticleOffLattice(particleId, x,y,theta,rc,mu); //TODO: VER BIEN ESTO
            particleId++;
            particles.add(part);
        }
        return particles;
    }
}

