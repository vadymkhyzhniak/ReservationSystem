package commonapplication.persistancemanagement;

import java.io.*;

/**
 * Class is used for reading the data from files
 *
 * @author Chiheb Bacha
 */
public abstract class DataHandler {

    /**
     * Reads the file
     *
     * @param file File to be read
     * @return String data from the file
     */
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
            String temp = input.readLine();
            return temp == null ? "" : temp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        File file = new File("src/server/resources/Restaurants/4.dat");
        //if (!file.exists()) {
        //Saver.createEmptyFile(file.getPath());
        //}
        System.out.println(file.getName());
        //System.out.println("1 : "+DataHandler.readFile(file));
        //System.out.println("null : "+DataHandler.readFile(null));
    }

}
