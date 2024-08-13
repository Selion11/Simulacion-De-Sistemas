package TP1;

import java.util.ArrayList;

// Enum para saber hacia donde quiero mover mi cuadrado virtual
enum squareType{
    TOP_BORDER,
    RIGHT_BORDER,
    BOTTOM_BORDER,
    TOP_RIGHT,
    BOTTOM_RIGHT
}

public class square {
    
    private float x_stop,y_stop,x_start,y_start;
    protected int i,j,m,l;
    private ArrayList<square> invertedL = new ArrayList<>();
    private ArrayList<particle> particles = new ArrayList<>();

    public square(float x_start, float x_stop, float y_start, float y_stop, int i, int j, int m, int l){
        this.x_stop = x_stop;
        this.y_stop = y_stop;
        this.x_start = x_start;
        this.y_start = y_start;
        this.i = i;
        this.j = j;
        this.m = m;
        this.l = l;
    }

    public boolean checkParticle(particle p){
        return (p.getX() <= x_stop && p.getY() <= y_stop && p.getX() >= x_start && p.getY() >= y_start );
    }

    public void add_particle(particle p) {
        particles.add(p);
    }

    public ArrayList<particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<particle> particles,squareType type){
        switch (type) {
            case TOP_BORDER:
                for (particle particle : particles) {
                    this.particles.add(new particle(particle.getX(), particle.getY()+l,particle.getR(),particle.getRC()));
                }
                break;

            case RIGHT_BORDER:
                for (particle particle : particles) {
                    this.particles.add(new particle(particle.getX()+l, particle.getY(),particle.getR(),particle.getRC()));
                }
                break;

            case BOTTOM_BORDER:
                for (particle particle : particles) {
                    this.particles.add(new particle(particle.getX(), particle.getY()-l,particle.getR(),particle.getRC()));
                }
                break;

            case TOP_RIGHT:
                for (particle particle : particles) {
                    this.particles.add(new particle(particle.getX()+l, particle.getY()+l,particle.getR(),particle.getRC()));
                }
                break;

            case BOTTOM_RIGHT:
                for (particle particle : particles) {
                    this.particles.add(new particle(particle.getX() + l, particle.getY() - l,particle.getR(),particle.getRC()));
                }
                break;

            default:
                break;
        }

    }

    public void get_invertedL(square[][] squares, boolean with_reflection) {
        invertedL.add(this); //voy a querer chequear vecinas dentro del mismo cuadrado
        square auxSquare;
        if (i == 0) { //borde superior
            if(with_reflection) {
                // cuadrados virtuales
                auxSquare = new square(squares[m-1][j].getX_start(),squares[m-1][j].getX_stop(),
                squares[m-1][j].getY_start()+l,squares[m-1][j].getY_stop()+l,
                i,j,m,l);
                auxSquare.setParticles(squares[m-1][j].getParticles(),squareType.TOP_BORDER);
                invertedL.add(auxSquare);
            }
            if (j == m-1 && with_reflection) {
                // cuadrados virtuales
                auxSquare = new square(squares[m-1][0].getX_start()+l,squares[m-1][0].getX_stop()+l,
                squares[m-1][0].getY_start()+l,squares[m-1][0].getY_stop()+l,
                i,j,m,l);
                auxSquare.setParticles(squares[m-1][0].getParticles(),squareType.TOP_RIGHT);
                invertedL.add(auxSquare);

                auxSquare = new square(squares[1][0].getX_start()+l,squares[1][0].getX_stop()+l,
                squares[1][0].getY_start(),squares[1][0].getY_stop(),
                i,j,m,l);
                auxSquare.setParticles(squares[0][0].getParticles(),squareType.RIGHT_BORDER);
                invertedL.add(auxSquare);

                auxSquare = new square(squares[1][0].getX_start()+l,squares[1][0].getX_stop()+l,
                squares[1][0].getY_start(),squares[1][0].getY_stop(),
                i,j,m,l);
                auxSquare.setParticles(squares[1][0].getParticles(),squareType.RIGHT_BORDER);
                invertedL.add(auxSquare);              

            } else{
                if(with_reflection) {
                    // cuadrados virtuales
                    auxSquare = new square(squares[m-1][j+1].getX_start(),squares[m-1][j+1].getX_stop(),
                    squares[m-1][j+1].getY_start()+l,squares[m-1][j+1].getY_stop()+l,
                    i,j,m,l);
                    auxSquare.setParticles(squares[m-1][j+1].getParticles(),squareType.RIGHT_BORDER);
                    invertedL.add(auxSquare);
                }
                invertedL.add(squares[i][j+1]);
                invertedL.add(squares[i+1][j+1]);
            }
            return;
        }
        if(j == m-1) { //borde derecho (sin esquina derecha superior)
            invertedL.add(squares[i-1][m-1]);
            if(with_reflection) {
                // cuadrados virtuales
                auxSquare = new square(squares[i][0].getX_start()+l,squares[i][0].getX_stop()+l,
                squares[i][0].getY_start(),squares[i][0].getY_stop(),
                i,j,m,l);
                auxSquare.setParticles(squares[i][0].getParticles(),squareType.RIGHT_BORDER);
                invertedL.add(auxSquare);

                auxSquare = new square(squares[i-1][0].getX_start()+l,squares[i-1][0].getX_stop()+l,
                squares[i-1][0].getY_start(),squares[i-1][0].getY_stop(),
                i,j,m,l);
                auxSquare.setParticles(squares[i-1][0].getParticles(),squareType.RIGHT_BORDER);
                invertedL.add(auxSquare);

                if(i != m-1) {
                    auxSquare = new square(squares[i+1][0].getX_start()+l,squares[i+1][0].getX_stop()+l,
                    squares[i+1][0].getY_start(),squares[i+1][0].getY_stop(),
                    i,j,m,l);
                    auxSquare.setParticles(squares[i+1][0].getParticles(),squareType.RIGHT_BORDER);
                    invertedL.add(auxSquare);
                } else {
                    auxSquare = new square(squares[0][0].getX_start()+l,squares[0][0].getX_stop()+l,
                    squares[0][0].getY_start()-l,squares[0][0].getY_stop()-l,
                    i,j,m,l);
                    auxSquare.setParticles(squares[0][0].getParticles(),squareType.BOTTOM_RIGHT);
                    invertedL.add(auxSquare);
                }
            }
            return;
        }

        //celdas restantes
        invertedL.add(squares[i-1][j]);
        invertedL.add(squares[i][j+1]);
        invertedL.add(squares[i-1][j+1]);
        if(i == m-1 && with_reflection) {
            // cuadrados virtuales
            auxSquare = new square(squares[0][j+1].getX_start(),squares[0][j+1].getX_stop(),
            squares[0][j+1].getY_start()-l,squares[0][j+1].getY_stop()-l,
            i,j,m,l);
            auxSquare.setParticles(squares[0][j+1].getParticles(),squareType.BOTTOM_BORDER);
            invertedL.add(auxSquare);
        } 
    }

    public ArrayList<square> getInvertedL() {
        return invertedL;
    }

    public float getX_stop() {
        return x_stop;
    }

    public float getY_stop() {
        return y_stop;
    }


    public float getX_start() {
        return x_start;
    }

    public float getY_start() {
        return y_start;
    }

}