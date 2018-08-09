package coltools;

import coldata.EnviroStatus;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class EnvironmentController {

    private static List<Environment> currentEnvironments = new ArrayList<>();

    public static void initialise(){

        // Check directory for environment files, if files exists read and import into currentEnvironments.

        FileController fileController = new FileController();
        Gson gson = new Gson();
        for (File file: fileController.getFilesFromDataStore()) {

            Environment env = gson.fromJson(fileController.read(file.getAbsolutePath()), Environment.class);

            currentEnvironments.add(env);
        }

    }


    public static boolean saveEnvironments(){

        // Iterate over environments list, convert to json and save in file.
        int counter = 0 ;
        for (Environment enviro: currentEnvironments) {
            Gson gson = new Gson();
            String output = gson.toJson(enviro);

            if(new FileController().writeToDataStore(enviro.getName() + ".json", output))
                counter++;
        }

        return counter == currentEnvironments.size();
    }



    public static void updateEnvironments(){

        for (Environment enviro: currentEnvironments) {

            // Stubbed for POC

            System.out.println(enviro.getBaseUrl());
            // Get from the console Rest api
            CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUsername(), enviro.getPassword());

            // With method environment
            String json = utils.getData("environment");

            if(json.isEmpty()){
                enviro.setId(UUID.randomUUID().toString());
                enviro.setStatus("UNREACHABLE");
            } else {
                // Remove json object from array
                json = json.substring(1,json.length() - 1);

                // Parse into a EnviroStatus object
                Gson gson = new Gson();
                EnviroStatus r1  = gson.fromJson(json, EnviroStatus.class);

                System.out.println(r1.status);
                enviro.setStatus(r1.status);
                enviro.setId(r1.id.toString());
            }
        }
    }

    public static List<Environment> getEnvironments(){
        return currentEnvironments;
    }

    public static Environment getEnvironment(String id){

        for (Environment e: currentEnvironments) {
            if(e.getId().equals(id)) return e;
        }
        return new Environment();
    }

    public static String addEnvironment(Map<String, String [] > params)  {

        if(params.get("baseUrl").length == 0 ) return "";

        for (Environment environment: currentEnvironments) {
            if(environment.getBaseUrl().equals(params.get("baseUrl")[0]))return "";
        }

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

        e1.setId(UUID.randomUUID().toString());

        currentEnvironments.add(e1);
        return e1.getId();
    }

    public static boolean addEnvironment(Environment e){
        return currentEnvironments.add(e);
    }

    public static boolean removeEnvironment(String id){

        for (int x = 0; x < currentEnvironments.size() ; x++) {
            if(currentEnvironments.get(x).getId().equals(id)) {
                currentEnvironments.remove(x);

                FileController.deleteFilesInDataStore();
                saveEnvironments();
                return true;
            }
        }

        return false;

    }

    public static Environment editEnvironment(String id, Map<String, String[] > params) {

        Environment editEnviro = null;

        for (Environment enviro: currentEnvironments){
            if (id.equals(enviro.getId()))
                editEnviro = enviro;
        }

        if (editEnviro != null ) {
            if(params.containsKey("baseUrl") || params.get("baseUrl").length > 0){
                editEnviro.setBaseUrl(params.get("baseUrl")[0]);
            }
            if(params.containsKey("username") || params.get("username").length > 0){
                editEnviro.setUsername(params.get("username")[0]);
            }
            if(params.containsKey("password") || params.get("password").length > 0){
                editEnviro.setPassword(params.get("password")[0]);
            }
            if(params.containsKey("name") || params.get("name").length > 0){
                System.out.println(params.get("name")[0]);
                editEnviro.setName(params.get("name")[0]);
            }
        }

        FileController.deleteFilesInDataStore();

        return editEnviro;
    }

    public static ArrayList<Environment> editEnvironment(Integer index, ArrayList<Environment> enviroList, String newBaseUrl, String newUserName, String newPassword) {

        enviroList.get(index).setBaseUrl(newBaseUrl);
        enviroList.get(index).setUsername(newUserName);
        enviroList.get(index).setPassword(newPassword);

        return enviroList;
    }

    public static boolean createBackup(String id, Map<String, String[] > params){


          // Get from the console Rest api
            CollibraRest utils = new CollibraRest(params.get("baseUrl")[0], params.get("username")[0], params.get("password")[0]);

            //if (type=="Full") {
            String JSON_STRING = "{\"name\": \"" + params.get("name")[0] + "\",\"description\": \"" + params.get("description")[0] + "\",\"dgcBackupOptions\": [\"CUSTOMIZATIONS\"],\"repoBackupOptions\": [\"DATA\",\"HISTORY\",\"CONFIGURATION\"],\"key\": \"\"}";
            //}

            Boolean backUpResult = utils.postData("backup/" + id, JSON_STRING);

            if (backUpResult) {
                System.out.println("Environment: " + params.get("name")[0] + " has been backed-up successfully!");
                return true;
            } else {
                System.out.println("Backup failed!");
                return false;
            }


    }
}
