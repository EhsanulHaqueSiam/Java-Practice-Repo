import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String name;
    private String address;
    private String programTaken;
    private Set<String> enrolledCourses;

    public Student(String studentId, String name, String address, String programTaken) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        this.programTaken = programTaken;
        this.enrolledCourses = new HashSet<>();
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

    public Set<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + name + "\nAddress: " + address + "\nProgram Taken: "
                + programTaken;
    }
}

class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseId;
    private String courseName;
    private int maxCapacity;
    private Set<String> enrolledStudents;

    public Course(String courseId, String courseName, int maxCapacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new HashSet<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Set<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    @Override
    public String toString() {
        return "Course ID: " + courseId + "\nCourse Name: " + courseName + "\nMax Capacity: " + maxCapacity;
    }
}

public class StudentManagementSystem {
    private static final String STUDENTS_FILE = "students.ser";
    private static final String COURSES_FILE = "courses.ser";

    public static void main(String[] args) {
        HashMap<String, Student> studentMap = loadStudentsFromFile();
        HashMap<String, Course> courseMap = loadCoursesFromFile();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. List All Students");
            System.out.println("6. Add Course");
            System.out.println("7. List All Courses");
            System.out.println("8. Enroll Student in Course");
            System.out.println("9. Generate Student Report");
            System.out.println("10. Exit");
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
                    addCourse(courseMap, scanner);
                    break;
                case 7:
                    listAllCourses(courseMap);
                    break;
                case 8:
                    enrollStudentInCourse(studentMap, courseMap, scanner);
                    break;
                case 9:
                    generateStudentReport(studentMap, courseMap, scanner);
                    break;
                case 10:
                    saveStudentsToFile(studentMap);
                    saveCoursesToFile(courseMap);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static HashMap<String, Student> loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENTS_FILE))) {
            return (HashMap<String, Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing student data found.");
            return new HashMap<>();
        }
    }

    private static void saveStudentsToFile(HashMap<String, Student> studentMap) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENTS_FILE))) {
            oos.writeObject(studentMap);
            System.out.println("Students saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
        }
    }

    private static HashMap<String, Course> loadCoursesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COURSES_FILE))) {
            return (HashMap<String, Course>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing course data found.");
            return new HashMap<>();
        }
    }

    private static void saveCoursesToFile(HashMap<String, Course> courseMap) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COURSES_FILE))) {
            oos.writeObject(courseMap);
            System.out.println("Courses saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving courses to file: " + e.getMessage());
        }
    }

    private static void addStudent(HashMap<String, Student> studentMap, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        if (studentMap.containsKey(studentId)) {
            System.out.println("Student ID already exists. Please choose a different ID.");
            return;
        }
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

    private static void addCourse(HashMap<String, Course> courseMap, Scanner scanner) {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        if (courseMap.containsKey(courseId)) {
            System.out.println("Course ID already exists. Please choose a different ID.");
            return;
        }
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Max Capacity: ");
        int maxCapacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Course course = new Course(courseId, courseName, maxCapacity);
        courseMap.put(courseId, course);
        System.out.println("Course added successfully.");
    }

    private static void listAllCourses(HashMap<String, Course> courseMap) {
        if (courseMap.isEmpty()) {
            System.out.println("No courses in the database.");
        } else {
            System.out.println("List of all courses:");
            for (Map.Entry<String, Course> entry : courseMap.entrySet()) {
                System.out.println(entry.getValue().toString());
            }
        }
    }

    private static void enrollStudentInCourse(HashMap<String, Student> studentMap, HashMap<String, Course> courseMap,
            Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentMap.get(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        Course course = courseMap.get(courseId);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (course.getEnrolledStudents().size() >= course.getMaxCapacity()) {
            System.out.println("Course is full. Cannot enroll more students.");
            return;
        }

        if (student.getEnrolledCourses().contains(courseId)) {
            System.out.println("Student is already enrolled in this course.");
            return;
        }

        student.getEnrolledCourses().add(courseId);
        course.getEnrolledStudents().add(studentId);
        System.out.println("Student enrolled in the course successfully.");
    }

    private static void generateStudentReport(HashMap<String, Student> studentMap, HashMap<String, Course> courseMap,
            Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentMap.get(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student Details:");
        System.out.println(student);

        Set<String> enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            System.out.println("Student is not enrolled in any courses.");
        } else {
            System.out.println("Enrolled Courses:");
            for (String courseId : enrolledCourses) {
                Course course = courseMap.get(courseId);
                if (course != null) {
                    System.out.println(course.toString());
                }
            }
        }
    }
}