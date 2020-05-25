package utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import attendance.*;

/**
 * A class that holds methods to convert dates and int.
 */
public class Utility {

    /**
     * Converts a number from a String type to an int type.
     * @param stringNumber A number value as a String type.
     * @return a Integer type value. Returns null if the number is not a valid Integer.
     */
    public static int convertIndexToInt(String stringNumber){

        int number;

        try {
            number = Integer.parseInt(stringNumber);
        }
        catch (NumberFormatException e) {
            return -1;
        }

        return number;
    }

    /**
     * Converts date from a string to local date type.
     * @param date
     * @return
     */
    public static LocalDate convertDate(String date) {
        LocalDate convertedDate;
        try {
            convertedDate = LocalDate.parse(date);
        } catch (DateTimeException e) {
            return null;
        }
        return convertedDate;
    }

    /**
     * Check for duplicate ids.
     * @param members a list with members.
     */
    public static boolean checkDuplicateId(ArrayList<Member> members){
        long size = members.stream()
                .map(member -> member.getMemberID())
                .distinct()
                .count();

        if(size != members.size()){
            return false;
        }
        else{
            return true;
        }
    }
}
