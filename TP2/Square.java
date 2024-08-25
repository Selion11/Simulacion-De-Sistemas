package TP2;

import java.util.ArrayList;
import java.util.HashMap;

// Enum para saber hacia donde quiero mover mi cuadrado virtual
enum squareType{
    TOP_BORDER,
    RIGHT_BORDER,
    BOTTOM_BORDER,
    TOP_RIGHT,
    BOTTOM_RIGHT
}

public class Square {
    
    private float x_stop,y_stop,x_start,y_start;
    protected int id, i,j,m,l;
    private ArrayList<Square> invertedL = new ArrayList<>();
    private ArrayList<ParticleOffLattice> particles = new ArrayList<>();
    private Integer virtualX = 0;
    private Integer virtualY = 0;



    public Square(int id, float x_start, float x_stop, float y_start, float y_stop, int i, int j, int m, int l){
        this.id = id;
        this.x_stop = x_stop;
        this.y_stop = y_stop;
        this.x_start = x_start;
        this.y_start = y_start;
        this.i = i;
        this.j = j;
        this.m = m;
        this.l = l;
    }

    public boolean checkParticle(ParticleOffLattice p){
        return (p.getX() <= x_stop && p.getY() <= y_stop && p.getX() >= x_start && p.getY() >= y_start );
    }

    public void add_particle(ParticleOffLattice p) {
        particles.add(p);
    }

    public ArrayList<ParticleOffLattice> getParticles() {
        return this.particles;
    }

    public void setVirtualParticles(ArrayList<ParticleOffLattice> particles,squareType type){
        //                    this.particles.add(new Particle(particle.getId(),particle.getX(), particle.getY()+l,particle.getR(),particle.getRC(),0, l));
        this.particles.addAll(particles);
        switch (type) {
            case TOP_BORDER:
                this.virtualY = l;

                break;

            case RIGHT_BORDER:
                this.virtualX = l;
                break;

            case BOTTOM_BORDER:
                this.virtualY = -l;
                break;

            case TOP_RIGHT:
                this.virtualX = l;
                this.virtualY = l;
                break;

            case BOTTOM_RIGHT:
                this.virtualX = l;
                this.virtualY = -l;
                break;

            default:
                break;
        }

    }

    public void get_invertedL(Square[][] squares, boolean with_reflection) {
        invertedL.add(this); //voy a querer chequear vecinas dentro del mismo cuadrado
        Square auxSquare;
        if (i == 0) { //borde superior
            if(with_reflection) {
                // cuadrados virtuales
                auxSquare = new Square(squares[m-1][j].getId(), squares[m-1][j].getX_start(),squares[m-1][j].getX_stop(),
                squares[m-1][j].getY_start()+l,squares[m-1][j].getY_stop()+l,
                i,j,m,l);
                auxSquare.setVirtualParticles(squares[m-1][j].getParticles(),squareType.TOP_BORDER);
                invertedL.add(auxSquare);
            }
            if (j == m-1) {
                if (with_reflection) {
                    // cuadrados virtuales
                    auxSquare = new Square(squares[m - 1][0].getId(), squares[m - 1][0].getX_start() + l, squares[m - 1][0].getX_stop() + l,
                            squares[m - 1][0].getY_start() + l, squares[m - 1][0].getY_stop() + l,
                            i, j, m, l);
                    auxSquare.setVirtualParticles(squares[m - 1][0].getParticles(), squareType.TOP_RIGHT);
                    invertedL.add(auxSquare);

                    auxSquare = new Square(squares[0][0].getId(), squares[0][0].getX_start() + l, squares[0][0].getX_stop() + l,
                            squares[0][0].getY_start(), squares[0][0].getY_stop(),
                            i, j, m, l);
                    auxSquare.setVirtualParticles(squares[0][0].getParticles(), squareType.RIGHT_BORDER);
                    invertedL.add(auxSquare);

                    auxSquare = new Square(squares[1][0].getId(), squares[1][0].getX_start() + l, squares[1][0].getX_stop() + l,
                            squares[1][0].getY_start(), squares[1][0].getY_stop(),
                            i, j, m, l);
                    auxSquare.setVirtualParticles(squares[1][0].getParticles(), squareType.RIGHT_BORDER);
                    invertedL.add(auxSquare);
                }

            } else{
                if(with_reflection) {
                    // cuadrados virtuales
                    auxSquare = new Square(squares[m-1][j+1].getId(), squares[m-1][j+1].getX_start(),squares[m-1][j+1].getX_stop(),
                    squares[m-1][j+1].getY_start()+l,squares[m-1][j+1].getY_stop()+l,
                    i,j,m,l);
                    auxSquare.setVirtualParticles(squares[m-1][j+1].getParticles(),squareType.RIGHT_BORDER);
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
                auxSquare = new Square(squares[i][0].getId(), squares[i][0].getX_start()+l,squares[i][0].getX_stop()+l,
                squares[i][0].getY_start(),squares[i][0].getY_stop(),
                i,j,m,l);
                auxSquare.setVirtualParticles(squares[i][0].getParticles(),squareType.RIGHT_BORDER);
                invertedL.add(auxSquare);

                auxSquare = new Square(squares[i-1][0].getId(), squares[i-1][0].getX_start()+l,squares[i-1][0].getX_stop()+l,
                squares[i-1][0].getY_start(),squares[i-1][0].getY_stop(),
                i,j,m,l);
                auxSquare.setVirtualParticles(squares[i-1][0].getParticles(),squareType.RIGHT_BORDER);
                invertedL.add(auxSquare);

                if(i != m-1) {
                    auxSquare = new Square(squares[i+1][0].getId(), squares[i+1][0].getX_start()+l,squares[i+1][0].getX_stop()+l,
                    squares[i+1][0].getY_start(),squares[i+1][0].getY_stop(),
                    i,j,m,l);
                    auxSquare.setVirtualParticles(squares[i+1][0].getParticles(),squareType.RIGHT_BORDER);
                    invertedL.add(auxSquare);
                } else {
                    auxSquare = new Square(squares[0][0].getId(), squares[0][0].getX_start()+l,squares[0][0].getX_stop()+l,
                    squares[0][0].getY_start()-l,squares[0][0].getY_stop()-l,
                    i,j,m,l);
                    auxSquare.setVirtualParticles(squares[0][0].getParticles(),squareType.BOTTOM_RIGHT);
                    invertedL.add(auxSquare);
                }
            }
            return;
        }

        //celdas restantes
        invertedL.add(squares[i-1][j]);
        invertedL.add(squares[i][j+1]);
        invertedL.add(squares[i-1][j+1]);
        if(i == m-1 ) {
            if(with_reflection) {
                // cuadrados virtuales
                auxSquare = new Square(squares[0][j + 1].getId(), squares[0][j + 1].getX_start(), squares[0][j + 1].getX_stop(),
                        squares[0][j + 1].getY_start() - l, squares[0][j + 1].getY_stop() - l,
                        i, j, m, l);
                auxSquare.setVirtualParticles(squares[0][j + 1].getParticles(), squareType.BOTTOM_BORDER);
                invertedL.add(auxSquare);
            }
        }else{
            invertedL.add(squares[i+1][j+1]);
        }
    }

    public ArrayList<Square> getInvertedL() {
        return invertedL;
    }

    public int getId() {
        return id;
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

    public Integer getVirtualX() {
        return virtualX;
    }

    public Integer getVirtualY() {
        return virtualY;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (ParticleOffLattice p:
             particles) {
            stringBuilder.append(p.getId() + "-");
        }
        return stringBuilder.toString();
    }

    public float getL() {
        return l;
    }
}