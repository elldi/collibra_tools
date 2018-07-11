package coltools;
/**
 * Created by elliot on 11/07/2018.
 */

import coldata.EnviroStatus;
import com.google.gson.Gson;

public class StatusDashboard {





    public static void main(String[] args) {

        // Get from the console Rest api
        CollibraRest utils = new CollibraRest("http://localhost:4402/rest/", "Admin", "admin");

        // With method environment
        String json = utils.getData("environment");

        // Remove json object from array
        json = json.substring(1,json.length() - 1);

        // Parse into a EnviroStatus object
        Gson gson = new Gson();
        EnviroStatus r1  = gson.fromJson(json, EnviroStatus.class);

        System.out.println(r1.status);


    }
}
