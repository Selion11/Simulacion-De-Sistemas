package TP4;

import TP4.algorithms.Sys2Verlet;
import TP4.algorithms.Verlet;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

public class system2 {
    public static void main(String[] args) throws IOException {
        double m,tf,timeStep,a;
        double omega,omega2,omega3,omega4,omega5,omega6,omega7,omega8,omega9;
        double k,k2,k3,k4,k5;
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
        n = Integer.parseInt(properties.getProperty("n"));
        a = Double.parseDouble(properties.getProperty("a"));
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
//        omegas.add(omega2);
//        omegas.add(omega3);
//        omegas.add(omega4);
//        omegas.add(omega5);
//        omegas.add(omega6);
//        omegas.add(omega7);
//        omegas.add(omega8);
//        omegas.add(omega9);

        ks.add(k);
//        ks.add(k2);
//        ks.add(k3);
//        ks.add(k4);
//        ks.add(k5);

        for(Double i: ks) {
            for (Double j : omegas) {
                timeStep = 1/(100 * j);
                //System.out.println("K: "+i+" Omega: "+j+" TimeStep: "+timeStep);
                runSystem(j,m,i,timeStep,n,tf,a);
            }
        }


    }

    private static void runSystem(double omega,double m,double k,double timeStep,int n,double tf,double a){
        double totalTime = 0;

        Map<Integer,ParticleSys2> particles = new HashMap<Integer,ParticleSys2>();
        Sys2Verlet v = new Sys2Verlet(m,k,timeStep);
        File output = new File("TP4/outputs/System2/"+omega+"_"+k+".csv");
        for(int i = 1; i <= n; i++){
            ParticleSys2 aux = new ParticleSys2(0.0,0.0);
            particles.put(i,aux);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))){
            writer.write("time;a\n");
            while(totalTime < tf){
                totalTime += timeStep;
                double maxPos = 0;
                for(int i = 1; i <= n; i++){
                    ParticleSys2 curr = particles.get(i);
                    double force = 0;
                    if(i == 1){
                        force = v.firstPartcileInForce(curr,particles.get(i+1));
                        v.updatePartcile(curr,force);
                    }else if(i > 1 && i < n){
                        force = v.systemForce(particles.get(i-1),curr,particles.get(i+1));
                        v.updatePartcile(curr,force);
                    }else if(i == n){
                        v.lastParticleSys2(curr,totalTime,omega,a);
                    }
                    if(curr.getPosition() > maxPos){
                        maxPos = curr.getPosition();
                    }
                    if(maxPos > 1E234){
                        System.out.println("ERROR IN PARTICLE: "+i+" POS: "+maxPos+" DT: "+timeStep
                                +" FORCE: "+force+" K: "+k+" M:"+m+"\n");
                        exit(0);
                    }
                }
                if(maxPos != 0.0) {
                    //writer.write(totalTime+";"+maxPos+"\n");
                    //System.out.println(maxPos);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();

        }
    }

}
