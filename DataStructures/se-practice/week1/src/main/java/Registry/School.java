package Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class School{
    private String name;
    private List<Department> departments = new ArrayList<>();
    private List<Dean> deans = new ArrayList<>();
    private int deanCode;
    private University schoolsUniversity = new University();
    protected School(){
        int min = -100000;
        int max = 100000;
        this.changeDeanCode(123454321,(int)Math.floor(Math.random()*(max-min+1)+min));
    }
    protected University getUniversity(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return schoolsUniversity;
    }
    protected void changeUniversity(int code, University u){
        if(!(code == this.deanCode|| code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.schoolsUniversity = u;
    }
    protected String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected void changeName(int code, String name){
        if(!(code == this.deanCode || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;
    }
    protected int getDeanCode(int code){
        if(!(code == 123454321 || code == this.deanCode)){
            throw new IllegalCallerException("Access denied.");
        }
        return deanCode;
    }
    protected void changeDeanCode(int code, int newDeanCode){
        if(!(code == 123454321 || code == this.deanCode)){
            throw new IllegalCallerException("Access denied.");
        }
        this.deanCode = newDeanCode;
    }
    protected List<Dean> getDeans(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(deans);
    }
    protected void addDean(int code, Dean d){//if dean of this school putt in your deanCode
        if(!(code == 123454321 || code == this.deanCode)){
            throw new IllegalCallerException("Access denied.");
        }
        d.setDeanCode(deanCode,123454321);
        d.setSchool(123454321, this);
        deans.add(d);
    }
    protected void deleteDean(int code, Dean d){//if dean of this school put in your deanCode
        if(!(code == 123454321 || code == this.deanCode)){
            throw new IllegalCallerException("Access denied.");
        }
        d.setDeanCode(0,123454321);
        d.setSchool(123454321, null);
        deans.remove(d);
    }
    protected List<Department> getDepartments(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(departments);
    }
    protected void addDepartment(int code, Department d){//if dean of this school putt in your deanCode
        if(!(code == 123454321 || code == this.deanCode)){
            throw new IllegalCallerException("Access denied.");
        }
        d.changeSchool(123454321,this);
        departments.add(d);
    }
    protected void deleteDepartment(int code, Department d){//if dean of this school putt in your deanCode
        if(!(code == 123454321 || code == this.deanCode)){
            throw new IllegalCallerException("Access denied.");
        }
        d.changeSchool(123454321,new School());
        departments.remove(d);
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