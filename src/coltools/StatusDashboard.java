package coltools;
/**
 * Created by elliot on 11/07/2018.
 */

import coldata.EnviroStatus;
import com.google.gson.Gson;
import java.util.ArrayList;

public class StatusDashboard {

    

    public static void getStatusOf(ArrayList<Environment> enviroList){


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

            System.out.println(r1.status);
        }

    }

    public static ArrayList<Environment> addEnvironment(ArrayList<Environment> enviroList, String baseUrl, String userName, String password){
        //add new environment to arrayList based on baseUrl, username and password
        enviroList.add(new Environment(baseUrl,userName,password));
        return enviroList;
    }

    public static ArrayList<Environment> removeEnvironment(ArrayList<Environment> enviroList, ArrayList<Environment> removeList){

        //Define counter to keep track of iterations through for loop
        Integer counter=0;
        //collection to keep track of index of environments to remove
        ArrayList<Integer> matchInt = new ArrayList<Integer>();
        //Loop through to gather which environments to remove
        for(Environment enviro: enviroList){
            for (Environment remEnviro: removeList) {
                if (enviro.equals(remEnviro)) {
                    matchInt.add(counter);
                    break;
                }
            }
            counter=counter++;
        }
        //Remove required Environments
        for (int i=0; i < matchInt.size(); i++){
            enviroList.remove(i);
        }
        return enviroList;
    }


    public static void main(String[] args) {
        //define an empty collection or ArrayList to store environments
        ArrayList<Environment> listOfEnvironments=new ArrayList<Environment>();

        //example addition of an environment - realise you could probably just use .add() as part of ArrayList util
        listOfEnvironments=addEnvironment(listOfEnvironments,"http://192.168.47.2:4402/rest/","Admin", "admin");

        getStatusOf(listOfEnvironments);

        //example removal of an environment
        ArrayList<Environment> emptyList=removeEnvironment(listOfEnvironments,listOfEnvironments);

        //getStatusOf(new Environment[]{new Environment("http://192.168.47.2:4402/rest/", "Admin", "admin")});

    }
}
