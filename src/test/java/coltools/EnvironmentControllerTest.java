package coltools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by elliot on 03/08/2018.
 */
public class EnvironmentControllerTest {


    @Before
    public void initialize(){
        Environment environment;

        environment = new Environment();
        environment.setBaseUrl("http://localhost:4400");
        environment.setUsername("elliot");
        environment.setPassword("enter");
        environment.setName("UAT");
        environment.setStatus("RUNNING");

        EnvironmentController.addEnvironment(environment);


    }


    @Test
    public void saveEnvironments() throws Exception {
        assertEquals(EnvironmentController.saveEnvironments(), true);



    }

}