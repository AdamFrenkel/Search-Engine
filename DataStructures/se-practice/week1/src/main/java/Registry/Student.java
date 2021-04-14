package Registry;

import java.util.List;

class Student{
    //protected final int accessPower = 1;
	private String name;
    private int bannerID;
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
    private int bannerID;
    protected void setBannerID(int bannerID, Object o){
        this.bannerID = bannerID;
    }
    protected int getbannerID(){
        return bannerID;
    }


}