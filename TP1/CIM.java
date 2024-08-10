package TP1;

import java.io.IOException;

public class CIM {
    public static void main(String[] args) throws IOException {
        FileProcess fileProcessor = new FileProcess();
        int L = 20;
        particle[] particles = fileProcessor.readFile("TP1/dynamic_CIM_input.txt", "TP1/static_CIM_input.txt", 1.0F);
        int M = 25;

        calculations(L,M);
    }

    private static void calculations(int L, int M){
        float start = 0.0F;
        float step = (float) L /M;
        float finish = step;
        int counter = 1;
        while(finish <= 20){
            System.out.println("Step " + counter + " Start: " + start + " Finish: " + finish);
            start += step;
            finish += step;
            counter++;
        }
    }
}
