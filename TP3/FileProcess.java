package TP3;


import TP3.Particle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FileProcess {
    public ArrayList<Particle> readFile(String dynamicFile, String staticFile,int v0) throws IOException {
        float r = 0.001F;
        BufferedReader dynamicReader = new BufferedReader(new FileReader(dynamicFile));
        BufferedReader staticReader = new BufferedReader(new FileReader(staticFile));
        String staticLine;
        String dynamicLine;
        int particleId = 1;
        int N = 0;
        if((staticLine = staticReader.readLine()) != null) {
            N = Integer.parseInt(staticLine);
            java.lang.System.out.println(N);
        }

        ArrayList<Particle> particles = new ArrayList<>();

        dynamicReader.readLine();
        staticReader.readLine();

        while (((dynamicLine = dynamicReader.readLine()) != null)) {
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
            float m = 1F;

            Particle part = new Particle(particleId, x,y,theta,r,v0,m); //TODO: VER BIEN ESTO
            particleId++;
            particles.add(part);
        }
        return particles;
    }
}

