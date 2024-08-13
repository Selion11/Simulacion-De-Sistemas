package TP1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CIM {
    private square[][] squares = null;
    private boolean with_reflection;

    public HashMap<particle,ArrayList<particle>> CIM(boolean with_reflection) throws IOException {
        this.with_reflection = with_reflection;
        FileProcess fileProcessor = new FileProcess();
        int L = 20;
        ArrayList<particle> particles;
        particles = fileProcessor.readFile("TP1/dynamic_CIM_input.txt", "TP1/static_CIM_input.txt", 1.0F);
        int M = 25;
        calculations(L,M);


        for(particle p : particles) {
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    if(squares[i][j].checkParticle(p)){
                        squares[i][j].add_particle(p);
                        p.set_square(squares[i][j]);
                        break;
                    }                   
                }
            }
        }

        HashMap<particle,ArrayList<particle>> vecinas = new HashMap<>();
        for(particle p : particles) {
            vecinas.put(p,p.getVecinas());
        }

        System.out.println("Check Finished");
        return vecinas;
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
                square aux = new square(x_start,x_finish,y_start,y_finish,i,j,M,L);
                squares[i][j] = aux;
            }
        }

        for(int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                squares[i][j].get_invertedL(squares, with_reflection);
            }
        }
    }

}
