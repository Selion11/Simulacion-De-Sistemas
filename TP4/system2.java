package TP4;

import TP4.algorithms.Beeman;
import TP4.algorithms.GearPredictorCorrector;
import TP4.algorithms.Verlet;

import java.io.*;
import java.util.*;

public class system2 {
    public static void main(String[] args) throws IOException {
        double k,m,tf,timeStep,printStep,omega,omega2,omega3,omega4,omega5,k2,k3,k4,k5;
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

        k = Double.parseDouble(properties.getProperty("k"));
        k2 = Double.parseDouble(properties.getProperty("k2"));
        k3 = Double.parseDouble(properties.getProperty("k3"));
        k4 = Double.parseDouble(properties.getProperty("k4"));
        k5 = Double.parseDouble(properties.getProperty("k5"));
        m = Double.parseDouble(properties.getProperty("m"));
        tf = Double.parseDouble(properties.getProperty("tf"));
        printStep = Double.parseDouble(properties.getProperty("printStep"));
        n = Integer.parseInt(properties.getProperty("n"));
        omega = Double.parseDouble(properties.getProperty("omega"));
        omega2 = Double.parseDouble(properties.getProperty("omega2"));
        omega3 = Double.parseDouble(properties.getProperty("omega3"));
        omega4 = Double.parseDouble(properties.getProperty("omega4"));
        omega5 = Double.parseDouble(properties.getProperty("omega5"));

        omegas.add(omega);
        omegas.add(omega2);
        omegas.add(omega3);
        omegas.add(omega4);
        omegas.add(omega5);

        ks.add(k);
        ks.add(k2);
        ks.add(k3);
        ks.add(k4);
        ks.add(k5);
        int run = 1;

        for(int i=0; i<5; i++){
            timeStep = 1/(100 * omegas.get(i));
            runSystem(run,omegas.get(i),m, ks.get(0), timeStep,n,printStep,tf);
            run += 1;
        }
        timeStep = 1 /(100 * omegas.get(0));
        for(int i=0; i<5; i++){
            runSystem(run,omegas.get(0),m, ks.get(i), timeStep,n,printStep,tf);
            run += 1;
        }

    }

    private static void runSystem(int run,double omega,double m,double k,double timeStep,int n,double printStep,double tf){
        double accumTime = 0;
        double totalTime = 0;
        Map<Integer,ParticleSys2> particles = new HashMap<Integer,ParticleSys2>();
        Map<Integer,Verlet> verletSystems = new HashMap<Integer,Verlet>();
        File output = new File("TP4/outputs/output_"+omega+"_"+k+"_"+run+"_SYS2.csv");

        for(int i = 1;i <=n;i++){
            ParticleSys2 p = new ParticleSys2(0,omega,m,k,0,timeStep,i);
            particles.put(i,p);
        }
        Particle pn = new Particle(0,omega,m,k,0,timeStep);
        verletSystems.put(1,null);
        verletSystems.put(n,null);
        verletSystems.put(n+1,new Verlet(pn));
        for(int i = 2 ;i < n;i++){
            ParticleSys2 actualAux = particles.get(i);
            ParticleSys2 prevAxu = particles.get(i-1);
            ParticleSys2 nextAxu = particles.get(i+1);
            Verlet aux = new Verlet(actualAux,prevAxu,nextAxu);
            verletSystems.put(i,aux);
        }

        try{
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))){
            writer.write("time;");
            for(int i = 1; i <= n;i++){
                writer.write("p"+i+";");
            }
            writer.write('\n');
            while(totalTime < tf){
                for(int i = 1;i<=n;i++){
                    if(i == 1){
                        float aux_pos = (float) (Math.cos(omega*totalTime));

                    }else if(i == n){
                        verletSystems.get(n+1).runAlgorithm();
                    }else{
                        verletSystems.get(i).runAlogrithmSys2();
                    }
                }

                totalTime += timeStep;
                accumTime += timeStep;
                if(accumTime >= printStep){
                    writer.write(totalTime+";");
                    for(int i = 1;i<=n;i++){
                        ParticleSys2 aux = particles.get(i);
                        if(i == n){
                            writer.write(aux.getPosition()+"\n");
                        }else {writer.write(aux.getPosition()+";");}
                    }
                    accumTime = 0;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();

        }
    }
}
