package Registry;
class Professor{
    private String name;
    private int permissionCode = 246810;
    private int classCode;
    protected Professor(){
        int min = -100000;
        int max = 100000;
        classCode = (int)Math.floor(Math.random()*(max-min+1)+min);
    }
    protected int getClassCode(int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return classCode;
    }

}