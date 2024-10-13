package TP4;

import TP4.algorithms.Verlet;
import TP4.algorithms.VerletForSeveral;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static java.lang.System.exit;

public class system2 {
    public static void main(String[] args) throws IOException {
        double mass,tf,timeStep,amp;
        double omega1,omega2,omega3,omega4,omega5,omega6,omega7,omega8,omega9;
        double k1,k2,k3,k4,k5;
        int n;
        List<Double> omegas = new ArrayList<>();
        List<Double> ks = new ArrayList<>();


        Properties properties = new Properties();

        try{
            FileInputStream config = new FileInputStream("TP4/configs/conf2.config");
            properties.load(config);
        }catch(IOException e){
            e.printStackTrace();
        }

        k1 = Double.parseDouble(properties.getProperty("k"));
        k2 = Double.parseDouble(properties.getProperty("k2"));
        k3 = Double.parseDouble(properties.getProperty("k3"));
        k4 = Double.parseDouble(properties.getProperty("k4"));
        k5 = Double.parseDouble(properties.getProperty("k5"));
        mass = Double.parseDouble(properties.getProperty("m"));
        tf = Double.parseDouble(properties.getProperty("tf"));
        n = Integer.parseInt(properties.getProperty("n"));
        amp = Double.parseDouble(properties.getProperty("amp"));
        omega1 = Double.parseDouble(properties.getProperty("omega"));
        omega2 = Double.parseDouble(properties.getProperty("omega2"));
        omega3 = Double.parseDouble(properties.getProperty("omega3"));
        omega4 = Double.parseDouble(properties.getProperty("omega4"));
        omega5 = Double.parseDouble(properties.getProperty("omega5"));
        omega6 = Double.parseDouble(properties.getProperty("omega6"));
        omega7 = Double.parseDouble(properties.getProperty("omega7"));
        omega8 = Double.parseDouble(properties.getProperty("omega8"));
        omega9 = Double.parseDouble(properties.getProperty("omega9"));

        omegas.add(omega1);//8
        omegas.add(omega2);//8.5
        omegas.add(omega3);//9
        omegas.add(omega4);//9.5
        omegas.add(omega5);//10
        omegas.add(omega6);//10.5
        omegas.add(omega7);//11
        omegas.add(omega8);//11.5
        omegas.add(omega9);//12

//        ks.add(k1);
          ks.add(k2);
//        ks.add(k3);
//        ks.add(k4);
//        ks.add(k5);

//        for(Double k: ks) {
//            for (Double omega : omegas) {
                timeStep = 1/(100 * 10);
                runSystem(100,mass,10,timeStep,n,tf,amp);
//            }
//        }


    }


    private static void runSystem(double k,double mass,double omega,double timeStep,int particleCant,double totalTime,double amp){
        List<Particle> particles = new ArrayList<>();
//        File output = new File("TP4/outputs/System2/"+k+"_"+omega+".csv");
        File positions = new File("TP4/outputs/positions" + omega + "_" + k + ".csv");

        for(int i = 0; i < particleCant;i++){
            //INICIALIZO TODAS LAS PARTICULAS DEL SISTEMA
            //POS AND V iniciales = 0
            //NO USO GAMMA -> 0
            //MASA VIENE DE LOS DATOS AL IGUAL QUE OMEGA Y K
            //TIME STEP PRECALCULADO
            Particle aux = new Particle(0.0000,0,mass,k,0,timeStep,i,omega);
            particles.add(aux);
        }
        //INICIALIZO EL SISTEMA CON TODAS LAS PARTICULAS
        VerletForSeveral verlet = new VerletForSeveral(particles);
        double timeElapsed = timeStep;
        try(
//                BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        BufferedWriter writerPositions = new BufferedWriter(new FileWriter(positions))){
//            writer.write("time;a\n");
            writerPositions.write("x;y;time\n");

            double maxAmp = 0;
            while(timeElapsed < totalTime){
                //MIENTRAS EL TIEMPO INTERNO DEL SISTEMA SEA MENOR AL TIMEPO DE CORRIDA DE LA SIMULACION
                verlet.runAlgorithm2(timeElapsed);
                for(Particle p: particles){
                    writerPositions.write((p.getId())*0.01 + ";" + p.getPosition() + ";" + totalTime + "\n");
                    if(p.getPosition() > maxAmp && !Double.isInfinite(p.getPosition())){
                        maxAmp = p.getPosition();
                    }
                }
//                writer.write(timeElapsed+";"+maxAmp+"\n");
                timeElapsed+=timeStep;
            }
//            System.out.println("FOR K: "+k+" OMEGA: "+omega+" MAX AMP WAS: "+maxAmp+"\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //UNA VEZ TERMINADO EL TIMEPO VERIFICO EL MAX AMP

    }
}
