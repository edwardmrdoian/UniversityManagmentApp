package universityManagmentApp;

import universityManagmentApp.customExceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

public  class Course {
    private final String name;
    private final List<Student> students= new ArrayList<>();
    private Lector assistance;
    private Lector lector;

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, Lector assistance, Lector lector) {
        this.name = name;
        this.assistance = assistance;
        this.lector = lector;
    }
    public String getName() {
        return name;
    }

//    Maximum students per course are 30!
    public boolean AddStudent(Student student){
        if(students.contains(student)){
            System.out.println("This course has already assigned to student named "+student.getFirstName()+".");
            return false;
        }
        else if(students.size()>=30){
            System.out.println("Students quantity is max(30).");
            return false;
        }
        students.add(student);
        return true;
    }
    public boolean DeleteStudent(Student student) throws StudentNotFoundException {
        if(students.contains(student)){
            students.remove(student);
            return true;
        }
        return false;
    }
    public boolean setLector(Lector professor){
        if(this.lector==null){
            this.lector=professor;
            return true;
        }else if(professor.getId()==this.lector.getId()){
            System.out.println(professor.type+" by id: "+professor.getId()+" has already assigned to course.");
            return false;
        }
        System.out.println("Course already has "+professor.type+" by id: "+professor.getId());
        return false;
    }
    public boolean setAssistance(Lector assistance){
        if(this.assistance==null){
            this.assistance=assistance;
            return true;
        }else if(assistance.getId()==this.assistance.getId()){
            System.out.println("Assistant by id: "+assistance.getId()+" has already assigned to course.");
            return false;
        }
        System.out.println("Course already has Assistant by id: "+assistance.getId());
        return false;
    }
    public List<Student> getStudents() {
        return students;
    }
    public Lector getAssistance() {
        return assistance;
    }

    public Lector getLector() {
        return lector;
    }
    public void deleteLector(){
        this.lector=null;
    }
    public void deleteAssistance(){
        this.assistance=null;
    }
}

