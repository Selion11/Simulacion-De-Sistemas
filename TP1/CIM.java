package TP1;

import java.io.IOException;

public class CIM {
    private square[][] squares = null;

    public CIM(String[] args) throws IOException {
        FileProcess fileProcessor = new FileProcess();
        int L = 20;
        particle[] particles = fileProcessor.readFile("TP1/dynamic_CIM_input.txt", "TP1/static_CIM_input.txt", 1.0F);
        int M = 25;
        calculations(L,M);


        for(particle p : particles) {
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    if(squares[i][j].checkParticle(p)){
                        squares[i][j].add_particle(p);
                        break;
                    }                   
                }    
            }
        }

        System.out.println("Check Finished");
    }

    private void calculations(int L, int M){
        float step = (float) L /M;
        this.squares = new square[M][M];
        float y_start;
        float y_finish;
        float x_start;
        float x_finish;

        for(int i = 0; i < M ; i++){
            y_finish = L - i *step;
            y_start = y_finish - step;

            for (int j = 0; j < M; j++) {
                x_start = j*step;
                x_finish = x_start + step;

                square aux = new square(x_start,x_finish,y_start,y_finish,i,j,M);
                squares[i][j] = aux;
            }
        }
    }
}
