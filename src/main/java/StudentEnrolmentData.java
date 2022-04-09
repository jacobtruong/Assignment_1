import java.io.*;
import java.util.*;

public class StudentEnrolmentData implements StudentEnrolmentManager {
    private ArrayList<StudentEnrolment> listOfEnrolments = getAllEnrolments();
    private HashMap<String, Student> listOfStudents = getAllStudents();
    private HashMap<String, Course> listOfCourses = getAllCourses();

    public StudentEnrolmentData() throws IOException {
    }

    public ArrayList<StudentEnrolment> getListOfEnrolments() {
        return listOfEnrolments;
    }

    public HashMap<String, Student> getListOfStudents() {
        return listOfStudents;
    }

    public HashMap<String, Course> getListOfCourses() {
        return listOfCourses;
    }

    public static boolean checkIfInt(String input) {
        try {
            double isNum = Double.parseDouble(input);
            if (isNum == Math.floor(isNum)) {
//                System.out.println("Input is Integer");
                return true;
            } else {
//                System.out.println("Input is Double");
                return false;
            }
        } catch (Exception e) {
            if (input.toCharArray().length == 1) {
//                System.out.println("Input is Character");
                return false;
            } else {
//                System.out.println("Input is String");
                return false;
            }
        }
    }

    // NOTE: THESE FUNCTIONS WERE ORIGINALLY USED FOR CLEANER INPUTTING CODE, BUT THEY INTERFERE WITH UNIT TESTING
    // SIMULATED USER INPUT WHEN THERE ARE MORE THAN 1 METHOD CALLS (AS THE SECOND METHOD CALL FLUSHES THE SIMULATED INPUT)
//    public String studentInput() {
//        Scanner sc = new Scanner(System.in);
//        String tmp_input;
//
//        do {
//            System.out.print("Please input valid/existing student's ID: ");
//            tmp_input = sc.nextLine().toUpperCase();
//        } while (!listOfStudents.containsKey(tmp_input));
//
//        return tmp_input;
//    }
//
//    public String courseInput() {
//        Scanner sc = new Scanner(System.in);
//        String tmp_input;
//
//        do {
//            System.out.print("Please input valid/existing course: ");
//            tmp_input = sc.nextLine().toUpperCase();
//        } while (!listOfCourses.containsKey(tmp_input));
//
//        return tmp_input;
//    }
//
//    public String semesterInput() {
//        Scanner sc = new Scanner(System.in);
//        String tmp_input;
//
//        do {
//            System.out.print("Please input valid semester: ");
//            tmp_input = sc.nextLine().toUpperCase();
//        } while (tmp_input.length() != 5 || !checkIfInt(tmp_input.substring(0, 4)) || Integer.parseInt(tmp_input.substring(0, 4)) < 2000 || (tmp_input.charAt(4) != 'A' && tmp_input.charAt(4) != 'B' && tmp_input.charAt(4) != 'C'));
//
//        return tmp_input;
//    }

    @Override
    public boolean add(Student student, Course course, String semester) {
        StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, semester);

        if (listOfEnrolments.contains(studentEnrolment)) {
            System.out.println("This enrolment has already existed!\n");
            return false;
        }

