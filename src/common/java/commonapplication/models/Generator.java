package commonapplication.models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.Random;

/**
 * Class is used to generate table schema, id, or file names
 *
 * @author Chiheb Bacha
 */
public class Generator {

    private static final int MAX = 5;
    private static final int MIN = 2;

    /**
     * Generates a random rable schema
     *
     * @return String consisting of '0's and '1's, representing the presence of tables
     */
    public static String generateRandomTableSchema() {
        String str = "";
      int randomLength = (int) Math.pow((double) (new Random().nextInt(MAX - MIN) + MIN), 2);
        for (int i = 0; i < randomLength; i++) {
            str += Integer.toString(new Random().nextInt(2)); //appends 0's and 1's randomly to str
        }
        if (!str.contains("1")) { //this guarantees that there is at least 1 table
            for (int i = 0; i < str.length() / 2; i++) {
                str.replaceAll("0", "1");
            }
        }
        return str;
    }

    /**
     * Generates a restaurant file name based on restaurant instance
     *
     * @param restaurant Actual restaurant based on which .dat file is created
     * @return Path to file in form of String
     */
    public static @NotNull String generateFileName(@NotNull Restaurant restaurant) {
        String temp = restaurant.getId() + restaurant.getName() + restaurant.getOpenedFrom() + restaurant.getOpenedTo();
        String fileID = Integer.toString(Math.abs(temp.hashCode()));
        return "src/server/resources/Restaurants/" + fileID + ".dat";
    }

    /**
     * Generates a restaurant file name based on id, name and opening hours
     *
     * @param id Id of the restaurant
     * @param name Name of the restaurant
     * @param OF Time, when the restaurant opens
     * @param OT Time, when the restaurant closes
     * @return Path to file in form of String
     */
    public static @NotNull String generateFileName(long id, String name, LocalTime OF, LocalTime OT) {
        String temp = id + name + OF + OT;
        String fileID = Integer.toString(Math.abs(temp.hashCode()));
        return "src/server/resources/Restaurants/" + fileID + ".dat";
    }

    /**
     * Generates an unique id of the reservation based on reservation start and end time,
     * reserving user, date
     *
     * @param resStart Reservation starts at
     * @param resEnd Reservation ends at
     * @param reservedBy User who reserved
     * @param resDate Reservation date
     * @return String identification number
     */
    public static @NotNull String generateUniqueId(String resStart, String resEnd, String reservedBy, String resDate) {
        String temp = resStart + resEnd + reservedBy + resDate;
        String uId = Integer.toString(Math.abs(temp.hashCode()));
        return uId;
    }

}
