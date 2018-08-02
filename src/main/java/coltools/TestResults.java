package coltools;

/**
 * Created by elliot on 02/07/2018.
 */
public class TestResults {

    Integer pass;
    Integer fail;
    Integer number;

    public TestResults(){
        pass = 0;
        fail = 0;
        number = 0;
    }

    public int passed(){
        return pass++;
    }
    public int failed(){
        return fail++;
    }
    public TestResults another(){
        number++;
        return this;
    }

    public String toString(){
        return "Tests passed " + pass + "/" + number + "\nTests failed " + fail + "/" + number + "\nAccuracy " + (Integer)(pass/number*100) + "%" ;
    }
}
