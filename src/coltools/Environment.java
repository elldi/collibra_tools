package coltools;

/**
 * Created by elliot on 11/07/2018.
 */
public class Environment {

    private String baseUrl;
    private String userName;
    private String password;

    public Environment(String baseUrl, String userName, String password){
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.password = password;
    }

    public String getBaseUrl(){
        return baseUrl;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
