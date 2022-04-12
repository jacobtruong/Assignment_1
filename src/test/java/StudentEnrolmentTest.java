import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

// Running all tests will create 3 .csv files in \\src\\main\\java
class TestMethodsInMain {
    InputStream sysInBackup = System.in;

    @Test
    public void test1_enrollStudentWithNewStudent() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101163\n2022A\ncosc4030".getBytes()));
        assertTrue(Main.enrollStudent(list));

        System.setIn(sysInBackup);
    }

    @Test
    public void test2_enrollStudentWithExistingStudent() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101163\n2021A\ncosc3321".getBytes()));
        assertFalse(Main.enrollStudent(list));

        System.setIn(sysInBackup);
    }

    @Test
    public void test3_updateStudentAddWithNewCourse() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101312\n2021A\n1\ncosc4030".getBytes()));
        assertTrue(Main.updateStudent(list));

        System.setIn(sysInBackup);
    }

    @Test
    public void test4_updateStudentAddWithExistingCourse() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101312\n2021A\n1\nphys1230".getBytes()));
        assertFalse(Main.updateStudent(list));

        System.setIn(sysInBackup);
    }

    @Test
    public void test5_updateStudentDeleteWhenNoCourses() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101312\n2022A\n2".getBytes()));
        assertFalse(Main.updateStudent(list));

        System.setIn(sysInBackup);
    }

    @Test
    public void test6_updateStudentDeleteWithValidCourse() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101312\n2021A\n2\nPHYS1230".getBytes()));
        assertTrue(Main.updateStudent(list));

        System.setIn(sysInBackup);
    }

    @Test
    // After this test, a "All courses enrolled by S101312 in 2021A.csv" file should be created in ..\\src\\main\\java
    public void test7_printAll1WithCSVOutput() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101312\n2021A\nY".getBytes()));

        Main.printAll(list, 1);

        System.setIn(sysInBackup);
    }

    @Test
    public void test8_printAll1WithoutCSVOutput() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("s101312\n2021A\nN".getBytes()));

        Main.printAll(list, 1);

        System.setIn(sysInBackup);
    }

    @Test
    // After this test, a "All students in COSC3321 in 2021A.csv" file should be created in ..\\src\\main\\java
    public void test9_printAll2WithCSVOutput() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("COSC3321\n2021A\nY".getBytes()));

        Main.printAll(list, 2);

        System.setIn(sysInBackup);
    }

    @Test
    public void test10_printAll2WithoutCSVOutput() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("COSC3321\n2021A\nN".getBytes()));

        Main.printAll(list, 2);

        System.setIn(sysInBackup);
    }

    @Test
    // After this test, a "All courses in 2021A.csv" file should be created in ..\\src\\main\\java
    public void test11_printAll3WithCSVOutput() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("2021A\nY".getBytes()));

        Main.printAll(list, 3);

        System.setIn(sysInBackup);
    }

    @Test
    public void test12_printAll3WithoutCSVOutput() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        System.setIn(new ByteArrayInputStream("2021A\nN".getBytes()));

        Main.printAll(list, 3);

        System.setIn(sysInBackup);
    }
}

class TestMethodsInStudentEnrolmentData {
    InputStream sysInBackup = System.in;

    @Test
    public void test1_checkIfInt() {
        assertTrue(StudentEnrolmentData.checkIfInt("123"));
        assertFalse(StudentEnrolmentData.checkIfInt("123.1"));
        assertFalse(StudentEnrolmentData.checkIfInt("a"));
        assertFalse(StudentEnrolmentData.checkIfInt("abc"));
    }

    @Test
    public void test2_add() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2022A";

        assertTrue(list.add(student, course, semester));
        assertFalse(list.add(student, course, semester));
    }

    @Test
    public void test2_updateInvalidEnrolment() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2022A";

        assertFalse(list.update(student, course, semester));
    }

    @Test
    public void test3_updateToExistingEnrolment() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        System.setIn(new ByteArrayInputStream("1\nS102732".getBytes()));

        assertFalse(list.update(student, course, semester));

        System.setIn(sysInBackup);
    }

    @Test
    public void test4_updateStudent() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        System.setIn(new ByteArrayInputStream("1\nS103192".getBytes()));

        assertTrue(list.update(student, course, semester));

        System.setIn(sysInBackup);
    }


    @Test
    public void test5_updateCourse() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        System.setIn(new ByteArrayInputStream("2\nPHYS1230".getBytes()));

        assertTrue(list.update(student, course, semester));

        System.setIn(sysInBackup);
    }

    @Test
    public void test6_updateSemester() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        System.setIn(new ByteArrayInputStream("3\n2021A".getBytes()));

        assertTrue(list.update(student, course, semester));

        System.setIn(sysInBackup);
    }

    @Test
    public void test7_updateAll() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        System.setIn(new ByteArrayInputStream("4\nS101163\nPHYS1230\n2022A".getBytes()));

        assertTrue(list.update(student, course, semester));

        System.setIn(sysInBackup);
    }

    @Test
    public void test8_delete() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        assertTrue(list.delete(student, course, semester));
        assertFalse(list.delete(student, course, "2022A"));
    }

    @Test
    public void test9_getOne() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();
        Student student = list.getAllStudents().get("S101312");
        Course course = list.getAllCourses().get("COSC4030");
        String semester = "2020C";

        assertEquals(new StudentEnrolment(student, course, semester), list.getOne(student, course, semester));
    }

    @Test
    public void test10_getAll() throws IOException {
        StudentEnrolmentData list = new StudentEnrolmentData();

        StudentEnrolment[] arrList = new StudentEnrolment[list.getListOfEnrolments().size()];
        list.getListOfEnrolments().toArray(arrList);

        StudentEnrolment[] arrList2 = list.getAll();


        for (int i = 0; i < list.getListOfEnrolments().size(); i++) {
            assertEquals(arrList[i], arrList2[i]);
        }
    }
}