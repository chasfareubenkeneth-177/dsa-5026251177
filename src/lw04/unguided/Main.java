package lw04.unguided;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner parcelsScanner = new Scanner(
                Main.class.getResourceAsStream("parcels.txt"));

        int parcelCount = parcelsScanner.nextInt();
        int[] parcels = new int[parcelCount];
        for (int i = 0; i < parcelCount; i++) {
            parcels[i] = parcelsScanner.nextInt();
        }

        int targetCount = parcelsScanner.nextInt();
        int[] targets = new int[targetCount];
        for (int i = 0; i < targetCount; i++) {
            targets[i] = parcelsScanner.nextInt();
        }
        parcelsScanner.close();

        int[] afterTwoPasses = new int[parcelCount];
        for (int pass = 0; pass < parcelCount - 1; pass++) {
            for (int j = 0; j < parcelCount - 1 - pass; j++) {
                if (parcels[j] < parcels[j + 1]) {
                    int temporary = parcels[j];
                    parcels[j] = parcels[j + 1];
                    parcels[j + 1] = temporary;
                }
            }

            if (pass == 1) {
                for (int i = 0; i < parcelCount; i++) {
                    afterTwoPasses[i] = parcels[i];
                }
            }
        }

        System.out.println("==== After 2 Passes ====");
        for (int i = 0; i < parcelCount; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(afterTwoPasses[i]);
        }
        System.out.println();

        System.out.println("==== Fully Sorted ====");
        for (int i = 0; i < parcelCount; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(parcels[i]);
        }
        System.out.println();

        System.out.println("==== Search Results ====");
        for (int i = 0; i < targetCount; i++) {
            int target = targets[i];
            int low = 0;
            int high = parcelCount - 1;
            int steps = 0;
            int foundIndex = -1;

            while (low <= high) {
                int middle = (low + high) / 2;
                steps++;

                if (parcels[middle] == target) {
                    foundIndex = middle;
                    break;
                } else if (parcels[middle] < target) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            }

            if (foundIndex >= 0) {
                System.out.println(target + ": FOUND at index " + foundIndex
                        + " (steps: " + steps + ")");
            } else {
                System.out.println(target + ": NOT FOUND (steps: " + steps + ")");
            }
        }
    }
}