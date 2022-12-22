package universityManagmentApp.customExceptions;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String course) {
        super("There is no course with name :"+course);
    }

}
