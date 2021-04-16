package Registry;

import java.util.ArrayList;
import java.util.List;

class Class{
    private String name;
    private Professor professor;
    private List<Student> students = new ArrayList<>();
    protected Professor getProfessor(){
        return professor;
    }
    protected List<Student> getStudents(){
        return List.copyOf(students);
    }

	
}