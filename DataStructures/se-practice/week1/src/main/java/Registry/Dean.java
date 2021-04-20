package Registry;
class Dean{
    private String name;
    private int permissionCode = 246813579;
    private int deanCode;

    public void setDeanCode(int deanCode, int code) {
        if(!(code == 123454321)){
            throw new IllegalCallerException("Access denied.");
        }
        this.deanCode = deanCode;
    }
}