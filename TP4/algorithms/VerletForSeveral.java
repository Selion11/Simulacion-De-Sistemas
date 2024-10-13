package TP4.algorithms;

import TP4.Particle;

import java.util.List;

public class VerletForSeveral implements Algorithm{
    private List<Particle> particles;
    private double dt;
    private double amplitud;
    private double omega;

    public VerletForSeveral(List<Particle> particles,double amplitud) {
        //INICIALIZO EL SISTEMA
        this.particles = particles;
        this.dt = particles.get(0).getTimeStep();
        this.amplitud = amplitud;
        this.omega = particles.get(0).getOmega();
/*      ESTO ESTA EN EL ORIGINAL DEL SISTEMA 1
        this.a no va cambiando por particula? Lo que hago en run algorithm no reemplaza esta linea??
        this.a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);

        this.particle.setPrevPosition no esta hecho ya cuando inicializo la paritcula con prev osition en 0??*/
        double a;

        for(Particle p : particles){
            //System.out.println("ID: "+p.getId()+" V: "+p.getV());
            a =  OscillatorForce(p.getPosition(), p.getV(), p.getM(), p.getK(), 100.0);
            p.setPrevPosition( EulerPosition(p.getPosition(), p.getV(), a, -dt));
            System.out.println("SET PREV POS: "+p.getPrevPosition());
            p.setA(a);
        }


    }


    @Override
    public void runAlgorithm() {
        return;
    }

    @Override
    public void runAlgorithm2(double time) {
        for(Particle p : particles) {
            //CADA FOR INDIVIDUAL DEBERIA FUNCIONAR COMO UN VERLET DE SYSTEMA 1
            int pid = p.getId();
            if(pid==99){
                p.setA(0.01);
                // SI ES LA ULTIMA PARTICULA UTILIZO LA CUENTA REQUERIDA EN EL PDF
                p.setPosition(lastParticle(p.getA(),omega,time));
            }else{
                if(pid == 0) {
                    //SI ES LA PARTICULA CONTRA LA PARED NO TIENE PARTICULA PREVIA F = m*a -> DIVIDO LA FUERZA POR LA MASA PARA OBTENER LA CELERACION
                    amplitud = calculateForce(p.getPosition(), particles.get(pid + 1).getPosition(), 0.0, p.getK(),p.getM());
                    p.setA(amplitud);
                }else{
                    //SINO USO EL CALCULO DE LA FUERZA QUE DICE EN EL PDF
                    amplitud = calculateForce(p.getPosition(),particles.get(pid+1).getPosition(),particles.get(pid-1).getPrevPosition(),p.getK(),p.getM());
                    //99 98 97
                    //n  n  c(T)
                    p.setA(amplitud);
                }
                double rAfter = (2.0*p.getPosition()) - p.getPrevPosition() + (Math.pow(dt,2)) *p.getA();
                p.setV ((rAfter - p.getPrevPosition())/(2.0 * dt));
                p.setPosition(rAfter);
            }
        }
    }


}
