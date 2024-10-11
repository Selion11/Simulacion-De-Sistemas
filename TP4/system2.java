package TP4;

import TP4.algorithms.Verlet;

import java.io.*;
import java.util.*;

public class system2 {
    public static void main(String[] args) throws IOException {
        double k,m,tf,timeStep,printStep;
        double omega,omega2,omega3,omega4,omega5,omega6,omega7,omega8,omega9;
        double k2,k3,k4,k5;
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
        omega6 = Double.parseDouble(properties.getProperty("omega6"));
        omega7 = Double.parseDouble(properties.getProperty("omega7"));
        omega8 = Double.parseDouble(properties.getProperty("omega8"));
        omega9 = Double.parseDouble(properties.getProperty("omega9"));

        omegas.add(omega);
        omegas.add(omega2);
        omegas.add(omega3);
        omegas.add(omega4);
        omegas.add(omega5);
        omegas.add(omega6);
        omegas.add(omega7);
        omegas.add(omega8);
        omegas.add(omega9);

        ks.add(k);
        ks.add(k2);
        ks.add(k3);
        ks.add(k4);
        ks.add(k5);

        for(Double i: ks) {
            for (Double j : omegas) {
                timeStep = 1/(100 * j);
                runSystem(j,m,i,timeStep,n,tf);
            }
        }


    }

    private static void runSystem(double omega,double m,double k,double timeStep,int n,double tf){
        double totalTime = 0;
        Map<Integer,ParticleSys2> particles = new HashMap<Integer,ParticleSys2>();
        Map<Integer,Verlet> verletSystems = new HashMap<Integer,Verlet>();
        File output = new File("TP4/outputs/System2/"+omega+"_"+k+".csv");

        for(int i = 1;i <=n;i++){
            ParticleSys2 p = new ParticleSys2(0,omega,m,k,0,timeStep,i);
            particles.put(i,p);
        }

        Particle pn = new Particle(0,omega,m,k,0,timeStep);
        verletSystems.put(1,null);
        verletSystems.put(n,new Verlet(pn));

        for(int i = 2 ;i < n;i++){
            ParticleSys2 prevAxu = particles.get(i-1);
            ParticleSys2 actualAux = particles.get(i);
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
            writer.write("time;a;omega;k\n");
            while(totalTime < tf){
                for(int i = 1;i<=n;i++){
                    if(i == 1){
                        double aux_pos = (Math.cos(omega*timeStep));
                    }else if(i == n){
                        verletSystems.get(n).runAlgorithm();
                    }else{
                        verletSystems.get(i).runAlogrithmSys2();
                    }
                }
                totalTime += timeStep;
                double a = pn.getPosition()/Math.cos(omega*totalTime);
                if(a <= 1.0 && a >= -1.0){
                    writer.write(totalTime+";"+a+";"+omega+";"+k+"\n");
                }
            }
        }catch (IOException e) {
            e.printStackTrace();

        }
    }
}
