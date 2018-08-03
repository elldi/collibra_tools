package coltools;


import java.io.FileReader;
import java.io.FileWriter;

public class DataStore {

    FileReader in = null;
    FileWriter out = null;


    public boolean write(String filename, String data){

        try {
            out = new FileWriter("./data_store/" + filename);
            out.write(data);
            out.close();
            return true;

        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }

    }

    public String read(String filename){

        return "this is data from a file.";

    }


}
