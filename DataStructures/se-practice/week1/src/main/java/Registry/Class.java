package Registry;

import java.util.ArrayList;
import java.util.List;

class Class{
    private String name;
    private Professor professor;
    private int time;
    private String days;
    private int classCode;
    private Subject subject;


    private List<Student> students = new ArrayList<>();
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
        this.professor = p;
    }
    protected List<Student> getStudents(int code){
        if(!(subject.getDepartment(123454321).getSchool(123454321).getDeanCode(123454321) == code || code == 123454321 || professor.getClassCode(123454321) == code)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(students);
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