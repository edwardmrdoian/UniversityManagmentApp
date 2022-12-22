package universityManagmentApp.managment;

import universityManagmentApp.Course;
import universityManagmentApp.Lector;
import universityManagmentApp.Student;
import universityManagmentApp.customExceptions.CourseNotFoundException;
import universityManagmentApp.customExceptions.LectorNotFoundException;
import universityManagmentApp.customExceptions.StudentNotFoundException;

public interface UniManagement {
    /**
     * Create a new course with name courseName and return it
     *
     * @return new instance of course with the passed name if it's cerated or
     * null in antoher case. A course will be created only if already does not exists
     * another course with the same courseName
     */
    public Course createCourse(String courseName);
    /**
     * Delete a course with name courseName
     *
     * @return <code>true</code> only in case the course with passed name was removed
     */
    public boolean deleteCourse(String courseName) throws CourseNotFoundException;
    /**
     * Create and return new instance of Student with the passed arguments and initial state
     of the student
     *
     * @return new instance of a student identified with the passed ID, if already does not
    exists,
     * and the other arguments as initial state if it's cerated or
     * null in another case
     */
    public Student createStudent(int id, String firstName, String lastName, String facNumber);
    /**
     * Delete a student with the passed ID
     *
     * @return <code>true</code> only in case the student was removed
     */
    public boolean deleteStudent(int id) throws StudentNotFoundException;
    /**
     * Create a new assistance in the univertity withthe passed arguemtns as initial state
     *
     * @return new created professor assistance identified withthe passed ID, if already does
    not exists with that ID
     */
    public Lector createAssistance(int id, String firstName, String lastName);
    /**
     * Delete an professor asisstance with the passed ID, if such exists
     *
     * @return <code>true</code> ONLY in case the assistance was removed
     */
    public boolean deleteAssistance(int id) throws LectorNotFoundException;

    public Lector createProfessor(int id, String firstName, String lastName, Lector.lectorType type);

    public boolean deleteProfessor(int id) throws LectorNotFoundException;

    /**
     * Aighn an assistance to a course
     *
     * @return <code>true</code> ONLY n case the assistance was successfully assigned to
    the course
     */
    public boolean assignAssistanceToCourse(Lector assistance, Course course);
    /**
     * Aighn a professor to a course
     *
     * @return <code>true</code> ONLY n case the professor was successfully assigned to
    the course
     */
    public boolean assignProfessorToCourse(Lector professor, Course course);
    /**
     * Add a studnt to a course
     *
     * @return <code.true</code> ONLY inca se the student is successfully added to the
    course
     */
    public boolean addStudentToCourse(Student student, Course course);
    /**
     * Add all students to a course
     *
     * @return <code>true</code> ONLY inc ase all students are added to a course
     */
    public boolean addStudentsToCourse(Student[] students, Course course);
    /**
     * Remove a student from a course
     *
     * @return <code>true</code> only in case the student was successfully removed from a
    course
     */
    public boolean removeStudentFromCourse(Student student, Course course);
}