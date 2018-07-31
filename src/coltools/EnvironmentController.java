package coltools;

import coldata.EnviroStatus;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by elliot on 30/07/2018.
 */
public class EnvironmentController {

    private static List<Environment> currentEnvironments = new ArrayList<>();


    public static void updateStatusOfAllEnvironments(){


        for (Environment enviro: currentEnvironments) {

            // Stubbed for POC

            // Get from the console Rest api
            //CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUserName(), enviro.getPassword());

            // With method environment
            //String json = utils.getData("environment");

            // Remove json object from array
            //json = json.substring(1,json.length() - 1);

            // Parse into a EnviroStatus object
            //Gson gson = new Gson();
            //EnviroStatus r1  = gson.fromJson(json, EnviroStatus.class);

            //System.out.println(r1.status);

            int rand = ThreadLocalRandom.current().nextInt(1, 4);
            switch (rand){
                case 1:
                    enviro.setStatus("RUNNING");
                    break;
                case 2:
                    enviro.setStatus("STOPPING");
                    break;
                case 3:
                    enviro.setStatus("STOPPED");
                    break;
            }
        }
    }

    public static List<Environment> getEnvironments(){
        return currentEnvironments;
    }

    public static boolean addEnvironment(String baseUrl, String userName, String password)  {
//        for (Environment enviro : currentEnvironments){
//            if (baseUrl.equals(enviro.getBaseUrl())){
//                //throw new RepeatException();
//            }
//        }
        //add new environment to arrayList based on baseUrl, username and password
        currentEnvironments.add(new Environment(baseUrl,userName,password));
        return true;
    }

    public static List<Environment> removeEnvironment(ArrayList<Environment> enviroList, ArrayList<Environment> removeList){

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

    public static ArrayList<Environment> editEnvironment(ArrayList<Environment> enviroList, String baseUrl, String newUserName, String newPassword) {
        Integer index=0;
        Integer count=0;
        boolean found=false;
        for (Environment enviro: enviroList){
            if (baseUrl.equals(enviro.getBaseUrl())){
                index=count;
                found=true;
            }
            count++;
        }

        if (found) {
            enviroList.get(index).setUserName(newUserName);
            enviroList.get(index).setPassword(newPassword);
        }

        return enviroList;
    }

    public static ArrayList<Environment> editEnvironment(Integer index, ArrayList<Environment> enviroList, String newBaseUrl, String newUserName, String newPassword) {

        enviroList.get(index).setBaseUrl(newBaseUrl);
        enviroList.get(index).setUserName(newUserName);
        enviroList.get(index).setPassword(newPassword);

        return enviroList;
    }
}
