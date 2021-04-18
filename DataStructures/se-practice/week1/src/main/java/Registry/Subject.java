package Registry;

import java.util.ArrayList;
import java.util.List;

class Subject {
    private String name;
    private List<Class> classes = new ArrayList<>();
    private int credits;
    private List<Class> prerequisites = new ArrayList<>();

    protected String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected List<Class> getClasses(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(classes);
    }
    protected List<Professor> getProfessors(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Professor> professors = new ArrayList<>();
        for(Class c : classes){
            professors.add(c.getProfessor(123454321));
        }
        return professors;
    }
    protected List<Student> getStudents(int code){
        if(!(code == 246813579 || code == 123454321)){ //no students or professors
            throw new IllegalCallerException("Access denied.");
        }
        List<Student> students = new ArrayList<>();
        for(Class c : classes){
            students.addAll(c.getStudents(123454321));
        }
        return students;
    }
}