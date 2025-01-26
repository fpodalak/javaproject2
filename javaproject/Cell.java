package javaproject;
public class Cell {
    private int x;
    private int y;
    private boolean hasFarmer;
    private boolean hasDog;
    private boolean hasRabbit;
    private boolean hasCarrot;
    private boolean isDamaged;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        hasFarmer = false;
        hasDog = false;
        hasRabbit = false;
        hasCarrot = false;
        isDamaged = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setFarmer() {
        hasFarmer = true;
    }
    public void removeFarmer() {
        hasFarmer = false;
    }

    public boolean hasFarmer() {
        return hasFarmer;
    }

    public void setDog() {
        hasDog = true;
    }
    public void removeDog() {
        hasDog = false;
    }

    public boolean hasDog() {
        return hasDog;
    }

    public void setRabbit() {
        hasRabbit = true;
    }

    public void removeRabbit() {
        hasRabbit = false;
    }

    public boolean hasRabbit() {
        return hasRabbit;
    }

    public void setCarrot() {
        hasCarrot = true;
    }

    public void removeCarrot() {
        hasCarrot = false;
    }

    public boolean hasCarrot() {
        return hasCarrot;
    }

    public void setDamaged() {
        isDamaged = true;
    }

    public void fix() {
        isDamaged = false;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public Rabbit getRabbit() {
        for (Unit unit : Main.units) {
            if (unit.isRabbit() && unit.getX() == x && unit.getY() == y) {
                return (Rabbit) unit;
            }
        }
        return null;
    }

    public Farmer getFarmer() {
        for (Unit unit : Main.units) {
            if (unit.isFarmer() && unit.getX() == x && unit.getY() == y) {
                return (Farmer) unit;
            }
        }
        return null;
    }

    public Dog getDog() {
        for (Unit unit : Main.units) {
            if (unit.isDog() && unit.getX() == x && unit.getY() == y) {
                return (Dog) unit;
            }
        }
        return null;
    }
}