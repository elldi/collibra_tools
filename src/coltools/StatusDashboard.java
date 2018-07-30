package coltools;
/**
 * Created by elliot on 11/07/2018.
 */

import spark.Route;


import static spark.Spark.*;

public class StatusDashboard {


    public static void main(String[] args) {

        port(4545);
        path("/", () -> {
            path("/environment", () -> {
                get("", (req,res) -> EnvironmentController.getEnvironments());
                post("", (req,res) -> EnvironmentController.addEnvironment("http://testing.com", "admin", "password"));
                
            });

        });



    }

}
