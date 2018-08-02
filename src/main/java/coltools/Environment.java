package coltools;

/**
 * Created by elliot on 11/07/2018.
 */
public class Environment {

    private String baseUrl;
    private String username;
    private String password;
    private String status;
    private String name;

    public Environment(){
        baseUrl ="None";
        username = "None";
        password = "None";
        status = "None";
        name = "None";
    }


    public String getBaseUrl(){ return baseUrl; }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getStatus(){ return status; }
    public String getName(){ return name; }

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


}
