package coltools;

import coldata.EnviroStatus;
import coldata.BackupsAll;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;

/**
 * Created by Sam Leese on 24/07/2018.
 */
public class ActionBackup {

    public static void createBackup(ArrayList<Environment> enviroList, String name, String description, String type){
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

            //if (type=="Full") {
                String JSON_STRING = "{\"name\": \"" + name + "\",\"description\": \"" + description + "\",\"dgcBackupOptions\": [\"CUSTOMIZATIONS\"],\"repoBackupOptions\": [\"DATA\",\"HISTORY\",\"CONFIGURATION\"],\"key\": \"\"}";
            //}

            Boolean backUpResult = utils.postData("backup/" + envID, JSON_STRING);

            if (backUpResult) {
                System.out.println("Environment: " + envName + " has been backed-up successfully!");
            } else {
                System.out.println("Backup failed!");
            }

        }

    }
    public static Map<String, String[]> getBackupList(ArrayList<Environment> enviroList){
        Map<String, String[]> backupIds=new HashMap<>();
        for (Environment enviro: enviroList) {

            // Get from the console Rest api
            CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUsername(), enviro.getPassword());

            // With method environment
            String json = utils.getData("backup");

            // Remove json object from array
            json = json.substring(1,json.length() - 1);

            // Parse into a EnviroStatus object
            Gson gson = new Gson();
            BackupsAll r1  = gson.fromJson(json, BackupsAll.class);

            // Get ID of environment
            String backupID = r1.id.toString();
            String backupName = r1.name;
            String backupDes = r1.description;
            String backupVer = r1.appVersion;
            String backupDate = r1.createdDate;
            String[] backUpInfo = {backupID, backupName, backupDes, backupDate, backupVer};
            //String backupsAll = utils.getData("backup/");
            backupIds.put(enviro.getBaseUrl(),backUpInfo);

        }
    return backupIds;
    }

    public static void restoreBackup(Environment enviro, String backupId, String type){

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

            //if (type.equals("Full")) {
                String JSON_STRING = "{\"dgcRestoreOptions\": [\"CUSTOMIZATIONS\"],\"repoRestoreOptions\": [\"DATA\",\"HISTORY\",\"CONFIGURATION\"], ,\"backupId\": \"" + backupId + "\",\"key\": \"\"}";
            //}else{
            //    String JSON_STRING = "{\"dgcRestoreOptions\": [\"CUSTOMIZATIONS\"],\"repoRestoreOptions\": [\"DATA\",\"CONFIGURATION\"], ,\"backupId\": \"" + backupId + "\"}";
            //}
            // With method environment
            Boolean success = utils.postData("restore/" + envID, JSON_STRING);

            if (success){
                System.out.println("Environment: " + envName + " has been restored to backup with ID:" + backupId);
            }else {
                System.out.println("restoration of backup failed!");
            }

    }
}
