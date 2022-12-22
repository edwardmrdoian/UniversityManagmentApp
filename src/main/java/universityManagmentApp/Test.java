package universityManagmentApp;

import universityManagmentApp.customExceptions.StudentNotFoundException;
import universityManagmentApp.managment.UniManagementImpl;

public class Test {
    public static void main(String[] args) {
        UniManagementImpl management = new UniManagementImpl();

        Course course1 = management.createCourse("Java");
        Course course2 = management.createCourse("Kotlin");
        Student student1 = management.createStudent(1,"edward","mrdoyan","001003910");
        Student student2 = management.createStudent(2,"brad","prat","103934");
        Student student3 = management.createStudent(6,"noah","jhones","231313");
        Lector lector1 = management.createProfessor(1,"jhon","bart", Lector.lectorType.PROFESSOR);
        Student student4 = management.createStudent(3,"david","harington","87877");
        Lector assistant1 = management.createAssistance(1,"george","black");
        Student[] students = {student1,student2,student3,student4};
        management.addStudentsToCourse(students,course1);
        management.assignProfessorToCourse(lector1,course1);
        management.assignAssistanceToCourse(assistant1,course1);
        management.removeStudentFromCourse(student4,course1);
        management.addStudentToCourse(student4,course2);
        Lector lector2 = management.createProfessor(2,"mary","winchester", Lector.lectorType.DOCENT);

        management.deleteCourse("Java");
        try {
            course1.DeleteStudent(student3);
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        management.assignProfessorToCourse(lector2,course2);
        management.assignAssistanceToCourse(assistant1,course2);
        management.deleteProfessor(2);
        management.deleteAssistance(1);
        management.createProfessor(1,"jhon","snow",Lector.lectorType.PROFESSOR);
    }
}
