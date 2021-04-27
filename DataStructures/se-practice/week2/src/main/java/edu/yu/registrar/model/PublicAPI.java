package edu.yu.registrar.model;

import java.util.Set;

public class PublicAPI{


    /**
     * An employee can create a class offering
     * @param ID
     * @param course
     * @param year
     * @param semester
     * @param professorID
     * @return the new class offering.  Throws an error if a student try to create a classOffering
     */
    public CourseOffering createClassOffering(int ID, Course course, int year, CourseOffering.Semester semester, int professorID){
        return null;
    }

    /** Anyone in the school can search for a specific major.
     * @param ID
     * @param majorName
     * @return a specific Major, if there is one, null if not.  A student and professor gets an
     * immutable major, while a dean and registrar get the actual major.
     */
    public Major searchForMajor(int ID,String majorName){
        return null;
    }


    /**
     * Anyone in the school can search for a specific class offering.
     * @param ID
     * @param ClassNumber
     * @param Year
     * @param semester
     * @return a specific Course Offering, if there is one, null if not.  A student and professor who doesn't teach this class
     * gets an immutable course offering, while a dean and registrar get the actual course offering.
     */
    public CourseOffering searchClassOffering(int ID, int ClassNumber, int Year, CourseOffering.Semester semester, String professorName ){
        return null;
    }



    /**
     * Register's a student for class if he has the right prerequisites
     * @param ID
     * @param ClassNumber
     * @return true if successfully registered the student for class
     */
    public boolean registerForClass(int ID, int ClassNumber){
        return false;
    }

    /**
     * A Dean and Registrar can add courses a student has taken.
     * @param employeeID
     * @param studentID
     * @return true if successfully added all the classes. Throws an UnauthorizedActionException if a student or professor
     * tries to add the course.
     */
    public boolean addCoursesStudentHasTaken(int employeeID, int studentID, Set<Course> courses){
        return false;
    }

    /**
     * The professor of a courseOffering, or a dean or registrar, can give a grade for a specific courseOffering.
     * @param employeeID
     * @param studentID
     * @return the previous grade if there was one, -1 if not. Throws an UnauthorizedActionException if a student or another
     * professor tries to add the course.
     */
    public double giveGrade(int employeeID,int studentID,CourseOffering courseOffering, double grade){
        return 0;
    }

    /**
     * Student gets his grades for a specific course
     * @param Id
     * @param course
     * @return the dtudent's grade or throw an error if the Id is an employees.
     */
    public double getGrade(int Id, Course course){
        return 0;
    }



}











// public Set<Course> searchClassPrerequisites(int ID,int classNumber){}
//
///**
// * When a student or professor perfoms a course search:
// * He is able to recieve all the information (name, number, school, and deoartment) about the couse,
// * but is allowed to edit any of it.
// * A Dean of that school or a Regisrar is allowed to edit the informtaion.
// */
////public String getNameClass(int UserId){
//
//        }
//
///**
// * When a student perfoms a course offerings search:
// * He is able to recieve almost all the information about the couse - other than the professor's ID number.
// * He isn'tallowed to edit any of the information.
// * The professor of the course, is allowed to change his first and last name, but not his ID.
// * A Dean of that school or a Regisrar is allowed to edit all the informtaion.
// */
//
///**
// * When a student or professor or dean of another school perfoms a Departments search:
// * He is able to recieve all the information about the department.
// * He isn't allowed to edit any of the information.
// * A registrar or dean of that school is allowed to edit all of the information.
// */
//
///**
// * When a student or professor or dean of another school perfoms a Majors search:
// * He is able to recieve all the information about the department.
// * He isn't allowed to edit any of the information.
// * A registrar or dean of that school is allowed to edit all of the information.
// */