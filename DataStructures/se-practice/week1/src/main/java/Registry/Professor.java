package Registry;
class Professor{
    private String name;
    private int permissionCode = 246810;
    private int classCode;
    protected int getClassCode(int code){
        if(!(code == 246813579 || code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return classCode;
    }

}