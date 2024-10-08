package TP4;

import TP4.algorithms.Beeman;
import TP4.algorithms.GearPredictorCorrector;
import TP4.algorithms.Verlet;

import java.io.*;
import java.util.Properties;

public class system1 {
    /*Parametros:
     * Masa = 70kg
     * k = 10^4 N/m
     * gamma = 100 kg/s
     * tfinal = 5 segundos
     *
     * Condiciones iniciales:
     * r(t = 0) = 1 m
     * v(t = 0) = - A * gamma/2m
     *
     * r = A exp(-(gamma/2m)*t) * cos((k/m - gamma^2/4m^2)^0.5 *t)*/
    public static void main(String[] args) throws IOException {
        float k,v0,r0,m,gamma,tf,timeStep,printStep;
        float accumTime = 0;
        float totalTime = 0;

        Properties properties = new Properties();

        try{
            FileInputStream config = new FileInputStream("TP4/configs/conf.config");
            properties.load(config);
        }catch(IOException e){
            e.printStackTrace();
        }

        k = Float.parseFloat(properties.getProperty("k"));
        r0 = Float.parseFloat(properties.getProperty("r0"));
        m = Float.parseFloat(properties.getProperty("m"));
        gamma = Float.parseFloat(properties.getProperty("gamma"));
        tf = Float.parseFloat(properties.getProperty("tf"));
        timeStep = Float.parseFloat(properties.getProperty("timeStep"));
        printStep = Float.parseFloat(properties.getProperty("printStep"));
        v0 = (-1*gamma)/2*m;

        Particle beemanParticle = new Particle(r0,v0,m,k,gamma,timeStep);
        Particle verletParticle = new Particle(r0,v0,m,k,gamma,timeStep);
        Particle gearParticle = new Particle(r0,v0,m,k,gamma,timeStep);

        Beeman beeman = new Beeman(beemanParticle);
        Verlet verlet = new Verlet(verletParticle);
        GearPredictorCorrector gear = new GearPredictorCorrector(gearParticle);

        Oscilador oscilador = new Oscilador();

        File output = new File("TP4/outputs/output_0.01_0.02.txt");

        try{
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))){
            while(totalTime < tf){
                // Iteracion de algoritmos
                beeman.runAlgorithm();
                verlet.runAlgorithm();
                gear.runAlgorithm();

                accumTime += timeStep;
                totalTime += timeStep;

                if(accumTime >= printStep){

                    //Print in file
                    writer.write(String.valueOf(beemanParticle.getPosition()));
                    writer.write(':');
                    writer.write(String.valueOf(verletParticle.getPosition()));
                    writer.write(':');
                    writer.write(String.valueOf(gearParticle.getPosition()));
                    writer.write(':');
                    writer.write(String.valueOf(oscilador.analyticalSolution(totalTime,r0,v0,k,gamma)));
                    writer.write(':');
                    writer.newLine();

                    accumTime = 0;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();

        }

    }
}
