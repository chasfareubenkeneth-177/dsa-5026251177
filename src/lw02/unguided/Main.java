package lw02.unguided;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main { 
    public static void main(String[] args) {
        LinkedList<String[]> orders = new LinkedList<>();
        LinkedList<String[]> foodStock = new LinkedList<>();
        LinkedList<String[]> drinkStock = new LinkedList<>();
        LinkedList<String[]> successfulOrders = new LinkedList<>();

        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("orders.txt"))) {
            while (scanner.hasNext()) {
                String[] order = new String[4];
                order[0] = scanner.next();
                order[1] = scanner.next();
                order[2] = scanner.next();
                order[3] = scanner.next();
                orders.add(order);
            }
        }

        foodStock.add(new String[] {"Bakso", "2"});
        foodStock.add(new String[] {"Sate", "1"});
        foodStock.add(new String[] {"Soto", "2"});

        drinkStock.add(new String[] {"EsTeh", "4"});
        drinkStock.add(new String[] {"EsJeruk", "2"});

        Queue<String[]> orderQueue = new LinkedList<>();
        orderQueue.addAll(orders);
        Stack<String[]> failedOrders = new Stack<>();

        while (orderQueue.peek() != null) {
            String[] order = orderQueue.poll();
            int foodIndex = -1;
            int drinkIndex = -1;
            boolean available = true;

            if (!order[1].equals("-")) {
                foodIndex = findStockIndex(foodStock, order[1]);
                if (foodIndex == -1 || Integer.parseInt(foodStock.get(foodIndex)[1]) == 0) {
                    available = false;
                }
            }

            if (!order[2].equals("-")) {
                drinkIndex = findStockIndex(drinkStock, order[2]);
                if (drinkIndex == -1 || Integer.parseInt(drinkStock.get(drinkIndex)[1]) == 0) {
                    available = false;
                }
            }

            if (available) {
                if (foodIndex != -1) {
                    int stock = Integer.parseInt(foodStock.get(foodIndex)[1]);
                    foodStock.get(foodIndex)[1] = String.valueOf(stock - 1);
                }
                if (drinkIndex != -1) {
                    int stock = Integer.parseInt(drinkStock.get(drinkIndex)[1]);
                    drinkStock.get(drinkIndex)[1] = String.valueOf(stock - 1);
                }
                successfulOrders.add(order);
            } else {
                failedOrders.push(order);
            }
        }

        System.out.println("=== Successfully Processed Orders ===");
        for (String[] order : successfulOrders) {
            printOrder(order);
        }

        System.out.println("=== Remaining Food Stock ===");
        printStock(foodStock);

        System.out.println("=== Remaining Drink Stock ===");
        printStock(drinkStock);

        System.out.println("=== Failed Orders ===");
        while (!failedOrders.isEmpty()) {
            printOrder(failedOrders.pop());
        }
    }

    private static int findStockIndex(LinkedList<String[]> stock, String itemName) {
        for (int index = 0; index < stock.size(); index++) {
            if (stock.get(index)[0].equals(itemName)) {
                return index;
            }
        }
        return -1;
    }

    private static void printOrder(String[] order) {
        System.out.println(order[0] + " " + order[1] + " " + order[2] + " " + order[3]);
    }

    private static void printStock(LinkedList<String[]> stock) {
        for (String[] item : stock) {
            System.out.println(item[0] + ": " + item[1]);
        }
    }
}