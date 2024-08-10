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
        int total = M*M;
        square[] squares = new square[total];
        float y_start = 0.0F;
        float y_finish = step;
        int counter = 0;
        for(int i = 0; i < 25 ; i++){
            float x_start = 0.0F;
            float x_finish = step;
            int ctr = 1;
            while(ctr <= 25){
                square aux = new square(x_start,x_finish,y_start,y_finish,i + 1);
                ctr ++;
                x_start += step;
                x_finish += step;
                squares[counter] = aux;
                counter ++;
            }
            y_start += step;
            y_finish += step;
        }
        return squares;
    }
}
