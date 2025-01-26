package javaproject;

import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.atomic.AtomicBoolean;
/*
 * The deadline is the end of January(30th of January)

The farmers have a field of size NxN, where they grows carrots. Farmers move randomly and plant carrots if they are not already planted. Carrots grow for several rounds. Occasionally, rabbits appear on the field, damaging the crops and eating the carrots. Each rabbit spends a random number of turns eating carrots. Each farmer is assisted by a dog that eliminates rabbits. The dog moves randomly, unless a rabbit is spotted by the farmer, or if the dog detects a rabbit within a range of 5 tiles, in which case it starts moving towards the rabbit. After eating the carrots, the rabbit leaves behind damaged land that the farmer must repair. Repairing the land and replanting carrots also takes a certain amount of time.
 */


public class Main {
    private static AtomicBoolean running = new AtomicBoolean(true);
    private static Field field;
    private static int carrots_eaten = 0;

    public static synchronized void incrementCarrotsEaten() {
        carrots_eaten++;
    }

    public static Vector<Unit> units = new Vector<Unit>();
    public static boolean isprinting = false;
    public static boolean wantsToQuit = false;

    public static void main(String[] args) {
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press 'q' to stop the program...");
            while (running.get()) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    running.set(false);
                }
            }
        });
        inputThread.start();
        
        while (true) {
            System.out.print("Enter 'r' to run the simulation or 's' to display stats: ");
            String option = System.console().readLine().trim().toLowerCase();

            if (option.equals("s")) {
                try (BufferedReader br = new BufferedReader(new FileReader("./game_stats.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to load game stats");
                }
            } else if (option.equals("r")) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.print("Enter field size: ");
        int N = Integer.parseInt(System.console().readLine());
        System.out.print("Enter number of farmers: ");
        int numOfFarmers = Integer.parseInt(System.console().readLine());
        System.out.print("Enter number of rabbits: ");
        int numOfRabbits = Integer.parseInt(System.console().readLine());

        int rabbitMoveTime = 0, rabbitEatTime = 0;
        int farmerMoveTime = 0, farmerPlantTime = 0, farmerFixTime = 0;
        int dogMoveTime = 0, dogEatTime = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("./simulation_paramiters.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2) {
                    int value = Integer.parseInt(parts[1].trim());
                    switch (parts[0].trim()) {
                        case "rabbit_movement_speed":
                            rabbitMoveTime = value;
                            break;
                        case "rabbit_eat_time":
                            rabbitEatTime = value;
                            break;
                        case "farmer_movement_speed":
                            farmerMoveTime = value;
                            break;
                        case "farmer_plant_time":
                            farmerPlantTime = value;
                            break;
                        case "farmer_fix_time":
                            farmerFixTime = value;
                            break;
                        case "dog_movement_speed":
                            dogMoveTime = value;
                            break;
                        case "dog_eat_time":
                            dogEatTime = value;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load simulation parameters");
            return;
        }

        field = new Field(N);
        Vector<Thread> threads = new Vector<>();
        Vector<Runnable> entities = new Vector<>();

        for (int i=0; i<numOfFarmers; i++) {
            int x = (int)(Math.random() * N);
            int y = (int)(Math.random() * N);
            while (field.getCell(x, y).hasFarmer()) {
                x = (int)(Math.random() * N);
                y = (int)(Math.random() * N);
            }
            Farmer farmer = new Farmer(x, y, farmerMoveTime, farmerPlantTime, farmerFixTime, field);
            Dog dog = new Dog(x, y, dogMoveTime, dogEatTime, field);
            entities.add(farmer);
            entities.add(dog);
        }

        for (int i=0; i<numOfRabbits; i++) {
            int x = (int)(Math.random() * N);
            int y = (int)(Math.random() * N);
            while (field.getCell(x, y).hasRabbit()) {
                x = (int)(Math.random() * N);
                y = (int)(Math.random() * N);
            }
            Rabbit rabbit = new Rabbit(x, y, rabbitMoveTime, rabbitEatTime, field);
            entities.add(rabbit);
        }
        
        for (Unit unit : units) {
            unit.p();
        }
        for (Runnable entity : entities) {
            Thread thread = new Thread(entity);
            threads.add(thread);
            thread.start();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./game_stats.txt"))) {
            writer.write("Carrots eaten: " + carrots_eaten);
        } catch (IOException e) {
            System.err.println("Failed to save game stats");
        }

        Grid veiw = new Grid();
        while (running.get()) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            veiw.printGrid(field);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./game_stats.txt"))) {
            writer.write("Carrots eaten: " + carrots_eaten);
        } catch (IOException e) {
            System.err.println("Failed to save game stats");
        }
        System.out.println("Program stopped.");
    }
}