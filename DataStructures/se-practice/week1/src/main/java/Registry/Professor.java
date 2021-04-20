package Registry;

import java.util.List;

class Professor implements Person {
    private String name;
    private int permissionCode = 246810;
    private int classCode;
    List<Class> classes;
    public String getName(int code){
        if(!(code == 13579 || code == 246810 || code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return name;
    }
    protected void changeName(int code, String name){
        if(!(code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;
    }
    protected Professor(int code,String Name){
        if(!(code == 246813579 || code == 123454321)){ //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        int min = -100000;
        int max = 100000;
        classCode = (int)Math.floor(Math.random()*(max-min+1)+min);
        this.name = name;
    }
    protected int getClassCode(int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return classCode;
    }
    protected void addClass(int code, Class c){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
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


}