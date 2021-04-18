package Registry;

import java.util.List;

class Student{
    //protected final int accessPower = 1;
	private String name;
    private int bannerID;
    private int permissionCode = 13579;
    private Transcript transcript;
    private List<Class> classesTaking;
	protected void setName(String name, Object o){
	    if(o instanceof Registrar) {
            this.name = name;
        }
    }
    protected String getName(){
	    return name;
    }
    protected void setBannerID(int bannerID, Object o){
        this.bannerID = bannerID;
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


}