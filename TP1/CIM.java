package TP1;

import java.io.IOException;

public class CIM {
    public static void main(String[] args) throws IOException {
        FileProcess fileProcessor = new FileProcess();
        int L = 20;
        particle[] particles = fileProcessor.readFile("TP1/dynamic_CIM_input.txt", "TP1/static_CIM_input.txt", 1.0F);
        int M = 25;
        square[] squares = calculations(L,M);

        for(particle p : particles) {
            int check = 0;
            int s = 0;
            while(check == 0){
                check = squares[s].add_particle(p);
                s++;
            }
        }
        System.out.println("Check Finished");
    }

    private static square[] calculations(int L, int M){
        float step = (float) L /M;
        float start = 0.0F;
        float finish = step;
        int counter = 0;
        square[] squares = new square[M];
        while(finish <= 20){
            square aux = new square(start,finish,start,finish,counter +1);
            start += step;
            finish += step;
            squares[counter] = aux;
            counter++;
        }
        return squares;
    }
}
