package TP4.algorithms;
import TP4.ParticleSys2;

public class Sys2Verlet{
    double m,k,dt;

    public double firstPartcileInForce(ParticleSys2 initial,ParticleSys2 second){
        return -k *(initial.getPosition()-second.getPosition()) - k *(initial.getPosition());
    }

    public double systemForce(ParticleSys2 prev, ParticleSys2 curr, ParticleSys2 next){
        //System.out.println("K: "+k+" CURR_POS: "+curr.getPosition()+" NEXT_POS: "+next.getPosition()+" DT: "+dt+"\n");
        return -k *(curr.getPosition()-prev.getPosition()) - (k *(curr.getPosition()- next.getPosition()));
    }

    public void lastParticleSys2(ParticleSys2 last,double timeElapsed,double omega,double force){
        last.setPosition(force * Math.sin(omega * timeElapsed));
    }

    public Sys2Verlet(double m,double k,double dt) {
        this.m = m;
        this.k = k;
        this.dt = dt;
    }

    public void updatePartcile(ParticleSys2 p, double force){
        double newPos = 2.0 * p.getPosition() - p.getPrevPosition() + ( dt * dt /m)* force;
        double newV = (newPos - p.getPosition())/(2.0*dt);
        p.setPrevPosition(p.getPosition());
        p.setPosition(newPos);
        p.setV(newV);
    }

}
