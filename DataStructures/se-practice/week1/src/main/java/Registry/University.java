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
	protected String getName(int code){
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		return this.name;
	}
	protected List<Dean> getDeans(int code){
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		List<Dean> deans = new ArrayList<>();
		for (School s: schools){
			deans.addAll(s.getDeans(123454321));
		}
		return deans;
	}
	protected List<Department> getDepartments(int code) {
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		List<Department> departments = new ArrayList<>();
		for (School s : schools) {
			departments.addAll(s.getDepartments(123454321));
		}
		return departments;
	}
	protected List<Subject> getSubjects(int code){
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		List<Subject> subjects =new ArrayList<>();
		for (School s: schools){
			subjects.addAll(s.getSubjects(123454321));
		}
		return subjects;
	}
	protected List<Class> getClasses(int code){
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		List<Class> classes =new ArrayList<>();
		for (School s: schools){
			classes.addAll(s.getClasses(123454321));
		}
		return classes;
	}
	protected List<Professor> getProfessors(int code){
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		List<Professor> professors = new ArrayList<>();
		for (School s: schools){
			professors.addAll(s.getProfessors(123454321));
		}
		return professors;
	}
	protected List<Student> getStudents(int code){
		if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
			throw new IllegalCallerException("Access denied.");
		}
		List<Student> students = new ArrayList<>();
		for (School s: schools){
			students.addAll(s.getStudents(123454321));
		}
		return students;
	}
}