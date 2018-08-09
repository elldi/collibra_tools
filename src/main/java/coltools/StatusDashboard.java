package coltools;
/**
 * Created by elliot on 11/07/2018.
 */


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import sun.rmi.runtime.Log;


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

                    return "<meta http-equiv=\"refresh\" content=\"0; url=/environments\" />\"";
                });

                post("/:id", "application/x-www-form-urlencoded", (req, res) ->{

                    EnvironmentController.editEnvironment(req.params(":id"), req.queryMap().toMap());

                    EnvironmentController.saveEnvironments();

                    return "<meta http-equiv=\"refresh\" content=\"0; url=/environments\" />\"";
                });

                post("/:id/backup", "application/x-www-form-urlencoded", (req,res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    //params should contain at least name of backup and description of what backup is as strings.
                    return EnvironmentController.createBackup(req.params(":id"), req.queryMap().toMap());
                });

                get("/:id/delete", "application/x-www-form-urlencoded", (req,res) -> {

                    EnvironmentController.removeEnvironment(req.params(":id"));

                    return "<meta http-equiv=\"refresh\" content=\"0; url=/environments\" />\"";
                });

                get("/:id/log", (req,res) -> {

                    Environment e = EnvironmentController.getEnvironment(req.params(":id"));

                    String dgcID = ServiceController.getIdOfDGC(e);

                    String log = LogController.getLog(e, dgcID);

                    return log;
                });

            });
        });


    }

}