        listOfEnrolments.add(studentEnrolment);
        System.out.printf("\nEnrolment:\n     %s\nHas been successfully added to the enrolment list!\n\n", studentEnrolment);
        return true;
    }

    @Override
    public boolean update(Student student, Course course, String semester) {
        Scanner sc = new Scanner(System.in);

        StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, semester);
        StudentEnrolment studentEnrolment_2;

        if (!listOfEnrolments.contains(studentEnrolment)) {
            System.out.println("This enrolment does not exist!\n");
            return false;
        }

        System.out.printf("How do you want to update the following enrolment?\n     %s\n", studentEnrolment);
        System.out.println("1. Update the Student");
        System.out.println("2. Update the Course");
        System.out.println("3. Update the Semester");
        System.out.println("4. Update all");
        int choice;

        do {
            System.out.print("Your choice: ");
            choice = Integer.parseInt(sc.nextLine());
        } while (choice < 1 || choice > 4);

        String tmp_input;

        if (choice == 1) {
            do {
                System.out.print("Please input valid/existing student's ID: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (!listOfStudents.containsKey(tmp_input));

            Student new_student = listOfStudents.get(tmp_input);
            studentEnrolment_2 = new StudentEnrolment(new_student, course, semester);

        } else if (choice == 2) {
            do {
                System.out.print("Please input valid/existing course: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (!listOfCourses.containsKey(tmp_input));

            Course new_course = listOfCourses.get(tmp_input);
            studentEnrolment_2 = new StudentEnrolment(student, new_course, semester);

        } else if (choice == 3) {
            do {
                System.out.print("Please input valid semester: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (tmp_input.length() != 5 || !StudentEnrolmentData.checkIfInt(tmp_input.substring(0, 4)) || Integer.parseInt(tmp_input.substring(0, 4)) < 2000 || (tmp_input.charAt(4) != 'A' && tmp_input.charAt(4) != 'B' && tmp_input.charAt(4) != 'C'));

            studentEnrolment_2 = new StudentEnrolment(student, course, tmp_input);

        } else {
            do {
                System.out.print("Please input valid/existing student's ID: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (!listOfStudents.containsKey(tmp_input));
            Student new_student = listOfStudents.get(tmp_input);

            do {
                System.out.print("Please input valid/existing course: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (!listOfCourses.containsKey(tmp_input));
            Course new_course = listOfCourses.get(tmp_input);

            do {
                System.out.print("Please input valid semester: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (tmp_input.length() != 5 || !StudentEnrolmentData.checkIfInt(tmp_input.substring(0, 4)) || Integer.parseInt(tmp_input.substring(0, 4)) < 2000 || (tmp_input.charAt(4) != 'A' && tmp_input.charAt(4) != 'B' && tmp_input.charAt(4) != 'C'));

            studentEnrolment_2 = new StudentEnrolment(new_student, new_course, tmp_input);
        }

        if (listOfEnrolments.contains(studentEnrolment_2)) {
            System.out.println("Cannot update to another existing enrolment!");
            return false;
        }

        listOfEnrolments.set(listOfEnrolments.indexOf(studentEnrolment), studentEnrolment_2);
        System.out.printf("Enrolment update successful!\n%s\nhas been updated to\n%s\n",studentEnrolment, studentEnrolment_2);

        return true;
    }

    @Override
    public boolean delete(Student student, Course course, String semester) {
        StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, semester);

        if (!listOfEnrolments.contains(studentEnrolment)) {
            System.out.println("This enrolment does not exist!\n");
            return false;
        }

        listOfEnrolments.remove(studentEnrolment);
        System.out.printf("\nEnrolment:\n     %s\nHas been successfully deleted from the enrolment list!\n\n", studentEnrolment);
        return true;
    }

    @Override
    public StudentEnrolment getOne(Student student, Course course, String semester) {
        StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, semester);

        if (!listOfEnrolments.contains(studentEnrolment)) {
            System.out.println("This enrolment does not exist!\n");
            return null;
        }

        return studentEnrolment;
    }

    @Override
    public StudentEnrolment[] getAll() {
        StudentEnrolment[] list = new StudentEnrolment[listOfEnrolments.size()];
        listOfEnrolments.toArray(list);
        return list;
    }
}

interface StudentEnrolmentManager {
    default ArrayList<StudentEnrolment> getAllEnrolments() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\default.csv"));
        ArrayList<StudentEnrolment> listOfEnrolments = new ArrayList<>();

        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            listOfEnrolments.add(new StudentEnrolment((new Student(parts[0], parts[1], parts[2])), (new Course(parts[3], parts[4], Integer.parseInt(parts[5]))), (parts[6])));
        }

        br.close();

        return listOfEnrolments;
    }


    default HashMap<String, Student> getAllStudents() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\default.csv"));
        HashMap<String, Student> listOfStudents = new HashMap<>();

        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            listOfStudents.put(parts[0], new Student(parts[0], parts[1], parts[2]));
        }

        br.close();

        return listOfStudents;
    }

    default HashMap<String, Course> getAllCourses() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\default.csv"));
        HashMap<String, Course> listOfCourses = new HashMap<>();

        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            listOfCourses.put(parts[3], new Course(parts[3], parts[4], Integer.parseInt(parts[5])));
        }

        br.close();

        return listOfCourses;
    }


    boolean add(Student student, Course course, String semester);
    boolean update(Student student, Course course, String semester);
    boolean delete(Student student, Course course, String semester);
    StudentEnrolment getOne(Student student, Course course, String semester);
    StudentEnrolment[] getAll();
}