package universityManagmentApp.customExceptions;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException(int id) {
        super("There is no student with id :"+id);
    }
}
