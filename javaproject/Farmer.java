package javaproject;

public class Farmer extends Unit implements Runnable{
    private int moveTime;
    private int plantTime;
    private int fixTime;
    public Farmer(int x, int y, int moveTime, int plantTime, int fixTime, Field field) {
        super(x, y, field);
        this.x = x;
        this.y = y;
        this.moveTime = moveTime;
        this.plantTime = plantTime;
        this.fixTime = fixTime;
        this.field = field;
        this.field.getCell(x, y).setFarmer();
        Main.units.add(this);
    }
    public void run() {
        while (true) {
            try {
                while (Main.isprinting) {
                    Thread.sleep(1);
                }
                if(field.getCell(x, y).isDamaged()){
                    Thread.sleep(fixTime);
                    fix();
                }else if(!field.getCell(x, y).hasCarrot()){
                    Thread.sleep(plantTime);
                    plantCarrot();
                }else{
                    move();
                    Thread.sleep(moveTime);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void move() {
        int dx;
        int dy;
        if (Math.random() < 0.5) {
            dx = Math.random() < 0.5 ? -1 : 1;
            dy = 0;
        } else {
            dx = 0;
            dy = Math.random() < 0.5 ? -1 : 1;
        }
        if (x + dx < 0 || x + dx >= field.getN() || y + dy < 0 || y + dy >= field.getN()) {
            return;
        }
        if (!field.getCell(x + dx, y + dy).hasFarmer()) {
            field.getCell(x, y).removeFarmer();
            x += dx;
            y += dy;
            field.getCell(x, y).setFarmer();
        }
    }

    private void plantCarrot() {
        field.getCell(x, y).setCarrot();
    }

    private void fix() {
        field.getCell(x, y).fix();
    }

    public int[] detectRabbit() {
        if (field.getCell(x, y).hasRabbit()) {
            return new int[]{x, y};
        }
        return null;
    }

    public boolean isFarmer(){
        return true;
    }

    public void p(){
        System.out.println("Farmer");
        System.out.println("x: " + x);
        System.out.println("y: " + y);
    }
}
