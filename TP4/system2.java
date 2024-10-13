package TP4;

import TP4.algorithms.MultiParticleSystem;
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

        for(Double k: ks) {
            for (Double omega : omegas) {
                timeStep = 1/(100 * omega);
                runSystem(k,mass,omega,timeStep,n,tf,amp);
            }
        }


    }

//    private static void runSystem(double omega,double m,double k,double timeStep,int n,double tf,double a){
//        double totalTime = 0;
//        File output = new File("TP4/outputs/System2/"+omega+"_"+k+".csv");
//
//        Function<Double, Double> positionSolution = time_input -> (a
//                 * Math.exp(-(gamma / (2 * m)) * time_input) *
//                 Math.cos(Math.pow((k / m) - (Math.pow(gamma, 2) / (4 * Math.pow(m, 2))), 0.5) * time_input));
//
//        List<ParticleSys2> particles = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            ParticleSys2 aux = new ParticleSys2(0.0,0.0,i);
//            particles.add(aux);
//        }
//
//        MultiParticleSystem system = new MultiParticleSystem(omega,a,k,tf,timeStep,gamma,particles,positionSolution,n);
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(output))){
//            writer.write("time;a\n");
//            while(totalTime < tf) {
//                system.advanceSystem();
//                double max_pos = 0;
//                for (int i = 0; i < n; i++) {
//                    if (system.getParticlePosition(i) > max_pos) {
//                        max_pos = system.getParticlePosition(i);
//                    }
//                }
//                totalTime += timeStep;
//                if(max_pos != 0.0) {
//                    writer.write(totalTime + ";" + max_pos + "\n");
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    private static void runSystem(double k,double mass,double omega,double timeStep,int particleCant,double totalTime,double amp){
        List<Particle> particles = new ArrayList<>();
        File output = new File("TP4/outputs/System2/"+k+"_"+omega+".csv");
        //System.out.println("K: "+k+" MASS: "+mass+" TIMESTEP: "+timeStep+" OMEGA: "+omega+" TOTAL TIME: "+totalTime);
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
        VerletForSeveral verlet = new VerletForSeveral(particles,amp);
        double timeElapsed = timeStep;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(output))){
            writer.write("time;a\n");
            double maxAmp = 0;
            while(timeElapsed < totalTime){
                //MIENTRAS EL TIEMPO INTERNO DEL SISTEMA SEA MENOR AL TIMEPO DE CORRIDA DE LA SIMULACION
                verlet.runAlgorithm2(timeElapsed);
                for(Particle p: particles){
                    if(p.getPosition() > maxAmp && !Double.isInfinite(p.getPosition())){
                        maxAmp = p.getPosition();
                    }
                }
                writer.write(timeElapsed+";"+maxAmp+"\n");
                timeElapsed+=timeStep;
            }
            System.out.println("FOR K: "+k+" OMEGA: "+omega+" MAX AMP WAS: "+maxAmp+"\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //UNA VEZ TERMINADO EL TIMEPO VERIFICO EL MAX AMP

    }
}
