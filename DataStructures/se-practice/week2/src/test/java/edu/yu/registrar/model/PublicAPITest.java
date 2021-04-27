package edu.yu.registrar.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PublicAPITest {
    School yC;
    Department comSci;
    Course dS;
    Course introToCS;
    Student adamFrenkel;
    Professor judahDiament;
    Employee barryEichler;
    Employee johnSmith;
    CourseOffering iCTRJudahDiament;
    CourseOffering dSTRJudahDiament;
    PublicAPI publicAPI;
//    Student mosheGoldstien;
    @BeforeEach
    public void init() {
        yC = new School("Yeshiva College");
        comSci = new Department("Computer Science", yC);
        dS = new Course("Data Structures", comSci, 1420);
        introToCS = new Course("Intro to Compuer Science", comSci, 1310);
        adamFrenkel = new Student();
        judahDiament = new Professor("Judah", "Diament", 30948, comSci);
        barryEichler = new Employee("Barry", "Eichler", 98323, Employee.Role.DEAN, yC);
        johnSmith = new Employee("John", "Smith", 84593, Employee.Role.REGISTRAR, yC);
        iCTRJudahDiament = new CourseOffering(introToCS, 2020, CourseOffering.Semester.FALL, judahDiament);
        dSTRJudahDiament = new CourseOffering(dS, 2021, CourseOffering.Semester.SPRING, judahDiament);
        dS.addPrerequisite(introToCS);
        publicAPI = new PublicAPI();
//        mosheGoldstien = new Student();
    }
    @Test
    public void testRegisterForClass(){
        boolean failedToRegisterForClass = false;
        try {
            publicAPI.registerForClass(adamFrenkel.getId(), 1420);
        }catch(IllegalAccessError e){ //bc doesn't have proper prereqs
            failedToRegisterForClass = true;
        }
        assertTrue(failedToRegisterForClass);
        adamFrenkel.addCoursesTaken(iCTRJudahDiament);
        assertTrue(publicAPI.registerForClass(adamFrenkel.getId(), 1420));
        boolean failedToRegisterJudahForClass = false;
        try {
            publicAPI.registerForClass(judahDiament.getId(), 1420); //trying to add a professor to a class
        }catch(IllegalArgumentException e){
            failedToRegisterJudahForClass = true;
        }
        assertTrue(failedToRegisterJudahForClass);
    }

    @Test
    public void testCreateClassOffering(){
        boolean failedToCreateClass = false;
        try {
            publicAPI.createClassOffering(adamFrenkel.getId(), introToCS,2021,CourseOffering.Semester.FALL,judahDiament.getId());
        }catch(IllegalAccessError e){
            failedToCreateClass = true;
        }
        assertTrue(failedToCreateClass);
        CourseOffering returnCourseOffering = publicAPI.createClassOffering(judahDiament.getId(), introToCS,2021,CourseOffering.Semester.FALL,judahDiament.getId());
        assertEquals("2021", returnCourseOffering.getYear());
        assertEquals(judahDiament, returnCourseOffering.getProfessor());
        assertEquals(introToCS,returnCourseOffering.getCourse());
        assertEquals(CourseOffering.Semester.FALL,returnCourseOffering.getSemester());
    }


    @Test
    public void testSearchForClassOffering(){
        CourseOffering returnCourseOffering = publicAPI.searchClassOffering(adamFrenkel.getId(),1420,2021, CourseOffering.Semester.FALL,"Judah Diament");
        assertEquals(1420,returnCourseOffering.getCourse().getNumber());
        boolean failedToSetClass = false;
        try {
            returnCourseOffering.getCourse().removePrerequisite(introToCS);
        }catch(UnauthorizedActionException e){ //bc a student can't remove a prereq, so he got an immutable class.  This should throw an unauthorized exception
            failedToSetClass = true;
        }
        assertTrue(failedToSetClass);
        CourseOffering returnCourse2 = publicAPI.searchClassOffering(judahDiament.getId(),1420,2021, CourseOffering.Semester.FALL,"Judah Diament");
        assertEquals(1420,returnCourse2.getCourse().getNumber());
        assertTrue(returnCourse2.getCourse().removePrerequisite(introToCS));
        CourseOffering returnCourse3 = publicAPI.searchClassOffering(barryEichler.getId(),1420,2021, CourseOffering.Semester.FALL,"Judah Diament");
        assertEquals(1420,returnCourse3.getCourse().getNumber());
        assertTrue(returnCourse3.getCourse().removePrerequisite(introToCS));
    }

    @Test
    public void testSeachForMajor() {
        dS.addPrerequisite(introToCS);
        Major returnMajor = publicAPI.searchForMajor(adamFrenkel.getId(), "Computer Science");
        assertEquals("Computer Science", returnMajor.getName());
        boolean failedToSetName = false;
        try {
            returnMajor.setName("Major that takes up a lot of time");
        } catch (UnauthorizedActionException e) { //bc a student can't remove a prereq, so he got an immutable class.  This should throw an unauthorized exception
            failedToSetName = true;
        }
        assertTrue(failedToSetName);
        Major returnMajor2 = publicAPI.searchForMajor(judahDiament.getId(), "Computer Science");
        assertEquals("Computer Science", returnMajor2.getName());
        boolean failedToSetName2 = false;
        try {
            returnMajor2.setName("Major that takes up a lot of time");
        } catch (UnauthorizedActionException e) { //bc a student can't remove a prereq, so he got an immutable class.  This should throw an unauthorized exception
            failedToSetName2 = true;
        }
        assertTrue(failedToSetName2);
        Major returnMajor3 = publicAPI.searchForMajor(barryEichler.getId(), "Computer Science");
        assertEquals("Computer Science", returnMajor3.getName());
        returnMajor3.setName("Com Sci");
        assertEquals("Com sci", returnMajor3.getName());
        Major returnMajor4 = publicAPI.searchForMajor(johnSmith.getId(), "Computer Science");
        assertEquals("Computer Science", returnMajor4.getName());
        returnMajor4.setName("Com Sci!");
        assertEquals("Com sci!", returnMajor4.getName());
    }

    public void testAddCoursesStudentHasTaken(){
        Set<Course> newCourses = new HashSet<>();
        newCourses.add(introToCS);
        boolean failedToAddCourses = false;
        try {
            publicAPI.addCoursesStudentHasTaken(adamFrenkel.getId(),adamFrenkel.getId(),newCourses);
        } catch (UnauthorizedActionException e) {
            failedToAddCourses = true;
        }
        assertTrue(failedToAddCourses);
        boolean failedToAddCourses2 = false;
        try {
            publicAPI.addCoursesStudentHasTaken(judahDiament.getId(),adamFrenkel.getId(),newCourses);
        } catch (UnauthorizedActionException e) {
            failedToAddCourses2 = true;
        }
        assertTrue(failedToAddCourses2);
        assertTrue(publicAPI.addCoursesStudentHasTaken(barryEichler.getId(),adamFrenkel.getId(),newCourses));
        newCourses.remove(introToCS);
        newCourses.add(dS);
        assertTrue(publicAPI.addCoursesStudentHasTaken(johnSmith.getId(),adamFrenkel.getId(),newCourses));

    }
    @Test
    public void testGiveGrade(){
        assertEquals(-1, publicAPI.giveGrade(judahDiament.getId(), adamFrenkel.getId(), dSTRJudahDiament, 98));
        boolean failedToGiveGrade = false;
        try {
            publicAPI.giveGrade(adamFrenkel.getId(), adamFrenkel.getId(), dSTRJudahDiament, 100);
        } catch (UnauthorizedActionException e) {
            failedToGiveGrade = true;
        }
        assertTrue(failedToGiveGrade);
        assertEquals(98, publicAPI.giveGrade(barryEichler.getId(), adamFrenkel.getId(), dSTRJudahDiament, 99));
        assertEquals(99, publicAPI.giveGrade(johnSmith.getId(), adamFrenkel.getId(), dSTRJudahDiament, 100));
    }
    @Test
    public void testGetGrade(){
        publicAPI.giveGrade(judahDiament.getId(), adamFrenkel.getId(), dSTRJudahDiament, 100);
        assertEquals(100,publicAPI.getGrade(adamFrenkel.getId(),dS));
        boolean failedToGetGrade = false;
        try {
            publicAPI.getGrade(judahDiament.getId(),dS);
        } catch (UnauthorizedActionException e) {
            failedToGetGrade = true;
        }
        assertTrue(failedToGetGrade);
    }





}