package Registry;

import java.util.ArrayList;
import java.util.List;

class Class{
    private String name;
    private Professor professor;
    private int time;
    private String days;
    private int classCode;

    private List<Student> students = new ArrayList<>();
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
    protected List<Student> getStudents(int code){
        if(!(code == 246813579 || code == 123454321 || professor.getClassCode(123454321) == code)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(students);
    }
    protected int getTime(int code){ //put code or classCode or bannerID if student in class
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
        return time;
    }
    protected String getDays(int code){ //put code or classCode if professor or bannerID if student in class
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
        return days;
    }

	
}