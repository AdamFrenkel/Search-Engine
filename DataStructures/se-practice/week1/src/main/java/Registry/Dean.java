package Registry;
class Dean{
    private String name;
    private int permissionCode = 246813579;
    private int deanCode;
    private School school;

    protected void setDeanCode(int deanCode, int code) {
        if(!(code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.deanCode = deanCode;
    }
    protected void setSchool(int code, School s){
        if(!(code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        school = s;
    }
    protected School getSchool(int code){
        if(!(code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        return school;
    }
}