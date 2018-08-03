package coltools;

import coldata.EnviroStatus;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class EnvironmentController {

    private static List<Environment> currentEnvironments = new ArrayList<>();

    public static void initialise(){

        // Check directory for environment files, if files exists read and import into currentEnvironments.

    }


    public static boolean saveEnvironments(){
        // Iterate over environments list, convert to json and save in file.

        int counter = 0 ;

        for (Environment enviro: currentEnvironments) {
            Gson gson = new Gson();

            String output = gson.toJson(enviro);

            if(new DataStore().write(enviro.getName() + ".json", output))
                counter++;



        }

        return counter == currentEnvironments.size();

    }



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
    public static List<Environment> updateIdsOfAllEnvironments(){


        for (Environment enviro: currentEnvironments) {

            // Stubbed for POC

//            // Get from the console Rest api
//            CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUserName(), enviro.getPassword());
//
//            // With method environment
//            String json = utils.getData("environment");
//
//            // Remove json object from array
//            json = json.substring(1,json.length() - 1);
//
//            // Parse into a EnviroStatus object
//            Gson gson = new Gson();
//            EnviroStatus r1  = gson.fromJson(json, EnviroStatus.class);
//
//            System.out.println(r1.id.toString());

            enviro.setId(UUID.randomUUID().toString());

        }
        return currentEnvironments;
    }

    public static List<Environment> getEnvironments(){
        return currentEnvironments;
    }

    public static boolean addEnvironment(Map<String, String [] > params)  {

        Environment e1 = new Environment();
        if(params.containsKey("baseUrl") || params.get("baseUrl").length > 0){
            e1.setBaseUrl(params.get("baseUrl")[0]);
        }
        if(params.containsKey("username") || params.get("username").length > 0){
            e1.setUsername(params.get("username")[0]);
        }
        if(params.containsKey("password") || params.get("password").length > 0){
            e1.setPassword(params.get("password")[0]);
        }
        if(params.containsKey("name") || params.get("name").length > 0){
            System.out.println(params.get("name")[0]);
            e1.setName(params.get("name")[0]);
        }

        currentEnvironments.add(e1);
        return true;
    }

    public static boolean addEnvironment(Environment e){
        return currentEnvironments.add(e);
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

    public static List<Environment> editEnvironment(String id, Map<String, String[] > params) {
        Integer index=0;
        Integer count=0;
        boolean found=false;

        String[] editEnv = params.get(id);

        for (Environment enviro: currentEnvironments){

            if (id.equals(enviro.getId())){
                index=count;
                found=true;
            }
            count++;
        }

        if (found) {
            if(params.containsKey("baseUrl") || params.get("baseUrl").length > 0){
                currentEnvironments.get(index).setBaseUrl(params.get("baseUrl")[0]);
            }
            if(params.containsKey("username") || params.get("username").length > 0){
                currentEnvironments.get(index).setUsername(params.get("username")[0]);
            }
            if(params.containsKey("password") || params.get("password").length > 0){
                currentEnvironments.get(index).setPassword(params.get("password")[0]);
            }
            if(params.containsKey("name") || params.get("name").length > 0){
                System.out.println(params.get("name")[0]);
                currentEnvironments.get(index).setName(params.get("name")[0]);
            }
        }
        return currentEnvironments;
    }

    public static ArrayList<Environment> editEnvironment(Integer index, ArrayList<Environment> enviroList, String newBaseUrl, String newUserName, String newPassword) {

        enviroList.get(index).setBaseUrl(newBaseUrl);
        enviroList.get(index).setUsername(newUserName);
        enviroList.get(index).setPassword(newPassword);

        return enviroList;
    }
}
