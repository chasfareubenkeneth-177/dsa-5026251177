package lw05.unguided;

import java.util.*;

public class Main {

    static int[][] graph;
    static int size;
    static String[] names;

    static int[] bfsLevel;
    static int[] bfsPrev;
    static int[] dijkstraPrev;

    static void addEdge(int from, int to, int minutes) {
        graph[from][to] = minutes;
        graph[to][from] = minutes;
    }

    static List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        bfsLevel = new int[size];
        bfsPrev = new int[size];

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0 && !visited.contains(next)) {
                    visited.add(next);
                    bfsLevel[next] = bfsLevel[current] + 1;
                    bfsPrev[next] = current;
                    queue.add(next);
                }
            }
        }

        return order;
    }

    static void printLevels(List<Integer> order) {
        int lastLevel = bfsLevel[order.get(order.size() - 1)];

        for (int level = 0; level <= lastLevel; level++) {
            System.out.println();
            System.out.print("Level " + level + ": ");

            boolean first = true;

            for (int i = 0; i < order.size(); i++) {
                int station = order.get(i);

                if (bfsLevel[station] == level) {
                    if (!first) {
                        System.out.print(", ");
                    }

                    System.out.print(names[station]);
                    first = false;
                }
            }
        }

        System.out.println();
    }

    static void dijkstraAlg(int start) {
        int[] dist = new int[size];

        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;

        boolean[] visited = new boolean[size];
        dijkstraPrev = new int[size];

        for (int i = 0; i < size; i++) {
            int current = findTheNextNode(visited, dist);

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0
                        && !visited[next]) {

                    int newDist = dist[current] + graph[current][next];

                    if (newDist < dist[next]) {
                        dist[next] = newDist;
                        dijkstraPrev[next] = current;
                    }
                }
            }
        }
    }

    static int findTheNextNode(boolean[] visited, int[] dist) {
        int min = Integer.MAX_VALUE;
        int closest = -1;

        for (int i = 0; i < size; i++) {
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                closest = i;
            }
        }

        return closest;
    }

    static List<Integer> buildPath(int current, int[] prev, int start) {
        List<Integer> path;

        if (current == start) {
            path = new ArrayList<>();
        } else {
            path = buildPath(prev[current], prev, start);
        }

        path.add(current);
        return path;
    }

    static int pathTime(List<Integer> path) {
        int total = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            total += graph[path.get(i)][path.get(i + 1)];
        }

        return total;
    }

    static void printPath(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(names[path.get(i)]);

            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    static void printSummary(String title, List<Integer> path) {
        System.out.println("=== " + title + " ===");
        printPath(path);

        System.out.println("Stops: " + (path.size() - 1)
                + " | Time: " + pathTime(path) + " min");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                Main.class.getResourceAsStream("metro1.txt"));

        size = scanner.nextInt();
        names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = scanner.next();
        }

        graph = new int[size][size];
        int edges = scanner.nextInt();
        for (int i = 0; i < edges; i++) {
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            int minutes = scanner.nextInt();
            addEdge(v1, v2, minutes);
        }

        int start = scanner.nextInt();
        int dest = scanner.nextInt();
        scanner.close();

        List<Integer> order = bfs(start);
        System.out.print("=== Station Levels from " + names[start] + " ===");
        printLevels(order);

        List<Integer> fewestStopsPath = buildPath(dest, bfsPrev, start);
        printSummary("Fewest Stops Route from " + names[start] + " to " + names[dest], fewestStopsPath);

        dijkstraAlg(start);
        List<Integer> fastestPath = buildPath(dest, dijkstraPrev, start);
        printSummary("Fastest Route from " + names[start] + " to " + names[dest], fastestPath);

        System.out.println("=== Comparison ===");
        int timeSaved = pathTime(fewestStopsPath) - pathTime(fastestPath);
        int extraStops = (fastestPath.size() - 1) - (fewestStopsPath.size() - 1);

        if (timeSaved == 0 && extraStops == 0) {
            System.out.println("Both routes are the same.");
        } else {
            System.out
                    .println("The fastest route saves " + timeSaved + " min but needs " + extraStops + " more stops.");
        }
    }
}