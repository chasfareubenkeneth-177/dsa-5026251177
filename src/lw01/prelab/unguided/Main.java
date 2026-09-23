package lw01.prelab.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("src/lw01/prelab/unguided/washes.txt"));
            if (!scanner.hasNextInt()) {
                scanner.close();
                return;
            }
            
            int totalRecords = scanner.nextInt();
            WashService[] services = new WashService[totalRecords];
            int[] unitsArray = new int[totalRecords]; 

            for (int i = 0; i < totalRecords; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                int units = scanner.nextInt();
                
                unitsArray[i] = units;

                if (type.equals("MOTORCYCLE")) {
                    services[i] = new MotorcycleWash(id, days);
                } else if (type.equals("CAR")) {
                    services[i] = new CarWash(id, days);
                }
            }

            for (int i = 0; i < totalRecords; i++) {
                String output = services[i].getId() + " | " 
                              + services[i].label() + " | " 
                              + services[i].calculateCharge(unitsArray[i]);
                System.out.println(output);
            }
            
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File washes.txt tidak ditemukan.");
        }
    }
}
