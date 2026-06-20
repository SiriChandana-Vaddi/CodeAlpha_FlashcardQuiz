import java.util.ArrayList;
import java.util.Scanner;

/**
 * CodeAlpha Internship - Task 1
 * Student Grade Tracker
 *
 * A console-based Java program to input and manage student grades,
 * calculate average/highest/lowest scores, and display a summary report.
 */
public class StudentGradeTracker {

    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("      STUDENT GRADE TRACKER");
        System.out.println("=========================================");

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addGradeToStudent();
                    break;
                case 3:
                    viewStudentReport();
                    break;
                case 4:
                    displaySummaryReport();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    System.out.println("\nExiting... Thank you for using Student Grade Tracker!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 6.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1. Add a new student");
        System.out.println("2. Add grade to a student");
        System.out.println("3. View individual student report");
        System.out.println("4. View summary report (all students)");
        System.out.println("5. Remove a student");
        System.out.println("6. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }

        if (findStudent(name) != null) {
            System.out.println("A student with this name already exists.");
            return;
        }

        students.add(new Student(name));
        System.out.println("Student \"" + name + "\" added successfully.");
    }

    private static void addGradeToStudent() {
        if (students.isEmpty()) {
            System.out.println("No students found. Please add a student first.");
            return;
        }

        Student student = selectStudent();
        if (student == null) return;

        double grade = readDouble("Enter grade for " + student.getName() + " (0-100): ");

        if (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100.");
            return;
        }

        student.addGrade(grade);
        System.out.println("Grade " + grade + " added to " + student.getName() + ".");
    }

    private static void viewStudentReport() {
        if (students.isEmpty()) {
            System.out.println("No students found. Please add a student first.");
            return;
        }

        Student student = selectStudent();
        if (student == null) return;

        printStudentReport(student);
    }

    private static void printStudentReport(Student student) {
        System.out.println("\n--------- Report: " + student.getName() + " ---------");
        if (student.getGrades().isEmpty()) {
            System.out.println("No grades recorded yet.");
            return;
        }

        System.out.println("Grades     : " + student.getGrades());
        System.out.printf("Average    : %.2f%n", student.getAverage());
        System.out.printf("Highest    : %.2f%n", student.getHighest());
        System.out.printf("Lowest     : %.2f%n", student.getLowest());
        System.out.println("Letter Grade: " + student.getLetterGrade());
    }

    private static void displaySummaryReport() {
        if (students.isEmpty()) {
            System.out.println("No students found. Please add a student first.");
            return;
        }

        System.out.println("\n=========================================");
        System.out.println("           SUMMARY REPORT");
        System.out.println("=========================================");
        System.out.printf("%-15s %-8s %-8s %-8s %-6s%n", "Name", "Average", "Highest", "Lowest", "Grade");
        System.out.println("-----------------------------------------");

        double classTotal = 0;
        int countWithGrades = 0;

        for (Student s : students) {
            if (s.getGrades().isEmpty()) {
                System.out.printf("%-15s %-8s %-8s %-8s %-6s%n", s.getName(), "-", "-", "-", "-");
                continue;
            }
            System.out.printf("%-15s %-8.2f %-8.2f %-8.2f %-6s%n",
                    s.getName(), s.getAverage(), s.getHighest(), s.getLowest(), s.getLetterGrade());
            classTotal += s.getAverage();
            countWithGrades++;
        }

        System.out.println("-----------------------------------------");
        if (countWithGrades > 0) {
            System.out.printf("Class Average (of students with grades): %.2f%n", classTotal / countWithGrades);
        } else {
            System.out.println("No grades recorded for any student yet.");
        }
        System.out.println("Total Students: " + students.size());
        System.out.println("=========================================");
    }

    private static void removeStudent() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        Student student = selectStudent();
        if (student == null) return;

        students.remove(student);
        System.out.println("Student \"" + student.getName() + "\" removed.");
    }

    // ---------- Helper methods ----------

    private static Student selectStudent() {
        System.out.println("\nStudents:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName());
        }

        int index = readInt("Select student number: ") - 1;

        if (index < 0 || index >= students.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return students.get(index);
    }

    private static Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}