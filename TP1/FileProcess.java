package TP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FileProcess {
    public particle[] readFile(String dynamicFile, String staticFile, Float rc) throws IOException {
        BufferedReader dynamicReader = new BufferedReader(new FileReader(dynamicFile));
        BufferedReader staticReader = new BufferedReader(new FileReader(staticFile));
        String staticLine;
        String dynamicLine;
        int N = 0;
        if((staticLine = staticReader.readLine()) != null) {
            N = Integer.parseInt(staticLine);
            System.out.println(N);
        }

        particle[] ans = new particle[N];

        dynamicReader.readLine();
        staticReader.readLine();

        int counter = 0;
        while (((dynamicLine = dynamicReader.readLine()) != null) &&((staticLine = staticReader.readLine()) != null)) {
            counter += 1;
            String[] tokens = dynamicLine.split(" ");

            float aux = Float.parseFloat(tokens[0]);

            String aux2 = String.format("%.2f", aux);

            float x = Float.parseFloat(aux2);

            aux = Float.parseFloat(tokens[1]);

            aux2 = String.format("%.2f", aux);

            float y = Float.parseFloat(aux2);
            particle part = new particle();
            part.setId(counter);
            part.setX(x);
            part.setY(y);
            part.setR(Float.parseFloat(staticLine));
            part.setRc(rc);
            ans[counter -1]  = part;
        }
        return ans;
    }
}

