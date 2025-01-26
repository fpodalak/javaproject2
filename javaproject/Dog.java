package javaproject;

public class Dog implements Runnable{
    private int x;
    private int y;
    private Field field;
    private int moveTime;
    private int eatTime;
    
    public Dog(int x, int y, int moveTime, int eatTime, Field field) {
        this.x = x;
        this.y = y;
        this.moveTime = moveTime;
        this.eatTime = eatTime;
        this.field = field;
        this.field.getCell(x, y).setDog();
    }
    public void run() {
        while (true) {
            try {
                if(field.getCell(x, y).hasRabbit())
                {
                    Thread.sleep(eatTime);
                    eatRabbit();
                }else{
                    int[] detection = detectRabbit();
                    if (detection != null) {
                        this.moveTowardsRabbit(detection[0], detection[1]);
                    } else {
                        this.moveRandomly();
                    }
                    Thread.sleep(moveTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] detectRabbit() {
        for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                if (x + dx >= 0 && x + dx < field.getN() && y + dy >= 0 && y + dy < field.getN()) {
                    if (field.getCell(x + dx, y + dy).hasRabbit()) {
                        return new int[]{x + dx, y + dy};
                    }
                }
            }
        }
        return null;
    }

    public void moveRandomly() {
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
        field.getCell(x, y).removeDog();
        x += dx;
        y += dy;
        field.getCell(x, y).setDog();
    }

    public void moveTowardsRabbit(int rabbitX, int rabbitY) {
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
        field.getCell(x, y).removeDog();
        x += dx;
        y += dy;
        field.getCell(x, y).setDog();
        
    }

    private void eatRabbit() {
        field.getCell(x, y).removeRabbit();
        // rabbit.die(x, y); //2:19, nie rozumuem, jestem zbyt zęczony - powinien być odnośnik do klasy rabbit, nie klasy field
    }
}
