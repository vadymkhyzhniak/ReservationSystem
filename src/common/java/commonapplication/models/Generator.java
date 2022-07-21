package commonapplication.models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.Random;

public class Generator {

    private static final int MAX = 5;
    private static final int MIN = 2;


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

    public static @NotNull String generateFileName(@NotNull Restaurant restaurant) {
        String temp = restaurant.getId() + restaurant.getName() + restaurant.getOpenedFrom() + restaurant.getOpenedTo();
        String fileID = Integer.toString(Math.abs(temp.hashCode()));
        return "src/server/resources/Restaurants/" + fileID + ".dat";
    }

    public static @NotNull String generateFileName(long id, String name, LocalTime OF, LocalTime OT) {
        String temp = id + name + OF + OT;
        String fileID = Integer.toString(Math.abs(temp.hashCode()));
        return "src/server/resources/Restaurants/" + fileID + ".dat";
    }

    public static @NotNull String generateUniqueId(String resStart, String resEnd, String reservedBy, String resDate) {
        String temp = resStart + resEnd + reservedBy + resDate;
        String uId = Integer.toString(Math.abs(temp.hashCode()));
        return uId;
    }

}
