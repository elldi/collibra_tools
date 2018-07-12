package coltools;

/**
 * Created by Sam Leese on 12/07/2018.
 */

import coldata.EnviroStatus;
import com.google.gson.Gson;
import java.util.UUID;

public class StartStopEnv {

    public static void stopStartEnvironment(Environment[] enviroList, String action){
        for (Environment enviro: enviroList) {

            // Get from the console Rest api
            CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUserName(), enviro.getPassword());

            // With method environment
            String json = utils.getData("environment");

            // Remove json object from array
            json = json.substring(1,json.length() - 1);

            // Parse into a EnviroStatus object
            Gson gson = new Gson();
            EnviroStatus r1  = gson.fromJson(json, EnviroStatus.class);

            // Get ID of environment
            String envID = r1.id.toString();
            String envName = r1.name;

            Boolean actionTrue = utils.postData("environment/" + envID + "/" + action);

            if (actionTrue) {
                System.out.println("Environment: " + envName + " has been " + action + "ed successfully!");
            }
        }

    }
    public static void main(String[] args) {


        stopStartEnvironment(new Environment[]{new Environment("http://192.168.47.2:4402/rest/", "Admin", "admin")}, "Stop");


    }
}

