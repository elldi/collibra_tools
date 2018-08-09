package coltools;

import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by elliot on 09/08/2018.
 */
public class ServiceController {


    public static String getIdOfDGC(Environment enviro){

        CollibraRest utils = new CollibraRest(enviro.getBaseUrl(), enviro.getUsername(), enviro.getPassword());

        Service[] services = new Gson().fromJson(utils.getData("service"), Service[].class);

        Service found = Arrays.stream(services)
                .filter(service -> service.getType().equals("DGC"))
                .findFirst()
                .orElse(null);

        System.out.println(found.getId());

        return found.getId();

    }


}
