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

        Map<Double,List<Double>> values = new HashMap<>();

        List<Double> omegas1 = new ArrayList<>();
        omegas1.add(8.0);
        omegas1.add(8.5);
        omegas1.add(9.0);
        omegas1.add(9.5);
        omegas1.add(10.0);
        omegas1.add(10.5);
        omegas1.add(11.0);
        omegas1.add(11.5);
        omegas1.add(12.0);

//        for(Double k: ks) {
//            for (Double omega : omegas) {
                timeStep = 1/(100.0 * 10);
                runSystem(100,mass,10,timeStep,n,tf,amp);
//            }
//        }
        List<Double> omegas2 = new ArrayList<>();
        omegas2.add(28.0);
        omegas2.add(28.5);
        omegas2.add(29.0);
        omegas2.add(29.5);
        omegas2.add(30.0);
        omegas2.add(30.5);
        omegas2.add(31.0);
        omegas2.add(31.5);
        omegas2.add(32.0);


        List<Double> omegas3 = new ArrayList<>();
        omegas3.add(42.0);
        omegas3.add(42.5);
        omegas3.add(43.0);
        omegas3.add(43.5);
        omegas3.add(44.0);
        omegas3.add(44.5);
        omegas3.add(45.0);
        omegas3.add(45.5);
        omegas3.add(46.0);

        List<Double> omegas4 = new ArrayList<>();
        omegas4.add(68.0);
        omegas4.add(68.5);
        omegas4.add(69.0);
        omegas4.add(69.5);
        omegas4.add(70.0);
        omegas4.add(70.5);
        omegas4.add(71.0);
        omegas4.add(71.5);
        omegas4.add(72.0);

        List<Double> omegas5 = new ArrayList<>();
        omegas5.add(98.0);
        omegas5.add(98.5);
        omegas5.add(99.0);
        omegas5.add(99.5);
        omegas5.add(100.0);
        omegas5.add(100.5);
        omegas5.add(101.0);
        omegas5.add(101.5);
        omegas5.add(102.0);



        ks.add(k1);
        ks.add(k2);
        ks.add(k3);
        ks.add(k4);
        ks.add(k5);

        values.put(k1,omegas1);
        values.put(k2,omegas2);
        values.put(k3,omegas3);
        values.put(k4,omegas4);
        values.put(k5,omegas5);

//        for(Double k: ks) {
//            for (Double omega : values.get(k)) {
//                timeStep = 1/(100 * omega);
//                runSystem(k,mass,omega,timeStep,n,tf,amp);
//            }
//        }


    }


    private static void runSystem(double k,double mass,double omega,double timeStep,int particleCant,double totalTime,double amp){
        List<Particle> particles = new ArrayList<>();
//        File output = new File("TP4/outputs/System2/"+k+"_"+omega+".csv");
        File positions = new File("TP4/positions" + omega + "_" + k + ".csv");

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
                    if(p.getId()== 50) {
                        writerPositions.write((p.getId()) * 0.001 + ";" + p.getPosition() + ";" + timeElapsed + "\n");
                    }
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
