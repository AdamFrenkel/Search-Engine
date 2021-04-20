package Registry;

import java.util.ArrayList;
import java.util.List;

class Class{
    private String name;
    private Professor professor;
    private int time;
    private String days;
    private int classCode;
    private Subject subject = new Subject(123454321, "fakeSubject");
    protected Class(int code,String name,Professor professor,String days,int time, List<Student> students) {
        if (!(code == 246813579 || code == 123454321)) { //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        this.students = students;
        this.time = time;
        this.days = days;
        this.professor = professor;
        this.name = name;
    }

    private List<Student> students;
    protected String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected void changeName(int code, String name){
        if(!(code == professor.getClassCode(123454321)||code == subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;
    }
    protected Professor getProfessor(int code){
        if(!(code == 246813579 || code == 123454321 || professor.getClassCode(123454321) == code)){
            List<Student> students = this.getStudents(123454321);
            boolean inClass = false;
            for(Student s : students){
                if(s.getbannerID(123454321) == code){
                    inClass = true;
                    break;
                }
            }
            if(!inClass) {
                throw new IllegalCallerException("Access denied.");
            }
        }
        return professor;
    }
    protected void changeProfessor(int code, Professor p){
        if(!(code == subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        professor.deleteClass(123454321, this);
        p.addClass(123454321, this);
        this.professor = p;
    }
    protected void changeDayAndTime(int code,String days, int time){
        if(!(code == subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.time = time;
        this.days = days;
    }
    protected List<Student> getStudents(int code){
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(students);
    }
    protected void addStudent(int code, Student s){
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Class> prerequisites = this.getSubject(123454321).getPrerequisites(123454321);
        for(Class pre : prerequisites) {
            if (!s.getTranscript(123454321).takenClass(123454321, pre)){
                throw new IllegalStateException("Haven't fulfilled prerequisites");
            }
        }
        s.addClass(123454321, this);
        students.add(s);
    }
    protected void deleteStudent(int code, Student s){
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            throw new IllegalCallerException("Access denied.");
        }
        s.deleteClass(123454321, this);
        students.remove(s);
    }
    protected int getTime(int code){ //put code or classCode or bannerID if student in class
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            List<Student> students = this.getStudents(123454321);
            boolean inClass = false;
            for(Student s : students){
                if(s.getbannerID(123454321) == code){
                    inClass = true;
                    break;
                }
            }
            if(!inClass) {
                throw new IllegalCallerException("Access denied.");
            }
        }
        return time;
    }
    protected String getDays(int code){ //put code or classCode if professor or bannerID if student in class
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            List<Student> students = this.getStudents(123454321);
            boolean inClass = false;
            for(Student s : students){
                if(s.getbannerID(123454321) == code){
                    inClass = true;
                    break;
                }
            }
            if(!inClass) {
                throw new IllegalCallerException("Access denied.");
            }
        }
        return days;
    }
    protected Subject getSubject(int code){ //put code or classCode if professor or bannerID if student in class
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            List<Student> students = this.getStudents(123454321);
            boolean inClass = false;
            for(Student s : students){
                if(s.getbannerID(123454321) == code){
                    inClass = true;
                    break;
                }
            }
            if(!inClass) {
                throw new IllegalCallerException("Access denied.");
            }
        }
        return subject;
    }
    protected void changeSubject(int code, Subject subject){
        if(!(code == subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.subject = subject;
    }

//    protected String getClassCode(int code){ //put code or classCode if professor or bannerID if student in class
//        if(!(code == 246813579 || code == 123454321 || professor.getClassCode(123454321) == code)){
//            List<Student> students = this.getStudents(123454321);
//            boolean inClass = false;
//            for(Student s : students){
//                if(s.getbannerID(123454321) == code){
//                    inClass = true;
//                    break;
//                }
//            }
//            if(!inClass) {
//                throw new IllegalCallerException("Access denied.");
//            }
//        }
//        return days;
//    }

	
}