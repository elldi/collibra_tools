package coltools;

import com.google.gson.Gson;

/**
 * Created by elliot on 09/08/2018.
 */
public class ServiceController {


    public static String getIdOfDGC(Environment enviro){

        CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUsername(), enviro.getPassword());


        Service service = new Gson().fromJson(utils.getData("service"), Service.class);

        return service.getId();

    }

    


}
