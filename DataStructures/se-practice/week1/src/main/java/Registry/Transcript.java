package Registry;

import java.util.HashMap;
import java.util.Map;

class Transcript{
    private Student student;
    private Map<Class, String> grades = new HashMap<>();
    private double Credits;
    private double totalGrade;
    protected Transcript(Student s, int code){
        if(!(code == 246813579 || code == 123454321)){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        student = s;
        s.giveTranscript(this, 123454321);
    }
    protected void assignGrade(int code, Class c, String grade){
        if(!(code == 246813579 || code == 123454321|| code == c.getProfessor(123454321).getClassCode(123454321))){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        double numGrade = 0;
        switch(grade){
            case("A"):
                numGrade = 4;
                break;
            case("A-"):
                numGrade = 3.7;
                break;
            case("B+"):
                numGrade = 3.3;
                break;
            case("B"):
                numGrade = 3.0;
                break;
            case("B-"):
                numGrade = 2.7;
                break;
            case("C+"):
                numGrade = 2.3;
            case("C"):
                numGrade = 2;
                break;
            case("C-"):
                numGrade = 1.7;
                break;
            case("D+"):
                numGrade = 1.3;
                break;
            case("D"):
                numGrade = 1;
                break;
            case("D-"):
                numGrade = .7;
                break;
            case("F"):
                numGrade = 0;
            default:
                throw new IllegalStateException("Unexpected value: " + grade);
        }
        if(grades.get(c) == null) {
            Credits += c.getSubject(123454321).getCredits(123454321);
        }
        totalGrade += numGrade;
        String oldGrade = grades.put(c,grade);
        if(oldGrade != null){
            double numGrade2 = 0;
            switch(grade){
                case("A"):
                    numGrade2 = 4;
                    break;
                case("A-"):
                    numGrade2 = 3.7;
                    break;
                case("B+"):
                    numGrade2 = 3.3;
                    break;
                case("B"):
                    numGrade2 = 3.0;
                    break;
                case("B-"):
                    numGrade2 = 2.7;
                    break;
                case("C+"):
                    numGrade2 = 2.3;
                case("C"):
                    numGrade2 = 2;
                    break;
                case("C-"):
                    numGrade2 = 1.7;
                    break;
                case("D+"):
                    numGrade2 = 1.3;
                    break;
                case("D"):
                    numGrade2 = 1;
                    break;
                case("D-"):
                    numGrade2 = .7;
                    break;
                case("F"):
                    numGrade2 = 0;
                default:
                    throw new IllegalStateException("Unexpected value: " + grade);
            }
            totalGrade -= numGrade2;
        }
    }
    protected boolean takenClass(int code, Class c){
        if(!(code == 246813579 || code == 123454321|| code == student.getbannerID(123454321))){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        if(grades.get(c) != null){
            return true;
        }
        return false;
    }
    protected double getGPA(int code){
        if(!(code == 246813579 || code == 123454321|| code == student.getbannerID(123454321))){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        return (totalGrade*Credits)/Credits;

    }

	
}