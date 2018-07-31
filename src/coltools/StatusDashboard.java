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

                    Velocity.init();

                    VelocityContext context = new VelocityContext();
                    context.put("enviros", EnvironmentController.getEnvironments());

                    Template t = Velocity.getTemplate("./www/environments.vm");

                    StringWriter sw = new StringWriter();
                    t.merge(context, sw);


                    return sw;

                });
                post("","application/x-www-form-urlencoded", (req,res) ->{
                    System.out.println(req.queryParams("baseUrl"));

                    return EnvironmentController.addEnvironment(req.queryParams("baseUrl"), req.queryParams("username"), req.queryParams("password"));
                });

            });

        });



    }

}
