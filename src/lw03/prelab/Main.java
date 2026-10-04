package lw03.prelab;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("===== Problem 1 =====");
        problem1();
        
        System.out.println("\n===== Problem 2 =====");
        problem2();
        
        System.out.println("\n===== Problem 3 =====");
        problem3();
    }

    private static void problem1() {
        List<String> playlist = new ArrayList<>();
        // Menggunakan getResourceAsStream
        InputStream is = Main.class.getResourceAsStream("playlist.txt");
        
        if (is == null) {
            System.out.println("playlist.txt tidak ditemukan");
            return;
        }
        
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split(" ", 2);
                String command = parts[0];

                if (command.equals("ADD")) {
                    playlist.add(parts[1]);
                } else if (command.equals("INSERT")) {
                    String[] insertParts = parts[1].split(" ", 2);
                    int index = Integer.parseInt(insertParts[0]);
                    String song = insertParts[1];
                    playlist.add(index, song);
                } else if (command.equals("REMOVE")) {
                    playlist.remove(parts[1]);
                }
            }
        }

        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

    private static void problem2() {
        Set<String> participants = new LinkedHashSet<>();
        int duplicates = 0;
        
        InputStream is = Main.class.getResourceAsStream("participants.txt");
        
        if (is == null) {
            System.out.println("participants.txt tidak ditemukan");
            return;
        }
        
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) continue;
                
                if (!participants.add(name)) {
                    duplicates++;
                }
            }
        }

        System.out.println("Unique participants: " + participants.size());
        int count = 1;
        for (String participant : participants) {
            System.out.println(count + ". " + participant);
            count++;
        }
        System.out.println("Duplicate registrations: " + duplicates);
    }

    private static void problem3() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;
        
        InputStream is = Main.class.getResourceAsStream("inventory.txt");
        
        if (is == null) {
            System.out.println("inventory.txt tidak ditemukan");
            return;
        }
        
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split(" ");
                String type = parts[0];
                String product = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                if (type.equals("ADD")) {
                    if (inventory.containsKey(product)) {
                        inventory.put(product, inventory.get(product) + quantity);
                    } else {
                        inventory.put(product, quantity);
                    }
                } else if (type.equals("SELL")) {
                    if (inventory.containsKey(product) && inventory.get(product) >= quantity) {
                        inventory.put(product, inventory.get(product) - quantity);
                    } else {
                        failedSales++;
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }
}