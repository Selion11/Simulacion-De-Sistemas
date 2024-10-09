package TP4;

import TP4.algorithms.Beeman;
import TP4.algorithms.GearPredictorCorrector;
import TP4.algorithms.Verlet;

import java.io.*;
import java.util.*;

public class system2 {
    public static void main(String[] args) throws IOException {
        float k,l0,m,tf,timeStep,printStep,omega,a;
        int n;
        float accumTime = 0;
        float totalTime = 0;

        Properties properties = new Properties();

        try{
            FileInputStream config = new FileInputStream("TP4/configs/conf2.config");
            properties.load(config);
        }catch(IOException e){
            e.printStackTrace();
        }

        k = Float.parseFloat(properties.getProperty("k"));
        l0 = Float.parseFloat(properties.getProperty("l0"));
        a = Float.parseFloat(properties.getProperty("a"));
        m = Float.parseFloat(properties.getProperty("m"));
        tf = Float.parseFloat(properties.getProperty("tf"));
        printStep = Float.parseFloat(properties.getProperty("printStep"));
        n = Integer.parseInt(properties.getProperty("n"));
        omega = Float.parseFloat(properties.getProperty("omega"));

        timeStep = 1/(100 * omega);

        Map<Integer,ParticleSys2> particles = new HashMap<Integer,ParticleSys2>();
        Map<Integer,Verlet> verletSystems = new HashMap<Integer,Verlet>();

        File output = new File("TP4/outputs/output_"+timeStep+"_"+printStep+"_SYS2.txt");

        for(int i = 1;i <= n;i++){
            ParticleSys2 p = new ParticleSys2(0,omega,m,k,0,timeStep,i,l0);
            particles.put(i,p);
        }
        verletSystems.put(1,null);
        verletSystems.put(n,null);
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
            while(totalTime < tf){
                for(int i = 1;i<=n;i++){
                     if(i == 1 || i == n){
                         ParticleSys2 aux = particles.get(i);
                         float aux_pos = (float) (Math.cos(omega*totalTime) * a);
                         aux.setPosition(aux_pos);
                     }else{
                         verletSystems.get(i).runAlgorithm();
                     }
                }

                totalTime += timeStep;
                accumTime += timeStep;
                if(accumTime >= printStep){
                    writer.write(totalTime+"\n");
                    for(int i = 1;i<=n;i++){
                        ParticleSys2 aux = particles.get(i);
                        if(i == n){
                            writer.write(aux.getPosition()+"\n");
                        }else {writer.write(aux.getPosition()+" ");}
                    }
                    accumTime = 0;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();

        }

    }
}