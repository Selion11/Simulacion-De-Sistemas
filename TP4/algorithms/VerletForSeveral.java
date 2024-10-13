package TP4.algorithms;

import TP4.Particle;

import java.util.List;

public class VerletForSeveral implements Algorithm{
    private List<Particle> particles;
    private double dt;
    private double amplitud;
    private double omega;
    private double time;

    public VerletForSeveral(List<Particle> particles,double amplitud) {
        //INICIALIZO EL SISTEMA
        this.particles = particles;
        this.dt = particles.get(0).getTimeStep();
        this.amplitud = amplitud;
        this.omega = particles.get(0).getOmega();
        time = 0;
/*      ESTO ESTA EN EL ORIGINAL DEL SISTEMA 1
        this.a no va cambiando por particula? Lo que hago en run algorithm no reemplaza esta linea??
        this.a = OscillatorForce(particle.getPosition(), particle.getV(), m, k, gamma);

        this.particle.setPrevPosition no esta hecho ya cuando inicializo la paritcula con prev osition en 0??

        this.particle.setPrevPosition( EulerPosition(particle.getPosition(), particle.getV(), a, -dt));
*/
    }


    @Override
    public void runAlgorithm() {
        double a;
        for(Particle p : particles) {
            //CADA FOR INDIVIDUAL DEBERIA FUNCIONAR COMO UN VERLET DE SYSTEMA 1
            int pid = p.getId();
            if(pid==99){
                // SI ES LA ULTIMA PARTICULA UTILIZO LA CUENTA REQUERIDA EN EL PDF
                p.setPosition(lastParticle(amplitud,omega,time));
            }else{
                if(pid == 0) {
                    //SI ES LA PARTICULA CONTRA LA PARED NO TIENE PARTICULA PREVIA
                    a = calculateForce(p.getPosition(), particles.get(pid + 1).getPosition(), 0.0, p.getK());
                }else{
                    a = calculateForce(p.getPosition(),particles.get(pid+1).getPosition(),particles.get(pid+1).getPosition(),p.getK());
                }
                double rAfter = (2.0*p.getPosition()) - p.getPrevPosition() + Math.pow(dt,2) *a;
                p.setV ((rAfter - p.getPrevPosition())/(2.0 * dt));
                p.setPrevPosition(p.getPosition());
                p.setPosition(rAfter);
            }
        }
        time+= dt;
    }

    public double timePast() {return time;}

}
