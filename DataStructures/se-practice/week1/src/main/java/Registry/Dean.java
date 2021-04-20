package Registry;
class Dean implements Person{
    private String name;
    private int permissionCode = 246813579;
    private int deanCode;
    private School school;
    protected Dean(int code,String name) {
        if (!(code == 246813579 || code == 123454321)) { //students and professors can't see BannerID
            throw new IllegalCallerException("Access denied.");
        }
        this.name = name;
    }

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