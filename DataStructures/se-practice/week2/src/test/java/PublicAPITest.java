import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PublicAPITest {
    @BeforeEach
    public void init(){
        School yC = new School("Yeshiva College");
        Department comSci = new Departemnt("Computer Science", YC);
        Course dS = new Course("Data Structures", comSci, 1420);
        Course introToCS = new Course("Intro to Compuer Science", comSci, 1310);
        dS.addPrerequisite(introToCS);
        Student adamFrenkel = new Student();
        Professor judahDiament = new Professor("Judah", "Diament", 30948, comSci);
        Employee barryEichler = new Employee("Barry", "Eichler",98323,Role.DEAN,yC);
        Employee johnSmith = new Employee("John", "Smith",84593,Role.REGISTRAR,yC);
        courseOffering iCTRJudahDiament = new CourseOffering(introToCS,2020,Semester.FALl, judahDiament);
        courseOffering dSTRJudahDiament = new CourseOffering(dS,2021,Semester.SPRING, judahDiament);
    }
    @Test
    public void testRegisterForClass(){
        boolean failedToRegisterForClass = false;
        try {
            registerForClass(adamFrenkel.getID(), 1420);
        }catch(IllegalAccessException){ //bc doesn't have proper prereqs
            failedToRegisterForClass = true;
        }
        assertTrue(failedToRegisterForClass);
        adamFrenkel.addCoursesTaken(iCTRJudahDiament);
        assertTrue(registerForClass(adamFrenkel.getID(), 1420));
        boolean failedToRegisterJudahForClass = false;
        try {
            registerForClass(judahDiament.getID(), 1420); //trying to add a professor to a class
        }catch(IllegalArgumentException){
            failedToRegisterJudahForClass = true;
        }
        assertTrue(failedToRegisterJudahForClass);
    }


    @Test
    public void testSeachForClassOffering(){
        CourseOffering returnCourseOffering = searchClassOffering(adamFrenkel.getID(),1420,2021,Semester.FALL,"Judah Diament");
        assertEquals(1420,returnCourseOffering.getNumber());
        boolean failedToSetClass = false;
        try {
            returnCourseOffering.removePrerequisite(introToCS);
        }catch(UnauthorizedActionException){ //bc a student can't remove a prereq, so he got an immutable class.  This should throw an unauthorized exception
            failedToSetClass = true;
        }
        assertTrue(failedToSetClass);
        Course returnCourse2 = searchClassOffering(judahDiament.getID(),1420,2021,Semester.FALL,"Judah Diament");
        assertEquals(1420,returnCourse2.getNumber());
        assertTrue(returnCourse2.removePrerequisite(introToCS));
        Course returnCourse3 = searchClassOffering(barryEichler.getID(),1420,2021,Semester.FALL,"Judah Diament");
        assertEquals(1420,returnCourse3.getNumber());
        assertTrue(returnCourseOffering3.removePrerequisite(introToCS));
    }

    @Test
    public void testSeachForMajor() {
        Major returnMajor = searchForMajor(adamFrenkel.getID(), "Computer Science");
        assertEquals("Computer Science", returnMajor.getName());
        boolean failedToSetName = false;
        try {
            returnMajor.setName("Major that takes up a lot of time");
        } catch (UnauthorizedActionException) { //bc a student can't remove a prereq, so he got an immutable class.  This should throw an unauthorized exception
            failedToSetName = true;
        }
        assertTrue(failedToSetName);
        Major returnMajor2 = searchForMajor(judahDiament.getID(), "Computer Science");
        assertEquals("Computer Science", returnMajor2.getName());
        boolean failedToSetName2 = false;
        try {
            returnMajor2.setName("Major that takes up a lot of time");
        } catch (UnauthorizedActionException) { //bc a student can't remove a prereq, so he got an immutable class.  This should throw an unauthorized exception
            failedToSetName2 = true;
        }
        assertTrue(failedToSetName2);
        Major returnMajor3 = searchForMajor(barryEichler.getID(), "Computer Science");
        assertEquals("Computer Science", returnMajor3.getName());
        assertTrue(returnMajor3.setName("Com Sci"));
        Major returnMajor4 = searchForMajor(johnSmith.getID(), "Computer Science");
        assertEquals("Computer Science", returnMajor4.getName());
        assertTrue(returnMajor4.setName("Com Sci"));
    }

    public void testAddCoursesStudentHasTaken(){
        Set<Course> newCourses = new HashSet<>();
        newCourses.add(introToCS);
        boolean failedToAddCourses = false;
        try {
            addCoursesStudentHasTaken(adamFrenkel.getID(),adamFrenkel.getID(),newCourses);
        } catch (UnauthorizedActionException) {
            failedToAddCourses = true;
        }
        assertTrue(failedToAddCourses);
        boolean failedToAddCourses2 = false;
        try {
            addCoursesStudentHasTaken(judahDiament.getID(),adamFrenkel.getID(),newCourses);
        } catch (UnauthorizedActionException) {
            failedToAddCourses2 = true;
        }
        assertTrue(failedToAddCourses2);
        assertTrue(addCoursesStudentHasTaken(barryEichler.getID(),adamFrenkel.getID(),newCourses));
        newCourses.remove(introToCS);
        newCourses.add(dS);
        assertTrue(addCoursesStudentHasTaken(johnSmith.getID(),adamFrenkel.getID(),newCourses));

    }
    @Test
    public void testGiveGrade(){
        assertEquals(-1, giveGrade(judahDiament.getID(), adamFrenkel.getID(), dSTRJudahDiament, 98));
        boolean failedToGiveGrade = false;
        try {
            giveGrade(adamFrenkel.getID(), adamFrenkel.getID(), dSTRJudahDiament, 100)
        } catch (UnauthorizedActionException) {
            failedToGiveGrade = true;
        }
        assertTrue(failedToGiveGrade);
        assertEquals(98, giveGrade(barryEichler.getID(), adamFrenkel.getID(), dSTRJudahDiament, 99));
        assertEquals(99, giveGrade(johnSmith.getID(), adamFrenkel.getID(), dSTRJudahDiament, 100));
    }





}