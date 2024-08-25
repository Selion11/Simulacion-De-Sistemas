package TP2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class CIMOffLattice {
    private Square[][] squares = null;
    private int L = 20;
    private int M = 10;

    public static void main(String[] args) throws IOException {
        CIMOffLattice cim = new CIMOffLattice();
        ArrayList<ParticleOffLattice> particles = cim.CIM();
        int times = 250;
        File myFile;
        while(times >= 0){
            myFile = new File("TP2/times/particles_time_" + (251-times)+".txt");
            try{
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile))) {
                for(ParticleOffLattice p : particles){
                    try {
                        writer.write(p.toString());
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                cim.updates(particles);
            }catch (IOException e) {
                e.printStackTrace();
            }
            times -= 1;
        }

    }

    private ArrayList<ParticleOffLattice> CIM() throws IOException {
        FileProcessOffLatice fileProcessor = new FileProcessOffLatice();
        ArrayList<ParticleOffLattice> particles;
        particles = fileProcessor.readFile("TP2/dynamic_CIM_input.txt", "TP2/static_CIM_input.txt", 5F);


        calculations(L,M);

        regenerateSquares(particles);

        System.out.println("Check Finished");
        return particles;
    }

    public void updates(ArrayList<ParticleOffLattice> particles) {

        for(ParticleOffLattice p : particles) {
            p.checkVecinas();
        }
        for(ParticleOffLattice p : particles) {
            p.updateX();
            p.updateY();
            p.updateTheta();
        }

        regenerateSquares(particles);
    }

    private void regenerateSquares(ArrayList<ParticleOffLattice> particles){
        for(ParticleOffLattice p : particles) {
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
                squares[i][j].get_invertedL(squares, true);
            }
        }
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
