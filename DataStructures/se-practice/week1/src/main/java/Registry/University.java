package Registry;
import java.util.ArrayList;
import java.util.List;

class University{
	private String name;
	private List<School> schools = new ArrayList<>();
	private List<Registrar> registrars = new ArrayList<>();

	protected List<School> getSchools(){
		return List.copyOf(schools);
	}
	protected List<Registrar> getRegistrars(){
		return List.copyOf(registrars);
	}
	protected String getName(){
		return this.name;
	}
	protected List<Dean> getDeans(){
		List<Dean> deans = new ArrayList<>();
		for (School s: schools){
			deans.addAll(s.getDeans());
		}
		return deans;
	}
	protected List<Department> getDepartments() {
		List<Department> departments = new ArrayList<>();
		for (School s : schools) {
			departments.addAll(s.getDepartments());
		}
		return departments;
	}
	protected List<Subject> getSubjects(){
		List<Subject> subjects =new ArrayList<>();
		for (School s: schools){
			subjects.addAll(s.getSubjects());
		}
		return subjects;
	}
	protected List<Class> getClasses(){
		List<Class> classes =new ArrayList<>();
		for (School s: schools){
			classes.addAll(s.getClasses());
		}
		return classes;
	}
	protected List<Professor> getProfessors(){
		List<Professor> professors = new ArrayList<>();
		for (School s: schools){
			professors.addAll(s.getProfessors());
		}
		return professors;
	}
	protected List<Student> getStudents(){
		List<Student> students = new ArrayList<>();
		for (School s: schools){
			students.addAll(s.getStudents());
		}
		return students;
	}
}