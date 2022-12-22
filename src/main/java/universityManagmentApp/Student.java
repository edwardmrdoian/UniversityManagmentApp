package universityManagmentApp;

import universityManagmentApp.customExceptions.CourseNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    private final String facNumber;
    private final List<Course> courses = new ArrayList<>();

    public Student(int id, String firstName, String lastName,String facNumber) {
        super(id, firstName, lastName);
        this.facNumber=facNumber;
    }
    //Maximum courses per student are 10!
    public boolean AddCourse(Course course){
        if(courses.contains(course)){
            System.out.println("The course named "+course.getName()+" has already assigned to student ");
            return false;
        }
        else if(courses.size()>=10){
            System.out.println("Courses quantity is max(10).");
            return false;
        }
        courses.add(course);
        return true;
    }
    public boolean DeleteCourse(Course course) throws CourseNotFoundException {
        if(courses.contains(course)){
           courses.remove(course);
            return true;
        }
        return false;
    }
    public String getFacNumber() {
        return facNumber;
    }
    public List<Course> getCourses() {
        return courses;
    }

}
