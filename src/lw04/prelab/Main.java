package lw04.prelab;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner file = new Scanner(Main.class.getResourceAsStream("branches.txt"));

        int n = file.nextInt();
        int[] branches = new int[n];
        for (int i = 0; i < n; i++) {
            branches[i] = file.nextInt();
        }

        int m = file.nextInt();
        int[] targets = new int[m];
        for (int i = 0; i < m; i++) {
            targets[i] = file.nextInt();
        }
        file.close();

        insertionSort(branches, 3);
        printBranches(branches);

        for (int target : targets) {
            int index = linearSearch(branches, target);
            if (index != -1) {
                System.out.println(target + ": FOUND at index " + index);
            } else {
                System.out.println(target + ": NOT FOUND");
            }
        }
    }

    private static void insertionSort(int[] branches, int passes) {
        for (int i = 1; i <= passes; i++) {
            int key = branches[i];
            int j = i - 1;

            while (j >= 0 && branches[j] < key) {
                branches[j + 1] = branches[j];
                j = j - 1;
            }
            branches[j + 1] = key;
        }
    }

    private static void printBranches(int[] branches) {
        for (int i = 0; i < branches.length; i++) {
            System.out.print(branches[i] + (i == branches.length - 1 ? "" : " "));
        }
        System.out.println();
    }

    private static int linearSearch(int[] branches, int target) {
        for (int i = 0; i < branches.length; i++) {
            if (branches[i] == target) {
                return i;
            }
        }
        return -1;
    }
}