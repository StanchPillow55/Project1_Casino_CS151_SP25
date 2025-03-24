package core;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Enforcer {

    public static final Map<String, Integer> instanceCounts = new HashMap<String, Integer>();

    public Enforcer() throws InstanceOverload {
        String className = this.getClass().getSimpleName();
        instanceCounts.put(className, instanceCounts.getOrDefault(className, 0) + 1);
        if (instanceCounts.get(className) > 100) {
            throw new InstanceOverload("Too many instances of " + className + " created. Terminating program.");
        }
    }

    public void checkNetEarnings(Person player) {
        if (player.getNetEarnings() > 1000000) {
            System.out.println("You cheated!");
            System.exit(0);
        }
    }

    public void checkInebriation(Person player) {
        if (player.getInebriation() > 5) {
            System.out.println("You are too inebriated to continue. Exiting the casino...");
            System.exit(0);
        }
    }

    public boolean checkExit(String input, Scanner scanner) {
        if (input.toUpperCase().equals("EXIT")) {
            System.out.println("Exiting the casino... Goodbye!");
            scanner.close();
            System.exit(0);
        }
        return false;
    }
}
