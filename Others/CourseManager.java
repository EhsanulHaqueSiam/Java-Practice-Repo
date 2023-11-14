import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @param creditCount Lec-Com-Sci-Lan-Stu
 */
record Course(String courseCode, String courseName, int[] creditCount, List<String> prerequisites,
              boolean major) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}

class TeeOutputStream extends java.io.OutputStream {
    private final java.io.OutputStream branch1;
    private final java.io.OutputStream branch2;

    public TeeOutputStream(java.io.OutputStream branch1, java.io.OutputStream branch2) {
        this.branch1 = branch1;
        this.branch2 = branch2;
    }

    @Override
    public void write(int b) throws java.io.IOException {
        branch1.write(b);
        branch2.write(b);
    }
}

public class CourseManager {
    public static void main(String[] args) {
        List<Course> courses = loadCoursesFromFile(); // Load courses from file or create a new list
//        List<Course> courses =createHardcodedCourses();
        System.out.println("             -------------------Welcome to the AIUB Pre-requisite solution----------------------\n\n\n");

        // Print course list with serial numbers
        System.out.println("Courses:");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println("                                " + (i + 1) + " : " + course.courseName() + " (" + course.courseCode() + ")");
        }
        System.out.println("\n\n\n       Please follow the instructions carefully\n");

        // Take user input for ID and courses taken
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID (e.g., 22-XXXXX-X): ");
        String studentId = getInputWithValidation(scanner, "\\d{2}-\\d{5}-\\d{1}");

        System.out.println("Note : Enter the serial of the courses one by one & invalid serial may lead to false result\n");
        System.out.println("Enter the numbers of the courses you have taken (separated by spaces):");
        String input = scanner.nextLine();

        int creditCompleted = 0;

        // Calculate total credits for the courses selected by the user
        for (String courseNumber : input.split(" ")) {
            int index = Integer.parseInt(courseNumber) - 1;
            creditCompleted += courses.get(index).creditCount()[0];
        }


        // Process user input and display available courses
        List<Course> availableCourses = getAvailableCourses(courses, input);

