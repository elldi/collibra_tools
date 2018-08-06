package coltools;
/**
 * Created by elliot on 11/07/2018.
 */


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class StatusDashboard {


    public static void main(String[] args) {

        port(4545);
        path("/", () -> {
            path("/environments", () -> {
                get("", (req,res) -> {

                    EnvironmentController.updateStatusOfAllEnvironments();
                    EnvironmentController.updateIdsOfAllEnvironments();

                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("enviros", EnvironmentController.getEnvironments());

                    Template t = Velocity.getTemplate("./www/addEnvironments.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    return sw;

                });
            });
            path("/environment", () -> {
                // GET, PATCH and  DELETE needed for environment model.
                post("", "application/x-www-form-urlencoded", (req,res) ->{
                    System.out.println(req.queryParams("baseUrl"));
                    System.out.println(req.queryParams("username"));
                    System.out.println(req.queryParams("password"));
                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("enviros", EnvironmentController.getEnvironments());

                    Template t = Velocity.getTemplate("./www/environments.vm");

                    return EnvironmentController.addEnvironment(req.queryMap().toMap());
                });

            });
            path("/environment/backup/", () -> {
                // create backup of environment by ID
                post(":enviroID", "application/x-www-form-urlencoded", (req,res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    // map should contain name of backup and description as minimum
                    return EnvironmentController.createBackup(req.params(":enviroID"), req.queryMap().toMap());
                });

            });
            path("/environment/" , () -> {
                put(":enviroID", "application/x-www-form-urlencoded", (req, res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    // map should contain baseurl, username and password as min
                    return EnvironmentController.editEnvironment(req.params(":enviroID"), req.queryMap().toMap());
                });
            });

        });



    }

}
