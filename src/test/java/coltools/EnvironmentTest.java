package coltools;

import static org.junit.Assert.assertEquals;

import coltools.Environment;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by elliot on 30/07/2018.
**/
public class EnvironmentTest {
    public Environment environment;

    @Before
    public void initialize(){
        environment = new Environment();
        environment.setBaseUrl("http://localhost:4400");
        environment.setUsername("elliot");
        environment.setPassword("enter");
        environment.setName("UAT");
        environment.setStatus("RUNNING");
    }

    @Test
    public void getBaseUrl() {
        assertEquals(environment.getBaseUrl(), "http://localhost:4400");
    }

    @Test
    public void getUserName() {
        assertEquals(environment.getUsername(), "elliot");
    }

    @Test
    public void getPassword() {
        assertEquals(environment.getPassword(), "enter");
    }

}
