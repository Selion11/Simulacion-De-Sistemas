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
        double k, v0, r0, m, gamma, tf, timeStep;
        double totalTime = 0;

        Properties properties = new Properties();

        try {
            FileInputStream config = new FileInputStream("TP4/configs/conf.config");
            properties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        k = Double.parseDouble(properties.getProperty("k"));
        r0 = Double.parseDouble(properties.getProperty("r0"));
        m = Double.parseDouble(properties.getProperty("m"));
        gamma = Double.parseDouble(properties.getProperty("gamma"));
        tf = Double.parseDouble(properties.getProperty("tf"));
        timeStep = Double.parseDouble(properties.getProperty("timeStep"));
        v0 = (-1 * gamma) / (2 * m);


        for (int i = 0; i < 5 ; i++) {
            Particle beemanParticle = new Particle(r0, v0, m, k, gamma, timeStep);
            Particle verletParticle = new Particle(r0, v0, m, k, gamma, timeStep);
            Particle gearParticle = new Particle(r0, v0, m, k, gamma, timeStep);

            Beeman beeman = new Beeman(beemanParticle);
            Verlet verlet = new Verlet(verletParticle);
            GearPredictorCorrector gear = new GearPredictorCorrector(gearParticle);

            File output = new File("TP4/outputs/output_" + i + ".csv");

            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
                writer.write("beeman;verlet;gear;analytic;time\n");
                while (totalTime < tf) {
                    // Iteracion de algoritmos
                    beeman.runAlgorithm();
                    verlet.runAlgorithm();
                    gear.runAlgorithm();

                    //Print in file
                    writer.write(String.valueOf(beemanParticle.getPosition()));
                    writer.write(';');
                    writer.write(String.valueOf(verletParticle.getPosition()));
                    writer.write(';');
                    writer.write(String.valueOf(gearParticle.getPosition()));
                    writer.write(';');
                    writer.write(String.valueOf(analyticalSolution(m, k, gamma, totalTime)));
                    writer.write(';');
                    writer.write(String.valueOf(totalTime));
                    writer.newLine();

                    totalTime += timeStep;
                }

            } catch (IOException e) {
                e.printStackTrace();

            }

            timeStep /= 10;
            totalTime = 0;
        }
    }

    public static double analyticalSolution(double m, double k, double gamma, double t) {
        return Math.exp(-t*gamma/(2*m)) * Math.cos(Math.pow(k/m - (Math.pow(gamma,2)/(4*Math.pow(m,2))), 0.5) * t);
    }
}
