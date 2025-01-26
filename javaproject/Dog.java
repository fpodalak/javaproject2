package javaproject;

public class Dog extends Unit implements Runnable{
    private int moveTime;
    private int eatTime;
    private Farmer farmer;
    
    public Dog(int x, int y, int moveTime, int eatTime, Field field, Farmer farmer) {
        super(x, y, field);
        this.x = x;
        this.y = y;
        this.moveTime = moveTime;
        this.eatTime = eatTime;
        this.field = field;
        this.farmer = farmer;
        this.field.getCell(x, y).setDog();
        Main.units.add(this);
    }

    public void run() {
        while (true) {
            try {
                while (Main.isprinting) {
                    Thread.sleep(1);
                }
                Rabbit currentRabbit = field.getCell(x, y).getRabbit();
                if(currentRabbit != null && currentRabbit.isAlive())
                {
                    eat(currentRabbit);
                }else{
                    int[] detection = detectRabbit();
                    if (detection != null) {
                        this.moveTowardsRabbit(detection[0], detection[1]);
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

    public int[] detectRabbit() {
        int[] farmerPosition = farmer.detectRabbit();
        if (field.getCell(farmerPosition[0], farmerPosition[1]).hasRabbit()) {
            return farmerPosition;
        }
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
        if (!field.getCell(x + dx, y + dy).hasDog()) {
            field.getCell(x, y).removeDog();
            x += dx;
            y += dy;
            field.getCell(x, y).setDog();
        }
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
        if (!field.getCell(x + dx, y + dy).hasDog()) {
            field.getCell(x, y).removeDog();
            x += dx;
            y += dy;
            field.getCell(x, y).setDog();
        }
    }

    public void eat(Rabbit rabbit) {
        field.getCell(rabbit.getX(), rabbit.getY()).removeRabbit();
        rabbit.die();
    }

    public boolean isDog(){
        return true;
    }

    public void p(){
        System.out.println("Dog");
        System.out.println("x: " + x);
        System.out.println("y: " + y);
    }
}
