package Registry;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityTest {
    @Test
    public void testUniversity(){
        University YU = new University(123454321,"YU");
        Registrar johnSmith = new Registrar(123454321,"John Smith");
        YU.addRegistrar(123454321, johnSmith);
        assertEquals(1,YU.getRegistrars(123454321).size());
        School yeshivaCollege = new School(123454321, "Yeshiva College");
        YU.addSchool(123454321, yeshivaCollege);
        assertEquals(1,YU.getSchools(123454321).size());
        Dean barryEichler= new Dean(123454321, "Barry Eichler");
        yeshivaCollege.addDean(123454321, barryEichler);
        assertEquals(1,YU.getDeans(123454321).size());
        assertEquals(1,yeshivaCollege.getDeans(123454321).size());
        Department computerScience = new Department(123454321,"Computer Science");
        yeshivaCollege.addDepartment(123454321,computerScience);
        assertEquals(1,yeshivaCollege.getDepartments(123454321).size());
        assertEquals(1,YU.getDepartments(123454321).size());
        Subject dataStructures = new Subject(123454321,"Data Structures");
        dataStructures.changeCredits(123454321,4);
        computerScience.addSubject(dataStructures,123454321);
        assertEquals(1,yeshivaCollege.getSubjects(123454321).size());
        assertEquals(1,YU.getSubjects(123454321).size());
        assertEquals(1,computerScience.getSubjects(123454321).size());
        Professor judahDiament = new Professor(123454321,"Judah Diament");
        Student adamFrenkel = new Student(123454321,"Adam Frenkel");
        List<Student> students = new ArrayList<>();
        students.add(adamFrenkel);
        Class judahDiamentTR430 = new Class(123454321,"Judah Diament TR 430",judahDiament,"TR",430,students);
        dataStructures.addClass(123454321,judahDiamentTR430);
        assertEquals(1,YU.getClasses(123454321).size());
        assertEquals(1,yeshivaCollege.getClasses(123454321).size());
        assertEquals(1,computerScience.getClasses(123454321).size());
        assertEquals(1,dataStructures.getClasses(123454321).size());
        assertEquals(1,YU.getStudents(123454321).size());
        assertEquals(1,yeshivaCollege.getStudents(123454321).size());
        assertEquals(1,computerScience.getStudents(123454321).size());
        assertEquals(1,dataStructures.getStudents(123454321).size());
        assertEquals(1,judahDiamentTR430.getStudents(123454321).size());
        Transcript adamsTranscript = adamFrenkel.getTranscript(123454321);
        adamsTranscript.assignGrade(123454321,judahDiamentTR430,"A");
        assertEquals(4.0,adamsTranscript.getGPA(123454321));
    }

}