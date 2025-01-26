package javaproject;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    private static Field field;
    private static int carrots_eaten = 0;
    public static volatile boolean running = true; // Flag to indicate if the simulation is running

    public static synchronized void incrementCarrotsEaten() {
        carrots_eaten++;
    }

    public static Vector<Unit> units = new Vector<Unit>();
    public static boolean isprinting = false;

    public static void main(String[] args) {
        // Input logic
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

        // Load simulation parameters
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

        for (int i = 0; i < numOfFarmers; i++) {
            int x = (int) (Math.random() * N);
            int y = (int) (Math.random() * N);
            while (field.getCell(x, y).hasFarmer()) {
                x = (int) (Math.random() * N);
                y = (int) (Math.random() * N);
            }
            Farmer farmer = new Farmer(x, y, farmerMoveTime, farmerPlantTime, farmerFixTime, field);
            Dog dog = new Dog(x, y, dogMoveTime, dogEatTime, field, farmer);
            entities.add(farmer);
            entities.add(dog);
        }

        for (int i = 0; i < numOfRabbits; i++) {
            int x = (int) (Math.random() * N);
            int y = (int) (Math.random() * N);
            while (field.getCell(x, y).hasRabbit()) {
                x = (int) (Math.random() * N);
                y = (int) (Math.random() * N);
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

        // Start a thread to monitor for 'q' input
        Thread inputThread = new Thread(() -> {
            while (running) {
                try {
                    char input = (char) System.in.read();
                    if (input == 'q') {
                        System.out.println("\nStopping simulation...");
                        running = false; // Signal all threads to stop
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        inputThread.start();

        Grid veiw = new Grid();
        while (running) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            veiw.printGrid(field);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./game_stats.txt",true))) {
            writer.write("Carrots eaten: " + carrots_eaten);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to save game stats");
        }

        System.out.println("Simulation ended.");
    }
}
