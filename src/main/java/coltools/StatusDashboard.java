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

                    EnvironmentController.updateEnvironments();

                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("enviros", EnvironmentController.getEnvironments());

                    Template t = Velocity.getTemplate("./www/environments.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    return sw;

                });
            });
            path("/environment/backup/", () -> {
                // create backup of environment by ID
                post(":enviroID", "application/x-www-form-urlencoded", (req,res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    //params should contain at least name of backup and description of what backup is as strings.
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

                post("/:id", "application/x-www-form-urlencoded", (req, res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("environment", EnvironmentController.editEnvironment(req.params(":id"), req.queryMap().toMap()));

                    Template t = Velocity.getTemplate("./www/environment_edit.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);

                    EnvironmentController.saveEnvironments();

                    return sw;
                });

            });
        });


    }

}
