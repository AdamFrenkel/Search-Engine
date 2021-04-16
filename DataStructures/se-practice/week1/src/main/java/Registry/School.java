package Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class School{
    private String name;
    private List<Department> departments = new ArrayList<>();
    private List<Dean> deans = new ArrayList<>();

    protected String getName(){
        return name;
    }
    protected List<Dean> getDeans(){
        return List.copyOf(deans);
    }
    protected List<Department> getDepartments(){
        return List.copyOf(departments);
    }
    protected List<Subject> getSubjects(){
        List<Subject> subjects =new ArrayList<>();
        for (Department d : departments){
            subjects.addAll(d.getSubjects());
        }
        return subjects;
    }
    protected List<Class> getClasses(){
        List<Class> classes =new ArrayList<>();
        for (Department d : departments){
            classes.addAll(d.getClasses());
        }
        return classes;
    }
    protected List<Professor> getProfessors(){
        List<Professor> professors = new ArrayList<>();
        for (Department d : departments){
            professors.addAll(d.getProfessors());
        }
        return professors;
    }
    protected List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        for (Department d : departments){
            students.addAll(d.getStudents());
        }
        return students;
    }

}