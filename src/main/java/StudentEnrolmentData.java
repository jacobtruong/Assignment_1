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

    @Override
    public boolean add(Student student, Course course, String semester) {
        return false;
    }

    @Override
    public boolean update(Student student, Course course, String semester) {
        return false;
    }

    @Override
    public boolean delete(Student student, Course course, String semester) {
        return false;
    }

    @Override
    public StudentEnrolment getOne(Student student, Course course, String semester) {
        return null;
    }

    @Override
    public StudentEnrolment[] getAll() {
        return new StudentEnrolment[0];
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