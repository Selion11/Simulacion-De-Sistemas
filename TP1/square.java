package TP1;

import java.util.ArrayList;

public class square {
    private float x_stop,y_stop,x_start,y_start;
    private int i,j,m;
    private ArrayList<particle> particles = new ArrayList<particle>();

    public square(float x_start, float x_stop, float y_start, float y_stop, int i, int j, int m){
        this.x_stop = x_stop;
        this.y_stop = y_stop;
        this.x_start = x_start;
        this.y_start = y_start;
        this.i = i;
        this.j = j;
        this.m = m;
    }

    public void add_particle(particle p){
        particles.add(p);
    }

    public boolean checkParticle(particle p){
        return (p.getX() <= x_stop && p.getY() <= y_stop && p.getX() >= x_start && p.getY() >= y_start );
    }

    public ArrayList<square> get_invertedL(square[][] squares, boolean with_reflection) {
        ArrayList<square> toReturn = new ArrayList<>();
        if (i == 0) {
            toReturn.add(squares[m-1][j]);
            if (j == m-1) {
                toReturn.add(squares[m-1][0]);
                toReturn.add(squares[0][0]);
                toReturn.add(squares[0][m-1]);
            }else{
                toReturn.add(squares[m-1][j+1]);
                toReturn.add(squares[i][j+1]);
                toReturn.add(squares[i+1][j+1]);
            }
            
        }

        if(with_reflection) {
            return get_invertedL_with_reflection();
        }
        return get_invertedL_without_reflection();
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