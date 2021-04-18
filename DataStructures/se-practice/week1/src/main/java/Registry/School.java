package Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class School{
    private String name;
    private List<Department> departments = new ArrayList<>();
    private List<Dean> deans = new ArrayList<>();

    protected String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected List<Dean> getDeans(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(deans);
    }
    protected List<Department> getDepartments(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(departments);
    }
    protected List<Subject> getSubjects(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Subject> subjects =new ArrayList<>();
        for (Department d : departments){
            subjects.addAll(d.getSubjects(123454321));
        }
        return subjects;
    }
    protected List<Class> getClasses(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Class> classes =new ArrayList<>();
        for (Department d : departments){
            classes.addAll(d.getClasses(123454321));
        }
        return classes;
    }
    protected List<Professor> getProfessors(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Professor> professors = new ArrayList<>();
        for (Department d : departments){
            professors.addAll(d.getProfessors(123454321));
        }
        return professors;
    }
    protected List<Student> getStudents(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Student> students = new ArrayList<>();
        for (Department d : departments){
            students.addAll(d.getStudents(123454321));
        }
        return students;
    }

}