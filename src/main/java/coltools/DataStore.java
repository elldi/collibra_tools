package coltools;


import java.io.*;
import java.util.Arrays;

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
            e.printStackTrace();
            return false;
        }

    }

    public String read(String filename){

        StringBuilder output = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                output.append(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return output.toString();

    }

    public File [] getFilesFromDataStore(){
        return new File("./data_store").listFiles();
    }

    public void deleteFilesInDataStore(){
        Arrays.stream(new File("./data_store").listFiles()).forEach(File::delete);

    }


}
