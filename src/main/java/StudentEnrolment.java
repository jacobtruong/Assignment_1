import java.io.*;
import java.util.*;

public class StudentEnrolment implements StudentEnrolmentManager {
    private Student student;
    private Course course;
    private String semester;
    private HashMap<String, Student> listOfStudents = getAllStudents();
    private HashMap<String, Course> listOfCourses = getAllCourses();
    private ArrayList<String> listOfEnrolments = getAllEnrolments();

    public StudentEnrolment() throws IOException {
        student = null;
        course = null;
        semester = "";
    }

    public StudentEnrolment(Student student, Course course, String semester) throws IOException {
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    public String studentInput() {
        Scanner sc = new Scanner(System.in);
        String tmp_input;

        do {
            System.out.print("Please input valid/existing student's ID: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (!listOfStudents.containsKey(tmp_input));

        return tmp_input;
    }

    public String courseInput() {
        Scanner sc = new Scanner(System.in);
        String tmp_input;

        do {
            System.out.print("Please input valid/existing course: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (!listOfCourses.containsKey(tmp_input));

        return tmp_input;
    }

    public String semesterInput() {
        Scanner sc = new Scanner(System.in);
        String tmp_input;

        do {
            System.out.print("Please input valid semester: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (tmp_input.length() != 5 || !checkIfInt(tmp_input.substring(0,4)) || Integer.parseInt(tmp_input.substring(0,4)) < 2000 || (tmp_input.charAt(4) != 'A' && tmp_input.charAt(4) != 'B' && tmp_input.charAt(4) != 'C'));

        return tmp_input;
    }

    @Override
    public boolean add() throws IOException {
        String tmp_input;

        tmp_input = studentInput();

        Student tmp_student = listOfStudents.get(tmp_input);

        tmp_input = semesterInput();

        return add(tmp_student, tmp_input);
    }

    public boolean add(Student student, String semester) throws IOException {
        String tmp_input;

        tmp_input = courseInput();

        Course tmp_course = listOfCourses.get(tmp_input);

        if (listOfEnrolments.contains((String.format("%s,%s,%s", student.toString(), tmp_course.toString(), semester)))) {
            System.out.println("This enrolment has already existed!\n");
            return false;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\default.csv", true));
        bw.write(String.format("\n%s,%s,%s", student.toString(), tmp_course.toString(), semester));
        bw.close();

        System.out.printf("\nEnrolment:\n     %s,%s,%s\nHas been successfully added to the enrolment list!\n\n", student.toString(), tmp_course.toString(), semester);

        // Update the lists of things again
        listOfStudents = getAllStudents();
        listOfCourses = getAllCourses();
        listOfEnrolments = getAllEnrolments();

        return true;
    }

    @Override
    public boolean update() throws IOException {
        Scanner sc = new Scanner(System.in);
        String tmp_input, semester;
        boolean exist = false;

        tmp_input = studentInput();

        Student tmp_student = listOfStudents.get(tmp_input);

        semester = semesterInput();

        for (String s : listOfEnrolments) {
            String[] parts = s.split(",");
            if (Objects.equals(parts[0], tmp_input) && Objects.equals(parts[6], semester)) {
                exist = true;
                System.out.println(s);
            }
        }

        System.out.println("\nDo you want to:");
        System.out.println("     1. Add new enrolment (course) for this student");
        System.out.println("     2. Delete existing enrolment (course) for this student");
        do {
            System.out.print("Please select valid option: ");
            tmp_input = sc.nextLine();
        } while (!Objects.equals(tmp_input, "1") && !Objects.equals(tmp_input, "2"));

        if (tmp_input.equals("2") && !exist) {
            System.out.printf("There are no existing courses for student %s in semester %s", tmp_student.getId());
        }

        if (tmp_input.equals("1")) {
            return add(tmp_student, semester);
        } else if (tmp_input.equals("2")) {
            return delete(tmp_student, semester);
        }

        return false;
    }

    @Override
    public boolean delete() throws IOException {
        String tmp_input;

        tmp_input = studentInput();

        Student tmp_student = listOfStudents.get(tmp_input);

        tmp_input = semesterInput();

        return delete(tmp_student, tmp_input);
    }

    public boolean delete(Student student, String semester) throws IOException {
        String tmp_input;
        Course tmp_course;

        do {
            tmp_input = courseInput();

            tmp_course = listOfCourses.get(tmp_input);

            if (!listOfEnrolments.contains(String.format("%s,%s,%s", student.toString(), tmp_course.toString(), semester))) {
                System.out.println("\nINVALID ENROLMENT! PLEASE RETRY");
            }

        } while (!listOfEnrolments.contains(String.format("%s,%s,%s", student.toString(), tmp_course.toString(), semester)));

        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\default.csv"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\default2.csv", false));

        String line;
        int count = 0;

        while ((line = br.readLine()) != null) {
            if (line.equals(String.format("%s,%s,%s", student.toString(), tmp_course.toString(), tmp_input))) {
                continue;
            }
            bw.write(line);

            if (count == listOfEnrolments.size() - 2) {
                continue;
            }

            bw.write("\n");
            count++;
        }

        br.close();
        bw.close();

        File file = new File(System.getProperty("user.dir") + "\\src\\default.csv");
        file.delete();

        File file2 = new File(System.getProperty("user.dir") + "\\src\\default2.csv");
        file2.renameTo(file);

        System.out.printf("\nThe following enrolment:\n     %s,%s,%s\nHas been successfully removed from the enrolment list!\n\n", student.toString(), tmp_course.toString(), tmp_input);

        // Update the lists of things again
        listOfStudents = getAllStudents();
        listOfCourses = getAllCourses();
        listOfEnrolments = getAllEnrolments();

        return true;
    }

    @Override
    // For getOne(), it is a bit unclear what is expected to be returned or printed, therefore the function basically asks for student ID, course, and semester and return the full enrolment info (if it exists)
    public void getOne() {
        boolean check = false;
        String student, course, semester;

        student = studentInput();
        course = courseInput();
        semester = semesterInput();

        for (String s : listOfEnrolments) {
            String[] parts = s.split(",");
            if (Objects.equals(parts[0], student) && Objects.equals(parts[3], course) && Objects.equals(parts[6], semester)) {
                System.out.println("Enrolment found: " + s);
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("There are no enrolment that matches search criteria");
        }

        System.out.println();
    }

    @Override
    public void getAll(int n) throws IOException {
        Scanner sc = new Scanner(System.in);
        Set<String> set = new HashSet<>();
        String choice;
        // If n = 1: Print all courses for 1 student in 1 semester
        // If n = 2: Print all students of 1 course in 1 semester
        // If n = 3: Prints all courses offered in 1 semester


        if (n == 1) {
            String student, semester;

            student = studentInput();

            semester = semesterInput();

            System.out.printf("\nAll courses enrolled by student %s in semester %s:\n", student, semester);

            for (String s : listOfEnrolments) {
                String[] parts = s.split(",");
                if (parts[0].equals(student) && parts[6].equals(semester)) {
                    System.out.printf("%s,%s,%s\n", parts[3], parts[4], parts[5]);
                    set.add(String.format("%s,%s,%s", parts[3], parts[4], parts[5]));
                }
            }

            System.out.println();

            System.out.print("Do you want to save data to a .csv file?\n");

            do {
                System.out.printf("Your choice (Y/N): ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s\\src\\All courses enrolled by %s in %s.csv",System.getProperty("user.dir"), student, semester)));

                for (String s : set) {
                    bw.write(String.format("%s\n",s));
                }

                bw.close();

                System.out.println("Data successfully saved to .csv file!");
            }

            System.out.println();

        } else if (n == 2) {
            String course, semester;

            course = courseInput();

            semester = semesterInput();

            System.out.printf("\nAll students enrolled in course %s in semester %s:\n", course, semester);

            for (String s : listOfEnrolments) {
                String[] parts = s.split(",");
                if (parts[3].equals(course) && parts[6].equals(semester)) {
                    System.out.printf("%s,%s,%s\n", parts[0], parts[1], parts[2]);
                    set.add(String.format("%s,%s,%s", parts[0], parts[1], parts[2]));
                }
            }

            System.out.println();

            System.out.print("Do you want to save data to a .csv file?\n");

            do {
                System.out.printf("Your choice (Y/N): ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s\\src\\All students in %s in %s.csv",System.getProperty("user.dir"), course, semester)));

                for (String s : set) {
                    bw.write(String.format("%s\n",s));
                }

                bw.close();

                System.out.println("Data successfully saved to .csv file!");
            }

            System.out.println();

        } else if (n == 3) {
            String semester;

            semester = semesterInput();

            System.out.printf("\nAll courses in semester %s:\n", semester);

            for (String s : listOfEnrolments) {
                String[] parts = s.split(",");
                if (parts[6].equals(semester)) {
                    set.add(String.format("%s,%s,%s", parts[3], parts[4], parts[5]));
                }
            }

            for (String c : set) {
                System.out.println(c);
            }

            System.out.println();

            System.out.print("Do you want to save data to a .csv file?\n");

            do {
                System.out.printf("Your choice (Y/N): ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s\\src\\All courses in %s.csv",System.getProperty("user.dir"), semester)));

                for (String s : set) {
                    bw.write(String.format("%s\n",s));
                }

                bw.close();

                System.out.println("Data successfully saved to .csv file!");
            }

            System.out.println();
        }

    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", student.toString(), course.toString(),semester);
    }
}

interface StudentEnrolmentManager {
    default ArrayList<String> getAllEnrolments() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\default.csv"));
        ArrayList<String> listOfEnrolments = new ArrayList<>();

        String line;

        while ((line = br.readLine()) != null) {
            listOfEnrolments.add(line);
        }

        br.close();

        return listOfEnrolments;
    }


    default HashMap<String, Student> getAllStudents() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\default.csv"));
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
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\default.csv"));
        HashMap<String, Course> listOfCourses = new HashMap<>();

        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            listOfCourses.put(parts[3], new Course(parts[3], parts[4], Integer.parseInt(parts[5])));
        }

        br.close();

        return listOfCourses;
    }


    boolean add() throws IOException;
    boolean update() throws IOException;
    boolean delete() throws IOException;
    void getOne();
    void getAll(int n) throws IOException;
}