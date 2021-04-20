package Registry;

import java.util.ArrayList;
import java.util.List;

class Subject {
    private String name;
    private List<Class> classes = new ArrayList<>();
    private int credits;
    private List<Class> prerequisites = new ArrayList<>();
    private Department department;
    protected Department getDepartment(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return department;
    }
    protected void changeDepartment(int code, Department d){
        if(department != null){
            if(!(code == department.getSchool(123454321).getDeanCode(123454321)|| code == 123454321)){
                throw new IllegalCallerException("Access denied.");
            }
        }else {
            if (!(code == 123454321)) {
                throw new IllegalCallerException("Access denied.");
            }
        }
        if (department != null) {
            department.deleteSubject(this,123454321);
        }
        d.addSubject(this,123454321);

    }
    protected String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected void changeName(int code, String name){
        if(!(code == department.getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;
    }
    protected List<Class> getClasses(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(classes);
    }
    protected void addClass(int code, Class c){
        if(!(code == department.getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        c.changeSubject(123454321, this);
        classes.add(c);
    }
    protected void deleteClass(int code, Class c){
        if(!(code == department.getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        c.changeSubject(123454321, null);
        classes.remove(c);
    }
    protected List<Class> getPrerequisites(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(prerequisites);
    }
    protected void addPrerequisite(int code, Class c){
        if(!(code == department.getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        prerequisites.add(c);
    }
    protected void deletePrerequisite(int code, Class c){
        if(!(code == department.getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        prerequisites.remove(c);
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