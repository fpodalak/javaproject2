package javaproject;
import java.util.Random;

public class Rabbit implements Runnable{
    private int x;
    private int y;
    private Field field;
    private int moveTime;
    private int eatTime;
    private boolean alive = true;

    public Rabbit(int x, int y, int moveTime, int eatTime, Field field) {
        this.x = x;
        this.y = y;
        this.moveTime = moveTime;
        this.eatTime = (new Random().nextInt(3) + 1) * eatTime;
        this.field = field;
        this.field.getCell(x, y).setRabbit();
    }
    public void run() {
        while (alive) {
            try {
                if(field.getCell(x, y).hasCarrot())
                {
                    Thread.sleep(eatTime);
                    if(alive) {
                        eat();
                        damage();
                    }
                }else{
                    move();
                    Thread.sleep(moveTime);
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void move() {
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
        field.getCell(x, y).removeRabbit();
        x += dx;
        y += dy;
        field.getCell(x, y).setRabbit();
    }
    
    public void die(int x, int y) {
        alive = false;
        field.getCell(x, y).removeRabbit();
    }
    
    public void eat() {
        field.getCell(x, y).removeCarrot();
        Main.incrementCarrotsEaten();
    }
      
    private void damage() {
        field.getCell(x, y).setDamaged();
    }
}
