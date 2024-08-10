package TP1;

public class square{
    private float x_stop,y_stop,x_start,y_start;
    private int id;
    private int array_size = 5;
    particle[] particles = new particle[array_size];
    int particle_index = 0;

    public square(float x_start, float x_stop, float y_start, float y_stop, int id){
        this.x_stop = x_stop;
        this.y_stop = y_stop;
        this.x_start = x_start;
        this.y_start = y_start;
        this.id = id;
    }

    public int add_particle(particle p){
        if(!checkParticle(p)){
            return 0;
        }
        int retVal = 0;
        while(retVal != 1){
            if(particle_index < array_size){
                particles[particle_index] = p;
                particle_index++;
                retVal = 1;
            }else{
                particles = expand_array();
            }
        }
        return retVal;
    }

    private boolean checkParticle(particle p){
        return (p.getX() <= x_stop && p.getY() <= y_stop && p.getX() >= x_start && p.getY() >= y_start );
    }

    private particle[] expand_array(){
        array_size += 5;
        particle[] aux = new particle[array_size];
        System.arraycopy(particles, 0, aux, 0, particles.length);
        return aux;
    }
    public void square_check(){
        for(particle p: particles){
            for(particle q: particles){
                if(p.getId() != q.getId()){

                }
            }
        }
    }
    public void setX_stop(float x_stop) {
        this.x_stop = x_stop;
    }

    public void setX_start(float x_start) {
        this.x_start = x_start;
    }

    public void setY_start(float y_start) {
        this.y_start = y_start;
    }

    public void setY_stop(float y_stop) {
        this.y_stop = y_stop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX_stop() {
        return x_stop;
    }

    public float getY_stop() {
        return y_stop;
    }

    public int getId() {
        return id;
    }

    public float getX_start() {
        return x_start;
    }

    public float getY_start() {
        return y_start;
    }
}
