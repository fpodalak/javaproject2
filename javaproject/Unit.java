package javaproject;

abstract public class Unit {
    protected int x;
    protected int y;
    protected Field field;

    public Unit(int x, int y, Field field) {
        this.x = x;
        this.y = y;
        this.field = field;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    abstract public void run();

    abstract protected void move();

    public void p(){
        System.out.println("Unit");
        System.out.println("x: " + x);
        System.out.println("y: " + y);
    }

    public boolean isDog(){
        return false;
    }

    public boolean isFarmer(){
        return false;
    }

    public boolean isRabbit(){
        return false;
    }
}