        try {
            // Save the current System.out
            PrintStream originalOut = System.out;

            // Create a FileOutputStream to write to the file
            FileOutputStream fileOutputStream = new FileOutputStream(studentId + ".txt");

            // Create a new PrintStream that writes to both the console and the file
            PrintStream combinedPrintStream = new PrintStream(new TeeOutputStream(originalOut, fileOutputStream));

            // Set the System.out to the combinedPrintStream
            System.setOut(combinedPrintStream);


        System.out.println("----------------------------------------------------------------------------------------------");

        System.out.println("       You Completed " + creditCompleted + " Credits.");
        System.out.println("       " + (148 - creditCompleted) + " More To Go .");
        System.out.println("       Best Of luck\n");
        if (availableCourses.isEmpty()) {
            System.out.println("            No Available Courses");
        } else {


            System.out.println("       Available Courses : ");
            System.out.println("       Credit (Lec-Com-Sci-Lan-Stu) ");


            List<String> infoSysMajorCourseCodes = Arrays.asList("CSC4181", "MIS3101", "MIS4011", "CSC4285", "CSC4182", "MIS4014", "CSC4180", "CSC4183", "MIS4007", "MIS4012");
            List<String> softwareEngMajorCourseCodes = Arrays.asList("CSC4270", "CSC4160", "CSC4271", "CSC4162", "CSC4274", "CSC4163", "CSC4164", "CSC4161", "CSC4272", "CSC4273");
            List<String> compTheoryMajorCourseCodes = Arrays.asList("CSC4125", "CSC4126", "CSC4127", "CSC4233", "CSC4128", "CSC4231", "CSC4232");
            List<String> compEngMajorCourseCodes = Arrays.asList("BAE1201", "EEE3103", "EEE4217", "EEE2213", "COE4128", "COE4231", "COE4129", "COE4230", "COE4126", "COE4234", "COE4232", "COE4125", "EEE4233");

            boolean infoSysMajorPrinted = false;
            boolean softwareEngMajorPrinted = false;
            boolean compTheoryMajorPrinted = false;
            boolean compEngMajorPrinted = false;

            int availableCourseCounter = 1;
            for (Course course : availableCourses) {
                if (course.major()) {
                    if (infoSysMajorCourseCodes.contains(course.courseCode()) && !infoSysMajorPrinted) {
                        System.out.println("\n       Major in Information Systems :");
                        infoSysMajorPrinted = true;
                    } else if (softwareEngMajorCourseCodes.contains(course.courseCode()) && !softwareEngMajorPrinted) {
                        System.out.println("\n       Major in Software Engineering :");
                        softwareEngMajorPrinted = true;
                    } else if (compTheoryMajorCourseCodes.contains(course.courseCode()) && !compTheoryMajorPrinted) {
                        System.out.println("\n       Major in Computational Theory :");
                        compTheoryMajorPrinted = true;
                    } else if (compEngMajorCourseCodes.contains(course.courseCode()) && !compEngMajorPrinted) {
                        System.out.println("\n       Major in Computer Engineering :");
                        compEngMajorPrinted = true;
                    }
                }

                System.out.println("\n                    " + (availableCourseCounter++) + " : " + "       " + course.courseName() + " (" + course.courseCode() + ")");
                System.out.println("                                " + Arrays.toString(course.creditCount()));
            }
        }

            // Close the combinedPrintStream and restore the original System.out
            combinedPrintStream.close();
            System.setOut(originalOut);

            System.out.println("Output saved to " + studentId + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Serialize the updated course list and save it to a file
        saveCoursesToFile(courses);


    }

    private static List<Course> createHardcodedCourses() {
        List<Course> courses = new ArrayList<>();

        // Hardcoded courses
        // Semester 1
        Course math1 = new Course("MAT1102", "DIFFERENTIAL CALCULUS & CO-ORDINATE GEOMETRY", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), false);
        Course phy1 = new Course("PHY1101", "PHYSICS 1", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), false);
        Course phy1Lab = new Course("PHY1102", "PHYSICS 1 LAB", new int[]{1, 0, 1, 0, 0}, new ArrayList<>(), false);
        Course eng1 = new Course("ENG1101", "ENGLISH READING SKILLS & PUBLIC SPEAKING", new int[]{3, 0, 0, 1, 0}, new ArrayList<>(), false);
        Course ics = new Course("CSC1101", "INTRODUCTION TO COMPUTER STUDIES", new int[]{1, 1, 0, 0, 0}, new ArrayList<>(), false);
        Course ipLab = new Course("CSC1103", "INTRODUCTION TO PROGRAMMING LAB", new int[]{1, 1, 0, 0, 0}, new ArrayList<>(), false);
        Course ip = new Course("CSC1102", "INTRODUCTION TO PROGRAMMING", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), false);

        // Semester 2
        Course dm = new Course("CSC1204", "DISCRETE MATHEMATICS", new int[]{3, 0, 0, 0, 0}, Arrays.asList("MAT1102", "CSC1102"), false);
        Course math2 = new Course("MAT1205", "INTEGRAL CALCULUS & ORDINARY DIFFERENTIAL EQUATIONS", new int[]{3, 0, 0, 0, 0}, List.of("MAT1102"), false);
        Course oop1 = new Course("CSC1205", "OBJECT ORIENTED PROGRAMMING 1", new int[]{3, 1, 0, 0, 0}, Arrays.asList("CSC1102", "CSC1103"), false);
        Course phy2 = new Course("PHY1203", "PHYSICS 2", new int[]{3, 0, 0, 0, 0}, List.of("PHY1101"), false);
        Course phy2Lab = new Course("PHY1204", "PHYSICS 2 LAB", new int[]{1, 0, 1, 0, 0}, List.of("PHY1102"), false);
        Course eng2 = new Course("ENG1202", "ENGLISH WRITING SKILLS & COMMUNICATIONS", new int[]{3, 0, 0, 1, 0}, List.of("ENG1101"), false);
        Course iec = new Course("COE2101", "INTRODUCTION TO ELECTRICAL CIRCUITS", new int[]{3, 0, 0, 0, 0}, List.of("PHY1101"), false);
        Course iecLab = new Course("COE2102", "INTRODUCTION TO ELECTRICAL CIRCUITS LAB", new int[]{1, 0, 1, 0, 0}, List.of("PHY1102"), false);

        // Semester 3
        Course chem = new Course("CHEM1101", "CHEMISTRY", new int[]{3, 0, 1, 0, 0}, List.of("PHY1203"), false);
        Course math3 = new Course("MAT2101", "COMPLEX VARIABLE,LAPLACE & Z-TRANSFORMATION", new int[]{3, 0, 0, 0, 0}, List.of("MAT1205"), false);
        Course db = new Course("CSC2108", "INTRODUCTION TO DATABASE", new int[]{3, 1, 0, 0, 0}, List.of("CSC1205"), false);
        Course deviceLab = new Course("EEE2104", "ELECTRONIC DEVICES LAB", new int[]{1, 0, 1, 0, 0}, List.of("COE2102"), false);
        Course accounting = new Course("BBA1102", "PRINCIPLES OF ACCOUNTING", new int[]{3, 0, 0, 0, 0}, List.of("MAT1205"), false);
        Course device = new Course("EEE2103", "ELECTRONIC DEVICES", new int[]{3, 0, 0, 0, 0}, List.of("COE2101"), false);
        Course ds = new Course("CSC2106", "DATA STRUCTURE", new int[]{3, 0, 0, 0, 0}, Arrays.asList("CSC1204", "CSC1205"), false);
        Course dsLab = new Course("CSC2107", "DATA STRUCTURE LAB", new int[]{1, 1, 0, 0, 0}, Arrays.asList("CSC2106", "CSC2108"), false);
        Course cad = new Course("BAE2101", "COMPUTER AIDED DESIGN & DRAFTING", new int[]{1, 1, 0, 0, 0}, new ArrayList<>(), false);

        // Semester 4
        Course algo = new Course("CSC2211", "ALGORITHMS", new int[]{3, 1, 0, 0, 0}, List.of("CSC2106"), false);
        Course math4 = new Course("MAT2202", "MATRICES, VECTORS, FOURIER ANALYSIS", new int[]{3, 0, 0, 0, 0}, List.of("MAT2101"), false);
        Course oop2 = new Course("CSC2210", "OBJECT ORIENTED PROGRAMMING 2", new int[]{3, 1, 0, 0, 0}, Arrays.asList("CSC2106", "CSC2108"), false);
        Course ooad = new Course("CSC2209", "OBJECT ORIENTED ANALYSIS AND DESIGN", new int[]{3, 0, 0, 0, 0}, List.of("CSC2108"), false);
        Course bs = new Course("BAS2101", "BANGLADESH STUDIES", new int[]{3, 0, 0, 0, 0}, List.of("CSC1101"), false);
        Course dlc = new Course("EEE3101", "DIGITAL LOGIC AND CIRCUITS", new int[]{3, 0, 0, 0, 0}, List.of("EEE2103"), false);
        Course dlcLab = new Course("EEE3102", "DIGITAL LOGIC AND CIRCUITS LAB", new int[]{1, 0, 1, 0, 0}, List.of("EEE2104"), false);
        Course math6 = new Course("MAT3103", "COMPUTATIONAL STATISTICS AND PROBABILITY", new int[]{3, 0, 0, 0, 0}, List.of("MAT2101"), false);

        // Semester 5
        Course comp = new Course("CSC3113", "THEORY OF COMPUTATION", new int[]{3, 0, 0, 0, 0}, List.of("CSC2211"), false);
        Course eco = new Course("ECO3150", "PRINCIPLES OF ECONOMICS", new int[]{2, 0, 0, 0, 0}, List.of("MAT3103"), false);
        Course bus = new Course("ENG2103", "BUSINESS COMMUNICATION", new int[]{3, 0, 0, 0, 0}, List.of("BAS2101"), false);
        Course math5 = new Course("MAT3101", "NUMERICAL METHODS FOR SCIENCE AND ENGINEERING", new int[]{3, 0, 0, 0, 0}, List.of("MAT2202"), false);
        Course comm = new Course("COE3103", "DATA COMMUNICATION", new int[]{3, 0, 1, 0, 0}, Arrays.asList("EEE3101", "EEE3102"), false);
        Course micro = new Course("COE3104", "MICROPROCESSOR AND EMBEDDED SYSTEMS", new int[]{3, 0, 1, 0, 0}, Arrays.asList("EEE3101", "EEE3102"), false);
        Course soft = new Course("CSC3112", "SOFTWARE ENGINEERING", new int[]{3, 1, 0, 0, 0}, List.of("CSC2209"), false);

        // Semester 6
        Course aies = new Course("CSC3217", "ARTIFICIAL INTELLIGENCE AND EXPERT SYSTEM", new int[]{3, 1, 0, 0, 0}, Arrays.asList("CSC2211", "MAT3103"), false);
        Course net = new Course("COE3206", "COMPUTER NETWORKS", new int[]{3, 0, 1, 0, 0}, List.of("COE3103"), false);
        Course coa = new Course("COE3205", "COMPUTER ORGANIZATION AND ARCHITECTURE", new int[]{3, 1, 0, 0, 0}, List.of("COE3104"), false);
        Course os = new Course("CSC3214", "OPERATING SYSTEM", new int[]{3, 1, 0, 0, 0}, Arrays.asList("CSC2211", "COE3104"), false);
        Course web = new Course("CSC3215", "WEB TECHNOLOGIES", new int[]{3, 1, 0, 0, 0}, List.of("CSC3112"), false);
        Course ethics = new Course("EEE2216", "ENGINEERING ETHICS", new int[]{2, 0, 0, 0, 0}, Arrays.asList("CSC3112", "COE3104"), false);
        Course design = new Course("CSC3216", "COMPILER DESIGN", new int[]{3, 1, 0, 0, 0}, List.of("CSC3113"), false);

        // Semester 7
        Course graphics = new Course("CSC4118", "COMPUTER GRAPHICS", new int[]{3, 1, 0, 0, 0}, Arrays.asList("CSC2211", "MAT2202"), false);
        Course management = new Course("MGT3202", "ENGINEERING MANAGEMENT", new int[]{3, 0, 0, 0, 0}, List.of("EEE2216"), false);
        Course research = new Course("CSC4197", "RESEARCH METHODOLOGY", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), false);

        // Semester 8
        Course thesis = new Course("CSC4299", "THESIS", new int[]{3, 0, 0, 0, 0}, List.of("CSC4197"), false);
        Course intern = new Course("CSC4296", "INTERNSHIP", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), false);

        // Major in Information Systems
        Course advDatabase = new Course("CSC4181", "ADVANCE DATABASE MANAGEMENT SYSTEM", new int[]{3, 1, 0, 0, 0}, List.of("CSC2108"), true);
        Course infoSysManagement = new Course("MIS3101", "MANAGEMENT INFORMATION SYSTEM", new int[]{3, 0, 0, 0, 0}, List.of("CSC3112"), true);
        Course enterpriseResource = new Course("MIS4011", "ENTERPRISE RESOURCE PLANNING", new int[]{3, 0, 0, 0, 0}, Arrays.asList("MIS3101", "CSC3112"), true);
        Course dataWarehouse = new Course("CSC4285", "DATA WAREHOUSE AND DATA MINING", new int[]{3, 0, 0, 0, 0}, Arrays.asList("CSC2211", "MAT3103"), true);
        Course hci = new Course("CSC4182", "HUMAN COMPUTER INTERACTION", new int[]{3, 0, 0, 0, 0}, Arrays.asList("CSC3217", "CSC3215"), true);
        Course businessIntelligence = new Course("MIS4014", "BUSINESS INTELLIGENCE AND DECISION SUPPORT SYSTEMS", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), true);
        Course introToDataScience = new Course("CSC4180", "INTRODUCTION TO DATA SCIENCE", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), true);
        Course cyberLaws = new Course("CSC4183", "CYBER LAWS & INFORMATION SECURITY", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), true);
        Course digitalMarketing = new Course("MIS4007", "DIGITAL MARKETING", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), true);
        Course eSeries = new Course("MIS4012", "E-COMMERCE, E-GOVERNANCE & E-SERIES", new int[]{3, 0, 0, 0, 0}, new ArrayList<>(), true);

        // Major in Software Engineering
        Course projectManagement = new Course("CSC4270", "SOFTWARE DEVELOPMENT PROJECT MANAGEMENT", new int[]{3, 0, 0, 0, 0}, List.of("CSC3112"), true);
        Course requirementEngineering = new Course("CSC4160", "SOFTWARE REQUIREMENT ENGINEERING", new int[]{3, 0, 0, 0, 0}, List.of("CSC3112"), true);
        Course qualityAndTesting = new Course("CSC4271", "SOFTWARE QUALITY AND TESTING", new int[]{3, 0, 0, 0, 0}, List.of("CSC3112"), true);
        Course pythonProgramming = new Course("CSC4162", "PROGRAMMING IN PYTHON", new int[]{3, 1, 0, 0, 0}, List.of("CSC3215"), true);
        Course vrSystemsDesign = new Course("CSC4274", "VIRTUAL REALITY SYSTEMS DESIGN", new int[]{3, 0, 0, 0, 0}, List.of("CSC2210"), true);
        Course javaProgramming = new Course("CSC4163", "ADVANCED PROGRAMMING WITH JAVA", new int[]{3, 1, 0, 0, 0}, List.of("CSC3215"), true);
        Course dotNetProgramming = new Course("CSC4164", "ADVANCED PROGRAMMING WITH .NET", new int[]{3, 1, 0, 0, 0}, List.of("CSC3215"), true);
        Course webTechProgramming = new Course("CSC4161", "ADVANCED PROGRAMMING IN WEB TECHNOLOGY", new int[]{3, 1, 0, 0, 0}, List.of("CSC3215"), true);
        Course mobileAppDevelopment = new Course("CSC4272", "MOBILE APPLICATION DEVELOPMENT", new int[]{3, 1, 0, 0, 0}, List.of("CSC3215"), true);
        Course architectureAndDesignPatterns = new Course("CSC4273", "SOFTWARE ARCHITECTURE AND DESIGN PATTERNS", new int[]{3, 0, 0, 0, 0}, List.of("CSC3112"), true);

        // Major in Computational Theory
        Course csMathematics = new Course("CSC4125", "COMPUTER SCIENCE MATHEMATICS", new int[]{3, 0, 0, 0, 0}, Arrays.asList("CSC2211", "MAT3101"), true);
        Course basicGraphTheory = new Course("CSC4126", "BASIC GRAPH THEORY", new int[]{3, 0, 0, 0, 0}, List.of("CSC2211"), true);
        Course algorithmTechniques = new Course("CSC4127", "ADVANCED ALGORITHM TECHNIQUES", new int[]{3, 1, 0, 0, 0}, List.of("CSC3217"), true);
        Course nlp = new Course("CSC4233", "NATURAL LANGUAGE PROCESSING", new int[]{3, 0, 0, 0, 0}, Arrays.asList("CSC3217", "CSC4162"), true);
        Course linearProgramming = new Course("CSC4128", "LINEAR PROGRAMMING", new int[]{3, 1, 0, 0, 0}, Arrays.asList("CSC3217", "MAT3103"), true);
        Course parallelComputing = new Course("CSC4231", "PARALLEL COMPUTING", new int[]{3, 0, 0, 0, 0}, List.of("CSC3217"), true);
        Course machineLearning = new Course("CSC4232", "MACHINE LEARNING", new int[]{3, 0, 0, 0, 0}, List.of("CSC3217"), true);

        // Major in Computer Engineering
        Course basicMechanicalEngg = new Course("BAE1201", "BASIC MECHANICAL ENGG", new int[]{3, 0, 0, 0, 0}, List.of("PHY1203"), true);
        Course digitalSignalProcessing = new Course("EEE3103", "DIGITAL SIGNAL PROCESSING", new int[]{3, 0, 0, 0, 0}, List.of("EEE2213"), true);
        Course vlsiCircuitDesign = new Course("EEE4217", "VLSI CIRCUIT DESIGN", new int[]{3, 0, 0, 0, 0}, Arrays.asList("EEE3101", "EEE3102"), true);
        Course signalsLinearSystem = new Course("EEE2213", "SIGNALS & LINEAR SYSTEM", new int[]{3, 0, 0, 0, 0}, List.of("MAT2202"), true);
        Course digitalSystemDesign = new Course("COE4128", "DIGITAL SYSTEM DESIGN", new int[]{3, 0, 0, 0, 0}, List.of("COE3205"), true);
        Course imageProcessing = new Course("COE4231", "IMAGE PROCESSING", new int[]{3, 0, 0, 0, 0}, Arrays.asList("CSC4118", "EEE2213"), true);
        Course multimediaSystems = new Course("COE4129", "MULTIMEDIA SYSTEMS", new int[]{3, 0, 0, 0, 0}, List.of("CSC3215"), true);
        Course simulationModeling = new Course("COE4230", "SIMULATION & MODELING", new int[]{3, 1, 0, 0, 0}, List.of("CSC3217"), true);
        Course advancedComputerNetworks = new Course("COE4126", "ADVANCED COMPUTER NETWORKS", new int[]{3, 0, 1, 0, 0}, List.of("COE3206"), true);
        Course computerVisionPatternRecognition = new Course("COE4234", "COMPUTER VISION AND PATTERN RECOGNITION", new int[]{3, 0, 0, 0, 0}, List.of("CSC4118"), true);
        Course networkSecurity = new Course("COE4232", "NETWORK SECURITY", new int[]{3, 0, 0, 0, 0}, List.of("COE3103"), true);
        Course advancedOperatingSystem = new Course("COE4125", "ADVANCED OPERATING SYSTEM", new int[]{3, 1, 0, 0, 0}, List.of("CSC3214"), true);
        Course digitalDesignWithSystem = new Course("EEE4233", "DIGITAL DESIGN WITH SYSTEM [ VERILOG,VHDL & FPGAS ]", new int[]{3, 0, 0, 0, 0}, List.of("EEE4217"), true);
        Course roboticsEngineering = new Course("COE4235", "ROBOTICS ENGINEERING", new int[]{3, 0, 0, 0, 0}, List.of("CSC3217"), true);
        Course telecommunicationsEngineering = new Course("EEE4209", "TELECOMMUNICATIONS ENGINEERING", new int[]{3, 0, 0, 0, 0}, List.of("COE3103"), true);
        Course networkResourceManagementOrganization = new Course("COE4127", "NETWORK RESOURCE MANAGEMENT & ORGANIZATION", new int[]{3, 0, 0, 0, 0}, List.of("COE3103"), true);
        Course wirelessSensorNetworks = new Course("COE4233", "WIRELESS SENSOR NETWORKS", new int[]{3, 0, 1, 0, 0}, List.of("COE3103"), true);
        Course industrialElectronicsDrivesInstrumentation = new Course("EEE4241", "INDUSTRIAL ELECTRONICS, DRIVES & INSTRUMENTATION", new int[]{3, 0, 1, 0, 0}, List.of("EEE3101"), true);

        // Add courses to the list
        // Semester 1
        courses.add(math1);
        courses.add(phy1);
        courses.add(phy1Lab);
        courses.add(eng1);
        courses.add(ics);
        courses.add(ipLab);
        courses.add(ip);

        // Semester 2
        courses.add(dm);
        courses.add(math2);
        courses.add(oop1);
        courses.add(phy2);
        courses.add(phy2Lab);
        courses.add(eng2);
        courses.add(iec);
        courses.add(iecLab);

        // Semester 3
        courses.add(chem);
        courses.add(math3);
        courses.add(db);
        courses.add(deviceLab);
        courses.add(accounting);
        courses.add(device);
        courses.add(ds);
        courses.add(dsLab);
        courses.add(cad);

        // Semester 4
        courses.add(algo);
        courses.add(math4);
        courses.add(oop2);
        courses.add(ooad);
        courses.add(bs);
        courses.add(dlc);
        courses.add(dlcLab);
        courses.add(math6);

        // Semester 5
        courses.add(comp);
        courses.add(eco);
        courses.add(bus);
        courses.add(math5);
        courses.add(comm);
        courses.add(micro);
        courses.add(soft);

        // Semester 6
        courses.add(aies);
        courses.add(net);
        courses.add(coa);
        courses.add(os);
        courses.add(web);
        courses.add(ethics);
        courses.add(design);

        // Semester 7
        courses.add(graphics);
        courses.add(management);
        courses.add(research);

        // Semester 8
        courses.add(thesis);
        courses.add(intern);

        // Major in Information Systems
        courses.add(advDatabase);
        courses.add(infoSysManagement);
        courses.add(enterpriseResource);
        courses.add(dataWarehouse);
        courses.add(hci);
        courses.add(businessIntelligence);
        courses.add(introToDataScience);
        courses.add(cyberLaws);
        courses.add(digitalMarketing);
        courses.add(eSeries);

        // Major in Software Engineering
        courses.add(projectManagement);
        courses.add(requirementEngineering);
        courses.add(qualityAndTesting);
        courses.add(pythonProgramming);
        courses.add(vrSystemsDesign);
        courses.add(javaProgramming);
        courses.add(dotNetProgramming);
        courses.add(webTechProgramming);
        courses.add(mobileAppDevelopment);
        courses.add(architectureAndDesignPatterns);

        // Major in Computational Theory
        courses.add(csMathematics);
        courses.add(basicGraphTheory);
        courses.add(algorithmTechniques);
        courses.add(nlp);
        courses.add(linearProgramming);
        courses.add(parallelComputing);
        courses.add(machineLearning);

        // Major in Computer Engineering
        courses.add(basicMechanicalEngg);
        courses.add(digitalSignalProcessing);
        courses.add(vlsiCircuitDesign);
        courses.add(signalsLinearSystem);
        courses.add(digitalSystemDesign);
        courses.add(imageProcessing);
        courses.add(multimediaSystems);
        courses.add(simulationModeling);
        courses.add(advancedComputerNetworks);
        courses.add(computerVisionPatternRecognition);
        courses.add(networkSecurity);
        courses.add(advancedOperatingSystem);
        courses.add(digitalDesignWithSystem);
        courses.add(roboticsEngineering);
        courses.add(telecommunicationsEngineering);
        courses.add(networkResourceManagementOrganization);
        courses.add(wirelessSensorNetworks);
        courses.add(industrialElectronicsDrivesInstrumentation);

        return courses;
    }

    @SuppressWarnings("unchecked")
    private static List<Course> loadCoursesFromFile() {
        List<Course> courses = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("courses.ser"))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                courses = (List<Course>) obj;
            } else {
                System.out.println("Unexpected object type in courses.ser. Loading hardcoded courses.");
                courses = createHardcodedCourses();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Course file not found. Loading hardcoded courses.");
            courses = createHardcodedCourses();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return courses;
    }


    private static void saveCoursesToFile(List<Course> courses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("courses.ser"))) {
            oos.writeObject(courses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<Course> getAvailableCourses(List<Course> courses, String userInput) {
        List<Course> takenCourses = new ArrayList<>();

        int totalCredits = 0;

        if (!userInput.isEmpty()) {
            String[] takenCourseNumbers = userInput.split(" ");
            for (String courseNumber : takenCourseNumbers) {
                int index = Integer.parseInt(courseNumber) - 1;
                takenCourses.add(courses.get(index));
                totalCredits += courses.get(index).creditCount()[0];
            }
        }


        List<Course> availableCourses = new ArrayList<>();
        for (Course course : courses) {
            if (!takenCourses.contains(course) && canTakeCourse(course, takenCourses)) {
                if (course.courseCode().equals("CSC4197") && totalCredits < 100)
                    continue;
                else if (course.courseCode().equals("CSC4296") && totalCredits < 139) {
                    continue;
                }
                availableCourses.add(course);
            }
        }

        return availableCourses;
    }

    private static boolean canTakeCourse(Course course, List<Course> takenCourses) {
        if (course.prerequisites().isEmpty()) {
            return true;
        }

        for (String prerequisite : course.prerequisites()) {
            boolean hasPrerequisite = false;
            for (Course takenCourse : takenCourses) {
                if (takenCourse.courseCode().equals(prerequisite)) {
                    hasPrerequisite = true;
                    break;
                }
            }
            if (!hasPrerequisite) {
                return false;
            }
        }

        return true;
    }
    private static String getInputWithValidation(Scanner scanner, String regex) {
        String input;
        while (true) {
            System.out.print("Enter input: ");
            input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                break;
            } else {
                System.out.println("Invalid input. Please enter the correct format.");
            }
        }
        return input;
    }
}

