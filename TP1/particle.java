package TP1;

public class particle {
    private float x,y,r,rc;
    int id;
    int idx_vecinas = 0;
    int[] vecinas = null;

    public particle(){
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRc(float rc) {
        this.rc = rc;
    }

    public int checkVecina(particle p){
        return 1;
    }
    public void add_vecina(int id){
        int new_idx = idx_vecinas + 1;
        int[] aux = new int[new_idx];
        System.arraycopy(vecinas, 0, aux, 0, vecinas.length);
        aux[new_idx] = id;
        idx_vecinas = new_idx;
        vecinas = aux;
    }
    public float getRc() {
        return rc;
    }

    public int getId() {
        return id;
    }

    public float getX(){
        return x;
    }

    public float getR() {
        return r;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("TP1.particle " + id +"{ "+ "x=" + x + ", y=" + y + ", r=" + r + '}');
    }
}
