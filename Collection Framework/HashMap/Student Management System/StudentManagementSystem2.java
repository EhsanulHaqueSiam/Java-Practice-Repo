
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId;
    private String name;
    private String address;
    private String programTaken;

    public Student(String studentId, String name, String address, String programTaken) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        this.programTaken = programTaken;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getProgramTaken() {
        return programTaken;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + name + "\nAddress: " + address + "\nProgram Taken: "
                + programTaken;
    }
}

public class StudentManagementSystem2 {
    private static final String FILENAME = "students.ser";

    public static void main(String[] args) {
        HashMap<String, Student> studentMap = new HashMap<>();
        loadStudentsFromFile(studentMap);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. List All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addStudent(studentMap, scanner);
                    break;
                case 2:
                    viewStudent(studentMap, scanner);
                    break;
                case 3:
                    updateStudent(studentMap, scanner);
                    break;
                case 4:
                    deleteStudent(studentMap, scanner);
                    break;
                case 5:
                    listAllStudents(studentMap);
                    break;
                case 6:
                    saveStudentsToFile(studentMap);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadStudentsFromFile(HashMap<String, Student> studentMap) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            studentMap.putAll((HashMap<String, Student>) ois.readObject());
            System.out.println("Students loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing student data found.");
        }
    }

    private static void saveStudentsToFile(HashMap<String, Student> studentMap) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(studentMap);
            System.out.println("Students saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
        }
    }

    private static void addStudent(HashMap<String, Student> studentMap, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Program Taken: ");
        String programTaken = scanner.nextLine();

        Student student = new Student(studentId, name, address, programTaken);
        studentMap.put(studentId, student);
        System.out.println("Student added successfully.");
    }

    private static void viewStudent(HashMap<String, Student> studentMap, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentMap.get(studentId);
        if (student != null) {
            System.out.println(student.toString());
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent(HashMap<String, Student> studentMap, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentMap.get(studentId);
        if (student != null) {
            System.out.println("Current Student Information:");
            System.out.println(student.toString());
            System.out.println("Enter new information:");

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Program Taken: ");
            String programTaken = scanner.nextLine();

            student = new Student(studentId, name, address, programTaken);
            studentMap.put(studentId, student);
            System.out.println("Student information updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void deleteStudent(HashMap<String, Student> studentMap, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentMap.remove(studentId);
        if (student != null) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void listAllStudents(HashMap<String, Student> studentMap) {
        if (studentMap.isEmpty()) {
            System.out.println("No students in the database.");
        } else {
            System.out.println("List of all students:");
            for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
                System.out.println(entry.getValue().toString());
            }
        }
    }
}