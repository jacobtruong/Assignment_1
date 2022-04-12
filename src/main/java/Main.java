import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static boolean enrollStudent(StudentEnrolmentData list) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-----------------------------------------------\n[Enrolling a new student into a new course]");
        String tmp_input, semester;

        do {
            System.out.print("Please input valid/existing student's ID: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (!list.getListOfStudents().containsKey(tmp_input));
        Student tmp_student = list.getListOfStudents().get(tmp_input);

        do {
            System.out.print("Please input valid semester: ");
            semester = sc.nextLine().toUpperCase();
        } while (semester.length() != 5 || !StudentEnrolmentData.checkIfInt(semester.substring(0, 4)) || Integer.parseInt(semester.substring(0, 4)) < 2000 || (semester.charAt(4) != 'A' && semester.charAt(4) != 'B' && semester.charAt(4) != 'C'));

        do {
            System.out.print("Please input valid/existing course: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (!list.getListOfCourses().containsKey(tmp_input));
        Course tmp_course = list.getListOfCourses().get(tmp_input);

        return list.add(tmp_student, tmp_course, semester);
    }

    public static boolean updateStudent(StudentEnrolmentData list) {
        System.out.println("-----------------------------------------------\n[Updating a student]");
        Scanner sc = new Scanner(System.in);
        String tmp_input, semester;
        int int_input;
        boolean exist = false;

        do {
            System.out.print("Please input valid/existing student's ID: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (!list.getListOfStudents().containsKey(tmp_input));
        Student tmp_student = list.getListOfStudents().get(tmp_input);

        do {
            System.out.print("Please input valid semester: ");
            semester = sc.nextLine().toUpperCase();
        } while (semester.length() != 5 || !StudentEnrolmentData.checkIfInt(semester.substring(0, 4)) || Integer.parseInt(semester.substring(0, 4)) < 2000 || (semester.charAt(4) != 'A' && semester.charAt(4) != 'B' && semester.charAt(4) != 'C'));

        for (StudentEnrolment s : list.getListOfEnrolments()) {
            if(s.getStudent().equals(tmp_student) && s.getSemester().equals(semester)) {
                System.out.println(s);
                exist = true;
            }
        }

        System.out.println("\nDo you want to:");

        System.out.println("     1. Add new enrolment (course) for this student");
        System.out.println("     2. Delete existing enrolment (course) for this student");
        do {
            System.out.print("Please select valid option: ");
            int_input = Integer.parseInt(sc.nextLine());
        } while (int_input != 1 && int_input != 2);

        if (int_input == 2 && !exist) {
            System.out.printf("There are no existing courses for student %s in semester %s", tmp_input, semester);
            return false;
        }

        do {
            System.out.print("Please input valid/existing course: ");
            tmp_input = sc.nextLine().toUpperCase();
        } while (!list.getListOfCourses().containsKey(tmp_input));

        Course tmp_course = list.getListOfCourses().get(tmp_input);

        if (int_input == 1) {
            return list.add(tmp_student, tmp_course, semester);
        } else {
            return list.delete(tmp_student, tmp_course, semester);
        }
    }

    public static void printAll(StudentEnrolmentData list, int n) throws IOException {
        Scanner sc = new Scanner(System.in);
        Set<String> set = new HashSet<>();
        String choice;
        // If n = 1: Print all courses for 1 student in 1 semester
        // If n = 2: Print all students of 1 course in 1 semester
        // If n = 3: Prints all courses offered in 1 semester


        if (n == 1) {
            System.out.println("-----------------------------------------------\n[Printing all courses for 1 student in 1 semester]");
            String tmp_input, semester;

            do {
                System.out.print("Please input valid/existing student's ID: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (!list.getListOfStudents().containsKey(tmp_input));
            Student student = list.getListOfStudents().get(tmp_input);

            do {
                System.out.print("Please input valid semester: ");
                semester = sc.nextLine().toUpperCase();
            } while (semester.length() != 5 || !StudentEnrolmentData.checkIfInt(semester.substring(0, 4)) || Integer.parseInt(semester.substring(0, 4)) < 2000 || (semester.charAt(4) != 'A' && semester.charAt(4) != 'B' && semester.charAt(4) != 'C'));

            System.out.printf("\nAll courses enrolled by student %s (%s) in semester %s:\n", student.getName(), student.getId(), semester);

            for (StudentEnrolment s : list.getListOfEnrolments()) {
                if (s.getStudent().equals(student) && s.getSemester().equals(semester)) {
                    System.out.println(s.getCourse());
                    set.add(s.getCourse().toString());
                }
            }

            System.out.println();

            System.out.print("Do you want to save data to a .csv file?\n");

            do {
                System.out.printf("Your choice (Y/N): ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s\\src\\main\\java\\All courses enrolled by %s in %s.csv",System.getProperty("user.dir"), student.getId(), semester)));

                for (String s : set) {
                    bw.write(String.format("%s\n",s));
                }

                bw.close();

                System.out.println("Data successfully saved to .csv file!");
            }

            System.out.println();

        } else if (n == 2) {
            System.out.println("-----------------------------------------------\n[Printing all students of 1 course in 1 semester]");
            String tmp_input, semester;

            do {
                System.out.print("Please input valid/existing course: ");
                tmp_input = sc.nextLine().toUpperCase();
            } while (!list.getListOfCourses().containsKey(tmp_input));

            Course course = list.getListOfCourses().get(tmp_input);

            do {
                System.out.print("Please input valid semester: ");
                semester = sc.nextLine().toUpperCase();
            } while (semester.length() != 5 || !StudentEnrolmentData.checkIfInt(semester.substring(0, 4)) || Integer.parseInt(semester.substring(0, 4)) < 2000 || (semester.charAt(4) != 'A' && semester.charAt(4) != 'B' && semester.charAt(4) != 'C'));


            System.out.printf("\nAll students enrolled in %s in %s:\n", course.getId(), semester);

            for (StudentEnrolment s : list.getListOfEnrolments()) {
                if (s.getCourse().equals(course) && s.getSemester().equals(semester)) {
                    System.out.println(s.getStudent());
                    set.add(s.getStudent().toString());
                }
            }

            System.out.println();

            System.out.print("Do you want to save data to a .csv file?\n");

            do {
                System.out.printf("Your choice (Y/N): ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s\\src\\main\\java\\All students in %s in %s.csv",System.getProperty("user.dir"), course.getId(), semester)));

                for (String s : set) {
                    bw.write(String.format("%s\n",s));
                }

                bw.close();

                System.out.println("Data successfully saved to .csv file!");
            }

            System.out.println();

        } else if (n == 3) {
            System.out.println("-----------------------------------------------\n[Printing all students of 1 course in 1 semester]");
            String semester;

            do {
                System.out.print("Please input valid semester: ");
                semester = sc.nextLine().toUpperCase();
            } while (semester.length() != 5 || !StudentEnrolmentData.checkIfInt(semester.substring(0, 4)) || Integer.parseInt(semester.substring(0, 4)) < 2000 || (semester.charAt(4) != 'A' && semester.charAt(4) != 'B' && semester.charAt(4) != 'C'));


            System.out.printf("\nAll courses in semester %s:\n", semester);

            for (StudentEnrolment s : list.getListOfEnrolments()) {
                if (s.getSemester().equals(semester)) {
                    set.add(s.getCourse().toString());
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
                BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s\\src\\main\\java\\All courses in %s.csv", System.getProperty("user.dir"), semester)));

                for (String s : set) {
                    bw.write(String.format("%s\n", s));
                }

                bw.close();

                System.out.println("Data successfully saved to .csv file!");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();

        // Enroll a student
        enrollStudent(list);

        // Update a student
        updateStudent(list);

        // Print all courses for 1 student in 1 semester
        printAll(list, 1);

        // If n = 2: Print all students of 1 course in 1 semester
        printAll(list, 2);

        // If n = 3: Prints all courses offered in 1 semester
        printAll(list, 3);

        // Print final StudentEnrolment list
        System.out.print("___________________________________________\nFinal StudentEnrolment list:\n");
        int k = 1;
        for (StudentEnrolment s : list.getAll()) {
            System.out.printf("%d - %s\n", k++, s);
        }
    }
}
