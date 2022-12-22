package universityManagmentApp.managment;

import universityManagmentApp.Course;
import universityManagmentApp.Lector;
import universityManagmentApp.Student;
import universityManagmentApp.customExceptions.CourseNotFoundException;
import universityManagmentApp.customExceptions.LectorNotFoundException;
import universityManagmentApp.customExceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class UniManagementImpl implements UniManagement{
    private static int lastUsedStudentIndex;
    public Student[] students = new Student[1000];
    public List<Course> courses = new ArrayList<>();
    public List<Lector> assistancences = new ArrayList<>();
    public List<Lector> docentsAndProfessors = new ArrayList<>();
    Function<String,Predicate<Course>> courseExistence = courseName-> (c->c.getName().equals(courseName));
    Function<Integer,Predicate<Student>> studentExistence = id-> (s->s.getId()==id);
    Function<Integer,Predicate<Lector>> lectorExistence = id-> (l->l.getId()==id);
    @Override
    public Course createCourse(String courseName) {
        Course newCourse = new Course(courseName);
        for (Course course : courses) {
            if (courseExistence.apply(courseName).test(course)) {
                System.out.println("There is already course with this name.");
                return null;
            }
        }
        if(courses.size()<10) {
            courses.add(newCourse);
            System.out.println("New Course Added :" +courseName);
            return newCourse;
        }else {
            System.out.println("Courses quantity is max , we can have maximum 10 courses.");
            return null;
        }
    }

    @Override
    public boolean deleteCourse(String courseName){
        int size = courses.size();
        for (Course course : courses) {
            if (courseExistence.apply(courseName).test(course)) {
                for (Student student : course.getStudents()) {
                    try {
                        student.DeleteCourse(course);
                    } catch (CourseNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(course.getAssistance()!=null){
                    course.getAssistance().DeleteCourse(course);
                }
                if(course.getLector()!=null){
                    course.getLector().DeleteCourse(course);
                }
                courses.remove(course);
                System.out.println("The Course "+courseName+" has been deleted");
                return true;
            }
        }
        if (size == courses.size()){
            try {
                throw new CourseNotFoundException(courseName);
            } catch (CourseNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Student createStudent(int id, String firstName, String lastName, String facNumber) {
        Student newStudent = new Student(id, firstName, lastName,facNumber);
        for (Student student : students) {
            if(student!=null){
                if (studentExistence.apply(id).test(student)) {
                    System.out.println("There is already student with this id.");
                    return null;
                }
            }
        }
        students[lastUsedStudentIndex]=newStudent;
        lastUsedStudentIndex++;
        System.out.println("Added New Student: id-"+id+" name-"+firstName+" lastName-"+lastName+" facNumber- "+facNumber);
        return newStudent;
    }

    @Override
    public boolean deleteStudent(int id) {
        for (int i=0;i < lastUsedStudentIndex ;i++) {
            if (studentExistence.apply(id).test(students[i])) {
                for (Course c : students[i].getCourses()) {
                    try {
                        c.DeleteStudent(students[i]);
                    } catch (StudentNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Deleted Student: id-"+id+" name-"+students[i].getFirstName()+" lastName-"+students[i].getLastName()+" facNumber- "+students[i].getFacNumber());
                students[i]=null;
                lastUsedStudentIndex--;
                for(int j=i;j<lastUsedStudentIndex;j++){
                    students[j] = students[++i];
                }
                return true;
            }
        }
        try {
            throw new StudentNotFoundException(id);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Lector createAssistance(int id, String firstName, String lastName) {
        Lector newAssistance = new Lector(id,firstName,lastName, Lector.lectorType.ASSISTANCE);
        for (Lector assistance : assistancences) {
            if(lectorExistence.apply(id).test(assistance)){
                System.out.println("There is already assistance with this id.");
                return null;
            }
        }
        assistancences.add(newAssistance);
        System.out.println("Added New Assistance: id-"+id+" name-"+firstName);
        return newAssistance;
    }

    @Override
    public boolean deleteAssistance(int id)  {
        int size = assistancences.size();
        for (Lector assistance : assistancences) {
            if(lectorExistence.apply(id).test(assistance)){
                try{
                    for (Course cours : assistance.getCourses()) {
                        if(cours.getAssistance()!=null){
                            cours.deleteAssistance();
                        }
                        assistance.DeleteCourse(cours);
                    }
                }catch (ConcurrentModificationException e){
                    System.out.println(" ");
                }
                assistancences.remove(assistance);
                return true;
            }
        }
        if(size==assistancences.size()){
            try {
                throw new LectorNotFoundException("There is no assistant with this id.");
            } catch (LectorNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
    @Override
    public Lector createProfessor(int id, String firstName, String lastName, Lector.lectorType type) {
        Lector newDocentsAndProfessors = new Lector(id,firstName,lastName, type);
        for (Lector docentsOrProfessor : docentsAndProfessors) {
            if(lectorExistence.apply(id).test(docentsOrProfessor)){
                System.out.println("There is already "+type+" with this id.");
                return null;
            }
        }
        docentsAndProfessors.add(newDocentsAndProfessors);
        System.out.println("Added New Professor: id-"+id+" name-"+firstName+" Type-"+type);
        return newDocentsAndProfessors;
    }

    @Override
    public boolean deleteProfessor(int id)  {
        int size = docentsAndProfessors.size();
        for (Lector docentsOrProfessor : docentsAndProfessors) {
            if(lectorExistence.apply(id).test(docentsOrProfessor)){
                try{
                    for (Course cours : docentsOrProfessor.getCourses()) {
                        if(cours.getLector()!=null){
                            cours.deleteLector();
                        }
                        docentsOrProfessor.DeleteCourse(cours);
                    }
                }catch (ConcurrentModificationException e){
                    System.out.println(" ");
                }
                docentsAndProfessors.remove(docentsOrProfessor);
                System.out.println("deleted");
                return true;
            }
        }
        if(size==docentsAndProfessors.size()){
            try {
                throw new LectorNotFoundException("There is no Lector with this id.");
            } catch (LectorNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean assignAssistanceToCourse(Lector assistance, Course course) {
        return assistance.AddCourse(course) && course.setAssistance(assistance);
    }

    @Override
    public boolean assignProfessorToCourse(Lector professor, Course course) {
        return professor.AddCourse(course) && course.setLector(professor);
    }

    @Override
    public boolean addStudentToCourse(Student student, Course course) {
        return  course.AddStudent(student) && student.AddCourse(course);
    }

    @Override
    public boolean addStudentsToCourse(Student[] students, Course course) {
        for (Student student : students) {
           if( !(course.AddStudent(student)&& student.AddCourse(course))){
               return false;
           }
        }
        return true;
    }

    @Override
    public boolean removeStudentFromCourse(Student student, Course course) {
        try {
            return (course.DeleteStudent(student)&&student.DeleteCourse(course));
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.out.println(e+" in course "+ course.getName());
        }
        return false;
    }
}
