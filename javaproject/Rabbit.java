package javaproject;
import java.util.Random;

public class Rabbit extends Unit implements Runnable{
    private int moveTime;
    private int eatTime;
    private boolean alive = true;

    public Rabbit(int x, int y, int moveTime, int eatTime, Field field) {
        super(x, y, field);
        this.x = x;
        this.y = y;
        this.moveTime = moveTime;
        this.eatTime = (new Random().nextInt(3) + 1) * eatTime;
        this.field = field;
        this.field.getCell(x, y).setRabbit();
        Main.units.add(this);
    }
    public void run() {
        while (alive) {
            try {
                while (Main.isprinting) {
                    Thread.sleep(1);
                }
                if(field.getCell(x, y).hasCarrot())
                {
                    Thread.sleep(eatTime);
                    if(alive) {
                        eat();
                        damage();
                    }
                }else{
                    int[] detection = detectcarrot();
                    if (detection != null) {
                        this.moveTowardsCarrot(detection[0], detection[1]);
                    } else {
                        this.move();
                    }
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
        if (!field.getCell(x + dx, y + dy).hasRabbit()) {
            field.getCell(x, y).removeRabbit();
            x += dx;
            y += dy;
            field.getCell(x, y).setRabbit();
        }
    }

    public void moveTowardsCarrot(int rabbitX, int rabbitY) {
        int dx = rabbitX - x;
        int dy = rabbitY - y;
        if (Math.abs(dx) > Math.abs(dy)) {
            dx = dx > 0 ? 1 : -1;
            dy = 0;
        } else {
            dx = 0;
            dy = dy > 0 ? 1 : -1;
        }
        if (x + dx < 0 || x + dx >= field.getN() || y + dy < 0 || y + dy >= field.getN()) {
            return;
        }
        if (!field.getCell(x + dx, y + dy).hasRabbit()) {
            field.getCell(x, y).removeRabbit();
            x += dx;
            y += dy;
            field.getCell(x, y).setRabbit();
        }
    }

    public int[] detectcarrot() {
        for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                if (x + dx >= 0 && x + dx < field.getN() && y + dy >= 0 && y + dy < field.getN()) {
                    if (field.getCell(x + dx, y + dy).hasCarrot()) {
                        return new int[]{x + dx, y + dy};
                    }
                }
            }
        }
        return null;
    }

    public void die() {
        alive = false;
        System.out.println("Rabbit died");
    }

    public boolean isAlive() {
        return alive;
    }
    
    public void eat() {
        field.getCell(x, y).removeCarrot();
        Main.incrementCarrotsEaten();
        System.out.println("Carrot died");
    }
      
    private void damage() {
        field.getCell(x, y).setDamaged();
    }

    public boolean isRabbit(){
        return true;
    }
}
