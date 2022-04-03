import java.util.Objects;

public class Course {
    private String id;
    private String name;
    private int credits;

    public Course() {
        id = "";
        name = "";
        credits = 0;
    }

    public Course(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String toString() {
        return String.format("%s,%s,%s", id, name, credits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return credits == course.credits && id.equals(course.id) && name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, credits);
    }
}
