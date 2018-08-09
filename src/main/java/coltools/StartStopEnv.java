package coltools;

/**
 * Created by Sam Leese on 12/07/2018.
 */

import coldata.EnviroStatus;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StartStopEnv {

    public static void stopEnvironment(ArrayList<Environment> enviroList, String action){
        for (Environment enviro: enviroList) {

            // Get from the console Rest api
            CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUsername(), enviro.getPassword());

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
            String JSON_STRING = "{\"userName\": \"test\",\"firstName\": \"test\",\"lastName\": \"ets\",\"emailAddress\": \"elliot.dines@db.com\",\"gender\": \"MALE\"}";

            Boolean actionTrue = utils.postData("environment/" + envID + "/stop", JSON_STRING);

            if (actionTrue) {
                System.out.println("Environment: " + envName + " has been stopped successfully!");
            }else {
                System.out.println("No change made to environment.");
            }
        }

    }
    public static void startEnvironment(ArrayList<Environment> enviroList, String action){
        for (Environment enviro: enviroList) {

            // Get from the console Rest api
            CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUsername(), enviro.getPassword());

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
            String JSON_STRING = "{\"userName\": \"test\",\"firstName\": \"test\",\"lastName\": \"ets\",\"emailAddress\": \"elliot.dines@db.com\",\"gender\": \"MALE\"}";

            Boolean actionTrue = utils.postData("environment/" + envID + "/start", JSON_STRING);

            if (actionTrue) {
                System.out.println("Environment: " + envName + " has been started successfully!");
            }else {
                System.out.println("No change made to environment.");
            }
        }

    }
    /*public static void main(String[] args) {


        stopStartEnvironment(new Environment[]{new Environment("http://192.168.47.2:4402/rest/", "Admin", "admin")}, "Stop");


    }*/
}

