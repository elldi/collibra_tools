package coltools;

/**
 * Created by elliot on 11/07/2018.
 */
public class Environment implements Comparable{

    private String baseUrl;
    private String username;
    private String password;
    private String status;
    private String name;
    private String id;

    public Environment(){
        baseUrl ="None";
        username = "None";
        password = "None";
        status = "None";
        name = "None";
        id = "None";
    }


    // Standard getter methods
    public String getBaseUrl(){ return baseUrl; }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getStatus(){ return status; }
    public String getName(){ return name; }
    public String getId(){ return id; }

    // Standard setter methods 
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public void setUsername(String userName) {
        this.username = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(String id){
        this.id = id;
    }


    // Implement compareTo method so that environments can be checked for equality.
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
