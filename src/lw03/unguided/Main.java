package lw03.unguided;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> registeredStudents = new HashSet<>();
        Set<String> checkedInStudents = new HashSet<>();
        List<String> checkInResults = new ArrayList<>();
        int rejectedAttempts = 0;

        Scanner registrationScanner = new Scanner(
                Main.class.getResourceAsStream("registrations.txt"));
        while (registrationScanner.hasNext()) {
            registeredStudents.add(registrationScanner.next());
        }
        registrationScanner.close();

        Scanner checkInScanner = new Scanner(
                Main.class.getResourceAsStream("checkins.txt"));
        while (checkInScanner.hasNext()) {
            String studentId = checkInScanner.next();

            if (!registeredStudents.contains(studentId)) {
                checkInResults.add(studentId + ": Rejected (not registered)");
                rejectedAttempts++;
            } else if (checkedInStudents.contains(studentId)) {
                checkInResults.add(studentId + ": Rejected (already checked in)");
                rejectedAttempts++;
            } else {
                checkedInStudents.add(studentId);
                checkInResults.add(studentId + ": Checked in");
            }
        }
        checkInScanner.close();

        System.out.println("===== Event Check-In Results =====");
        for (String result : checkInResults) {
            System.out.println(result);
        }

        System.out.println();
        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + registeredStudents.size());
        System.out.println("Successful check-ins: " + checkedInStudents.size());
        System.out.println("Absent students: " + (registeredStudents.size() - checkedInStudents.size()));
        System.out.println("Rejected attempts: " + rejectedAttempts);
    }

}