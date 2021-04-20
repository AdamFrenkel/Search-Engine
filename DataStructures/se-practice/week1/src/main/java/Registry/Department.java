package Registry;

import java.util.ArrayList;
import java.util.List;

class Department{
    private String name;
    private List<Subject> subjects = new ArrayList<>();
    private School school;
    protected School getSchool(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return school;
    }
    protected void changeSchool(int code, School s){
        if(school != null){
            if(!(code == school.getDeanCode(123454321)|| code == 123454321)){
                throw new IllegalCallerException("Access denied.");
            }
        }else {
            if (!(code == 123454321)) {
                throw new IllegalCallerException("Access denied.");
            }
        }
        if (school != null) {
            school.deleteDepartment(123454321,this);
        }
        s.addDepartment(123454321,this);
    }
    protected String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected void changeName(int code, String name){
        if(!(code == school.getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;
    }

    protected List<Subject> getSubjects(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(subjects);
    }
    protected void addSubject(Subject s,int code){
        if(!(code == school.getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        //s.changeSchool(this.school);
        s.changeDepartment(123454321,this);
        subjects.add(s);
    }
    protected void deleteSubject(Subject s,int code){
        if(!(code == school.getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        //s.changeSchool(null);
        s.changeDepartment(123454321,null);
        subjects.remove(s);
    }
    protected List<Class> getClasses(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Class> classes =new ArrayList<>();
        for (Subject s : subjects){
            classes.addAll(s.getClasses(123454321));
        }
        return classes;
    }
    protected List<Professor> getProfessors(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Professor> professors = new ArrayList<>();
        for (Subject s : subjects){
            professors.addAll(s.getProfessors(123454321));
        }
        return professors;
    }
    protected List<Student> getStudents(int code){
        if(!(code == 246813579 || code == 123454321)){ //students and professors can't see which students are in a particular department
            throw new IllegalCallerException("Access denied.");
        }
        List<Student> students = new ArrayList<>();
        for (Subject s : subjects){
            students.addAll(s.getStudents(123454321));
        }
        return students;
    }

}