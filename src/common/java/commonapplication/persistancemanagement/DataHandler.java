package commonapplication.persistancemanagement;

import java.io.*;

public abstract class DataHandler {

    public static String readFile(File file) {
        if (file == null) {
            return "";
        }
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(file.getPath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
