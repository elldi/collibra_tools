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

        EnvironmentController.initialise();

        port(4545);
        path("/", () -> {
            path("/environments", () -> {
                get("", (req,res) -> {

                    EnvironmentController.updateStatusOfAllEnvironments();
                    EnvironmentController.updateIdsOfAllEnvironments();


                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("enviros", EnvironmentController.getEnvironments());


                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    return sw;

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
            path("/environment" , () -> {

                get("", (req,res) -> {
                    // Add new environment page
                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    // context.put("environment", EnvironmentController.getEnvironment(req.params(":id")));

                    Template t = Velocity.getTemplate("./www/environment_add.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    return sw;
                });

                get("/:id", (req,res) -> {

                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("environment", EnvironmentController.getEnvironment(req.params(":id")));

                    Template t = Velocity.getTemplate("./www/environment_edit.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    return sw;
                });

                post("", "application/x-www-form-urlencoded", (req,res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    String enviroID = EnvironmentController.addEnvironment(req.queryMap().toMap());

                    EnvironmentController.saveEnvironments();


                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("environment", EnvironmentController.getEnvironment(enviroID));

                    Template t = Velocity.getTemplate("./www/environment_edit.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    return sw ;
                });

                put("/:enviroID", "application/x-www-form-urlencoded", (req, res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    return EnvironmentController.editEnvironment(req.params(":enviroID"), req.queryMap().toMap());
                });

            });
        });


    }

}
