package lw01.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<PrintJob> printJobs = new ArrayList<>();

        File file = new File("src/lw01/prelab/jobs.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                String id = scanner.next();
                int pages = scanner.nextInt();

                if (type.equals("MONO")) {
                    printJobs.add(new MonoPrint(id, pages));
                } else if (type.equals("COLOUR")) {
                    printJobs.add(new ColourPrint(id, pages));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File jobs.txt tidak ditemukan.");
            return;
        }

        for (PrintJob job : printJobs) {
            System.out.println(job.summary());
        }
    }
}
