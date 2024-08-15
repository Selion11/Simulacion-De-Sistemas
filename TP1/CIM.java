package TP1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class CIM {
    private Square[][] squares = null;
    private boolean with_reflection;

    public static void main(String[] args) throws IOException {
        CIM cim = new CIM();
        HashMap<Particle,ArrayList<Particle>> rta = cim.CIM(false);
        AtomicInteger cantVecinas = new AtomicInteger();
        rta.forEach((k,v) -> {
                System.out.println("Particle: " + k.getId());
                System.out.println("Vecinas: ");
                for (Particle p : v) {
                    cantVecinas.addAndGet(1);
                    System.out.println(p.getId());
                }
        });
        System.out.println("Cantidad de vecinas: "+ cantVecinas);
    }

    private HashMap<Particle,ArrayList<Particle>> CIM(boolean with_reflection) throws IOException {
        this.with_reflection = with_reflection;
        FileProcess fileProcessor = new FileProcess();
        int L = 20;
        ArrayList<Particle> particles;
        particles = fileProcessor.readFile("TP1/dynamic_CIM_input.txt", "TP1/static_CIM_input.txt", 10F);
        int M = 10;
        calculations(L,M);


        for(Particle p : particles) {
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

        for(int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                squares[i][j].get_invertedL(squares, with_reflection);
            }
        }

        HashMap<Particle,ArrayList<Particle>> vecinas = new HashMap<>();
        for(Particle p : particles) {
            p.checkVecinas();
            vecinas.put(p,p.getVecinas());
        }

        System.out.println("Check Finished");
        return vecinas;
    }

    private void calculations(int L, int M){
        float step = (float) L /M;
        this.squares = new Square[M][M];
        float y_start;
        float y_finish;
        float x_start;
        float x_finish;
        int squareId = 1;

        for(int i = 0; i < M ; i++){
            y_finish = L - i *step;
            y_start = y_finish - step;

            for (int j = 0; j < M; j++) {
                x_start = j*step;
                x_finish = x_start + step;
                Square aux = new Square(squareId, x_start,x_finish,y_start,y_finish,i,j,M,L);
                squareId++;
                squares[i][j] = aux;
            }
        }


    }

}
