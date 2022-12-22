package universityManagmentApp;

import universityManagmentApp.customExceptions.CourseNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Lector extends User{

    public enum lectorType { DOCENT, PROFESSOR, ASSISTANCE }
    lectorType type;
    private List<Course> courses = new ArrayList<>();

    public Lector(int id, String firstName, String lastName, lectorType type) {
        super(id, firstName, lastName);
        this.type = type;
    }
    //Maximum courses per Lector are 4!
    public boolean AddCourse(Course course){
        if(courses.contains(course)){
            System.out.println("This course has already assigned to "+this.type+".");
            return false;
        }
        else if(courses.size()>=4){
            System.out.println("Courses quantity is max(4).");
            return false;
        }
        courses.add(course);
        return true;
    }
    public boolean DeleteCourse(Course course){
        int size=courses.size();
        if(courses.contains(course)){
            courses.remove(course);
            return true;
        }
        if(size==courses.size()){
            try {
                throw new CourseNotFoundException(course.getName());
            } catch (CourseNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
    public List<Course> getCourses() {
        return courses;
    }

}
