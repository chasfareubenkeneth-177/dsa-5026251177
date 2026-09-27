package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class BankTransaction {
    public static void main(String[] args) {
        // Inisialisasi struktur data sesuai ketentuan
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();
        Queue<String[]> queue = new LinkedList<>();
        Stack<String[]> failedTransactions = new Stack<>();

        try {
            Scanner scanner = new Scanner(new File("src/lw02/prelab/transactions.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    transactions.add(parts);

                    String name = parts[0];
                    boolean customerExists = false;
                    for (String[] customer : customers) {
                        if (customer[0].equals(name)) {
                            customerExists = true;
                            break;
                        }
                    }
                    if (!customerExists) {
                        customers.add(new String[] { name, "0" });
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File transactions.txt tidak ditemukan.");
            return;
        }

        queue.addAll(transactions);

        while (!queue.isEmpty()) {
            String[] currentTx = queue.poll();
            String name = currentTx[0];
            String type = currentTx[1];
            int amount = Integer.parseInt(currentTx[2]);

            for (String[] customer : customers) {
                if (customer[0].equals(name)) {
                    int balance = Integer.parseInt(customer[1]);

                    if (type.equals("DEPOSIT")) {
                        balance += amount;
                        customer[1] = String.valueOf(balance);
                    } else if (type.equals("WITHDRAW")) {
                        if (amount > balance) {
                            failedTransactions.push(currentTx);
                        } else {
                            balance -= amount;
                            customer[1] = String.valueOf(balance);
                        }
                    }
                    break;
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + ": " + customer[1]);
        }

        System.out.println("=== Failed Transactions ===");
        // Mengeluarkan transaksi gagal dari Stack (LIFO)
        while (!failedTransactions.isEmpty()) {
            String[] failedTx = failedTransactions.pop();
            System.out.println(failedTx[0] + " " + failedTx[1] + " " + failedTx[2]);
        }
    }
}