package TP2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class CIMOffLattice {
    private static Square[][] squares = null;
    private int L = 20;
    private static int M = 10;
    private static int N = 200;


    public static void main(String[] args) throws IOException {
        CIMOffLattice cim = new CIMOffLattice();
        ArrayList<ParticleOffLattice> particles = cim.CIM();
        int times = 250;
        float Va_values[] = new float[times];
        float cos_theta_sum = 0, sin_theta_sum = 0;

        String density = "200";

        File myFile;
        while(times > 0){
            myFile = new File("TP2/times/eta8/Desity_"+density+"/particles_time_" + (251-times)+".txt");
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
                float[] aux = updates(particles, cos_theta_sum, sin_theta_sum);
                cos_theta_sum = aux[0];
                sin_theta_sum = aux[1];
            }catch (IOException e) {
                e.printStackTrace();
            }
            Va_values[250-times] = (float) Math.sqrt(Math.pow(cos_theta_sum,2) + Math.pow(sin_theta_sum,2))/N;
            cos_theta_sum = 0;
            sin_theta_sum = 0;
            times -= 1;
        }
        File Va_values_file = new File("TP2/times/eta8/va/Va_values_" + density +".txt");
        try{
            Va_values_file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Va_values_file))) {
            for (float f : Va_values) {
                try {
                    writer.write(String.valueOf(f));
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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


    private static float[] updates(ArrayList<ParticleOffLattice> particles, float cos_theta_sum, float sin_theta_sum) {
        for(ParticleOffLattice p : particles) {
            p.checkVecinas();
        }

        for(ParticleOffLattice p : particles) {
            cos_theta_sum += (float) Math.cos(p.getOldTheta());
            sin_theta_sum += (float) Math.sin(p.getOldTheta());
            p.updateX();
            p.updateY();
            p.updateTheta();
        }

        regenerateSquares(particles);
        return new float[]{cos_theta_sum, sin_theta_sum};
    }

    private static void regenerateSquares(ArrayList<ParticleOffLattice> particles){
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
