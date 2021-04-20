package Registry;
class Registrar{
    private String name;
    private int permissionCode = 123454321;
    protected String getName(int code){
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

}