import java.io.*;
import java.util.*;

public class StudentEnrolment {
    private Student student;
    private Course course;
    private String semester;


    public StudentEnrolment() throws IOException {
        student = null;
        course = null;
        semester = "";
    }

    public StudentEnrolment(Student student, Course course, String semester) {
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

}