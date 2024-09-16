package TP3;


public class Wall {
    private int id;
    private WallType type;
    private float l;

    public Wall(int id,WallType type,float l){
        this.id = id;
        this.type = type;
        this.l = l;
    }

    public WallType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
