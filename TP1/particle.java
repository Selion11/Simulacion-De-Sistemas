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

    public void setRc(float rc) { this.rc = rc; }

    public boolean checkVecina(particle p){
        double x_diff = Math.pow(this.x - p.getX(),2);
        double y_diff = Math.pow(this.y - p.getY(),2);
        double ans = Math.sqrt(x_diff+y_diff) - (r+p.getR()) - rc;
        if(ans <= 0){
            add_vecina(p.id);
            p.add_vecina(this.id);
            return true;
        }
        return false;
    }

    public void add_vecina(int id){
        for(int vecina: vecinas){
            if(vecina == id){
                return;
            }
        }
        int new_idx = idx_vecinas + 1;
        int[] aux = new int[new_idx ];
        System.arraycopy(vecinas, 0, aux, 0, vecinas.length);
        aux[new_idx] = id;
        idx_vecinas = new_idx;
        vecinas = aux;
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
