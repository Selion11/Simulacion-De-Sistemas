package TP4;

import TP4.algorithms.Beeman;
import TP4.algorithms.Verlet;

import java.io.FileInputStream;
import java.io.IOException;
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

        while(totalTime < tf){
            beeman.runAlgorithm();
            verlet.runAlgorithm();

            accumTime += timeStep;
            totalTime += timeStep;

            if(accumTime >= printStep){
                //Print in file
                System.out.printf("Solución Analítica: %.5f%n \n",verlet.analyticalSolution(accumTime));
                accumTime = 0;
            }
        }

    }
}
