package Core;
public class Casino{ //general class for identifying a casino, has no functionality currently
    private String name;
    private String size; //casinos are ranked in size, small, med, large
    //can also make a var to rank what games they have / have access to
    //see "legal gaming classifications"

    public Casino(){

    }

    public Casino(String n, String s){
        name = n;
        size = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    
}