import universityManagmentApp.Course;
import universityManagmentApp.Lector;
import universityManagmentApp.Student;
import universityManagmentApp.commands.ArgumentParser;
import universityManagmentApp.customExceptions.CourseNotFoundException;
import universityManagmentApp.customExceptions.LectorNotFoundException;
import universityManagmentApp.customExceptions.StudentNotFoundException;
import universityManagmentApp.managment.UniManagementImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static universityManagmentApp.Lector.lectorType.DOCENT;
import static universityManagmentApp.Lector.lectorType.PROFESSOR;

public class Main {
    static UniManagementImpl management;
    public static void main(String[] args) {
        management = new UniManagementImpl();
        Scanner scanner = new Scanner(System.in);
        String statement;
        List<String> sentence;
        while (scanner.hasNextLine()){
            statement = scanner.nextLine();
            if(statement.equalsIgnoreCase("exit")){
                break;
            }
            sentence = List.of(statement.split(" "));
            checkStatement(sentence);
        }

    }
    public static void checkStatement(List<String> sentence){
        String command= sentence.get(0);
        Lector.lectorType lectorType;
        Course course = null;
        Student student = null;
        Lector lector = null;
        switch (command){
            case ArgumentParser.CREATE_COURSE:
                if(sentence.size()==2){
                    management.createCourse(sentence.get(1));
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: createCourse <courseName>");
                }
                break;
            case ArgumentParser.DELETE_COURSE:
                if(sentence.size()==2){
                    management.deleteCourse(sentence.get(1));
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: deleteCourse <courseName>");
                }
                break;
            case ArgumentParser.CREATE_STUDENT:
                if(sentence.size()==5) {
                    management.createStudent(Integer.parseInt(sentence.get(1)), sentence.get(2), sentence.get(3), sentence.get(4));
                }else{
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: createStudent <user_id> <userFirstName> <userLastName> <facNumber>");
                }
                break;
            case ArgumentParser.DELETE_STUDENT:
                if(sentence.size()==2){
                    management.deleteStudent(Integer.parseInt(sentence.get(1)));
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: deleteStudent <studentId>");
                }
                break;
            case ArgumentParser.CREATE_PROFESSOR:
                if(sentence.size()==5){
                    if(!sentence.get(4).equals("DOCENT")&&!sentence.get(4).equals("PROFESSOR")){
                        System.out.println("Lector type should be DOCENT or PROFESSOR");
                    }
                    lectorType = (sentence.get(4).equals("DOCENT"))?DOCENT:PROFESSOR;
                    management.createProfessor(Integer.parseInt(sentence.get(1)), sentence.get(2), sentence.get(3),lectorType);
                }else{
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: createProfessor <user_id> <userFirstName> <userLastname> <lectorType>");
                }
                break;
            case ArgumentParser.DELETE_PROFESSOR:
                if(sentence.size()==2){
                    management.deleteProfessor(Integer.parseInt(sentence.get(1)));
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: deleteProfessor <professorId>");
                }
                break;
            case ArgumentParser.CREATE_ASSISTANCE:
                if(sentence.size()==4){
                    management.createAssistance(Integer.parseInt(sentence.get(1)), sentence.get(2), sentence.get(3));
                }else{
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: createAssistance <user_id> <userFirstName> <userLastName>");
                }
                break;
            case ArgumentParser.DELETE_ASSISTANCE:
                if(sentence.size()==2){
                    management.deleteAssistance(Integer.parseInt(sentence.get(1)));
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: deleteAssistance <assistanceId>");
                }
                break;
            case ArgumentParser.ASSIGN_STUDENT_TO_COURSE:
                if(sentence.size()==3){
                    if(Arrays.stream(management.students).noneMatch(Objects::nonNull)&&management.courses.isEmpty()){
                        System.out.println("Students and Courses are empty!");
                        break;
                    }
                    if (Arrays.stream(management.students).noneMatch(Objects::nonNull)) {
                        try {
                            throw new StudentNotFoundException(Integer.parseInt(sentence.get(1)));
                        } catch (StudentNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(management.courses.isEmpty()){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    for (Course c : management.courses) {
                        if(c.getName().equals(sentence.get(2))){
                            course=c;
                        }
                    }
                    for (Student s : Arrays.stream(management.students).filter(Objects::nonNull).collect(Collectors.toList())){
                        if(s.getId()==Integer.parseInt(sentence.get(1))){
                            student=s;
                        }
                    }
                    if(student!=null&&course!=null){
                        if(management.addStudentToCourse(student,course)){
                            System.out.println("Student by id: "+student.getId()+ " is assigned to Course" +
                                    " "+course.getName());
                        }
                    }
                    if(student==null){
                        try {
                            throw new StudentNotFoundException(Integer.parseInt(sentence.get(1)));
                        } catch (StudentNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(course==null){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: assignStudentToCourse <student_id> <courseName>");
                }
                break;
            case ArgumentParser.ASSIGN_ASSISTANCE_TO_COURSE:
                if(sentence.size()==3){
                    if(management.assistancences.isEmpty()&&management.courses.isEmpty()){
                        System.out.println("Assistances and Courses are empty!");
                        break;
                    }
                    if (management.assistancences.isEmpty()) {
                        try {
                            throw new LectorNotFoundException("There is no Assistant with id: "+sentence.get(1));
                        } catch (LectorNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(management.courses.isEmpty()){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    for (Course c : management.courses) {
                        if(c.getName().equals(sentence.get(2))){
                            course=c;
                        }
                    }
                    for (Lector a : management.assistancences){
                        if(a.getId()==Integer.parseInt(sentence.get(1))){
                            lector=a;
                        }
                    }
                    if(lector!=null&&course!=null){
                        if(management.assignAssistanceToCourse(lector,course)){
                            System.out.println("Assistant by id: "+lector.getId()+ " is assigned to Course" +
                                    " "+course.getName());
                        }
                    }
                    if(lector==null){
                        try {
                            throw new LectorNotFoundException("There is no Assistant with id: "+sentence.get(1));
                        } catch (LectorNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(course==null){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: assignStudentToCourse <assistance_id> <courseName>");
                }
                break;
            case ArgumentParser.ASSIGN_PROFESSOR_TO_COURSE:
                if(sentence.size()==3){
                    if(management.docentsAndProfessors.isEmpty()&&management.courses.isEmpty()){
                        System.out.println("Docents|Professors and Courses are empty!");
                        break;
                    }
                    if (management.docentsAndProfessors.isEmpty()) {
                        try {
                            throw new LectorNotFoundException("There is no Professor with id: "+sentence.get(1));
                        } catch (LectorNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(management.courses.isEmpty()){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    for (Course c : management.courses) {
                        if(c.getName().equals(sentence.get(2))){
                            course=c;
                        }
                    }
                    for (Lector l : management.docentsAndProfessors){
                        if(l.getId()==Integer.parseInt(sentence.get(1))){
                            lector=l;
                        }
                    }
                    if(lector!=null&&course!=null){
                        if(management.assignProfessorToCourse(lector,course)){
                            System.out.println("Professor by id: "+lector.getId()+ " is assigned to Course" +
                                    " "+course.getName());
                        }
                    }
                    if(lector==null){
                        try {
                            throw new LectorNotFoundException("There is no Professor with id: "+sentence.get(1));
                        } catch (LectorNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(course==null){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: assignProfessorToCourse <lector_id> <courseName>");
                }
                break;
            case ArgumentParser.REMOVE_STUDENT_FROM_COURSE:
                if(sentence.size()==3){
                    if(Arrays.stream(management.students).noneMatch(Objects::nonNull)&&management.courses.isEmpty()){
                        System.out.println("Students and Courses are empty!");
                        break;
                    }
                    if (Arrays.stream(management.students).noneMatch(Objects::nonNull)) {
                        try {
                            throw new StudentNotFoundException(Integer.parseInt(sentence.get(1)));
                        } catch (StudentNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(management.courses.isEmpty()){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    for (Course c : management.courses) {
                        if(c.getName().equals(sentence.get(2))){
                            course=c;
                        }
                    }
                    for (Student s : Arrays.stream(management.students).filter(Objects::nonNull).collect(Collectors.toList())){
                        if(s.getId()==Integer.parseInt(sentence.get(1))){
                            student=s;
                        }
                    }
                    if(student!=null&&course!=null){
                        if(management.removeStudentFromCourse(student,course)){
                            System.out.println("Student by id: "+student.getId()+ " is removed from Course" +
                                    " "+course.getName());
                        }
                    }
                    if(student==null){
                        try {
                            throw new StudentNotFoundException(Integer.parseInt(sentence.get(1)));
                        } catch (StudentNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }else if(course==null){
                        try {
                            throw new CourseNotFoundException(sentence.get(2));
                        } catch (CourseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }else {
                    System.out.println("Not Relevant Command , Enter All Parameters !");
                    System.out.println("Enter In This Format: removeStudentFromCourse <student_id> <courseName>");
                }
                break;
            default:
                System.out.println("Not Relevant Command !!!\n Change Command  "+sentence.get(0));
        }
    }
}