package TP4.algorithms;

import TP4.ParticleSys2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MultiParticleSystem {
    private final double k;
    private final double gamma;
    private final double omega;
    private Map<Integer, Double> last_positions;
    private final double dt;
    private final double time_limit;
    private double time;
    protected List<ParticleSys2> particles;
    private final Function<Double, Double> positionSolution;
    private final double amplitud;
    private final int particle_ammount;

    public MultiParticleSystem(double omega,double amp,double k, double tf, double dt,double gamma,
                               List<ParticleSys2> particles, Function<Double, Double> positionSolution,int max){
        this.k = k;
        this.gamma = gamma;
        this.amplitud = amp;
        this.omega = omega;
        this.time_limit = tf;
        this.dt = dt;
        this.time = 0;
        this.particles = particles;
        this.positionSolution = positionSolution;
        last_positions = new HashMap<>();
        this.particle_ammount = max;
        for(ParticleSys2 particle : particles){
            last_positions.put(particle.getId(),0.0);
        }
    }

    private double systemForce(double prev, double curr, double next){
        //System.out.println("K: "+k+" CURR_POS: "+curr.getPosition()+" NEXT_POS: "+next.getPosition()+" DT: "+dt+"\n");
        double a = -k *(curr-prev) - (k *(curr- next));
        if (Double.isInfinite(a) || Double.isNaN(a)) {
            return 0;
        }
        return a;
    }

    private double calculateAcceleration(double position, double velocity, double mass) {
        return (-k * position - gamma * velocity) / mass;
    }

    public void advanceSystem(){
        List<Double> new_positions = new ArrayList<>();
        List<Double> new_velocities = new ArrayList<>();
        for(ParticleSys2 particle : particles){
            int id = particle.getId();
            if(id != particle_ammount-1){
                double lastPosition = last_positions.get(id);
                double a;
                if(id ==0){
                    a = systemForce(0, particles.get(id).getPosition(), particles.get(id+1).getPosition());
                }else{
                    a = systemForce(particles.get(id-1).getPrevPosition(), particles.get(id).getPosition(), particles.get(id+1).getPosition());
                }
                double new_r = (2.0 * particle.getPosition()) - lastPosition + Math.pow(dt,2) * a;
                double new_v = (new_r - lastPosition)/(2.0 * dt);
                last_positions.put(id,particle.getPosition());
                new_positions.add(new_r);
                new_velocities.add(new_v);
            }
        }
        for(ParticleSys2 particle : particles){
            int id = particle.getId();
            if(id != particle_ammount-1){
                particle.setPosition(amplitud * Math.sin(omega * time));
            }else{
                particles.get(id-1).setPosition(new_positions.get(id-1));
                particles.get(id-1).setV(new_velocities.get(id-1));
            }
        }
        time += dt;
    }

    public double getParticlePosition(int i){
        return particles.get(i).getPosition();
    }
}
