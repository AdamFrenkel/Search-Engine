package Registry;

import java.util.ArrayList;
import java.util.List;

class Department{
    private String name;
    private List<Subject> subjects = new ArrayList<>();
    protected String getName(){
        return name;
    }
    protected List<Subject> getSubjects(){
        return List.copyOf(subjects);
    }
    protected List<Class> getClasses(){
        List<Class> classes =new ArrayList<>();
        for (Subject s : subjects){
            classes.addAll(s.getClasses());
        }
        return classes;
    }
    protected List<Professor> getProfessors(){
        List<Professor> professors = new ArrayList<>();
        for (Subject s : subjects){
            professors.addAll(s.getProfessors());
        }
        return professors;
    }
    protected List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        for (Subject s : subjects){
            students.addAll(s.getStudents());
        }
        return students;
    }

}