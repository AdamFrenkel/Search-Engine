package Registry;

import java.util.List;

class Student{
    //protected final int accessPower = 1;
	private String name;
    private int bannerID;
    private int permissionCode = 13579;
    private Transcript transcript;
    private List<Class> classes;
    protected Student(int code){
        if(!(code == 246813579 || code == 123454321)){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        int min = -100000;
        int max = 100000;
        bannerID = (int)Math.floor(Math.random()*(max-min+1)+min);
    }
	protected void setName(String name, int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;

    }
    protected String getName(int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }return name;
    }
    protected int getbannerID(int code) {
        if(!(code == 246813579 || code == 123454321)){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        return bannerID;
    }
    protected Transcript getTranscript(){
	    return transcript;
    }
    protected void addClass(int code, Class c){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        List<Class> prerequisites = c.getSubject(123454321).getPrerequisites(123454321);
        for(Class pre : prerequisites) {
            if (!transcript.takenClass(123454321, pre)){
                throw new IllegalStateException("Haven't fulfilled prerequisites");
            }
        }
        classes.add(c);
    }
    protected void deleteClass(int code, Class c){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        classes.remove(c);
    }
    protected List<Class> getClasses(int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return List.copyOf(classes);
    }
    protected void giveTranscript(Transcript t, int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
	    transcript = t;
    }
    protected Transcript getTranscript(int code){
        if(!(code == 246810||code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return transcript;
    }


}