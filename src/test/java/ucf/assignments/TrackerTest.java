package ucf.assignments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrackerTest {


    protected static Item item = new Item();

    String shortString = "9";
    String longString = "testttttttttttttttttttttttttttttttttttttttttttt" +
            "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
            "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
            "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
            "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
            "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt";

    String acceptableString = "An item description should be more than 2 and less than 256 chars";

    String shortSerialNumber = "12345";
    String longSerialNumber = "1234567890987654";
    String nonAlphaNumericalSerialNumber = "1234567$#@";
    String acceptableSerialNumber = "eaft12367";

    @Test
    void itemNameCheckerShort() {


        boolean resultShortString = Tracker.itemNameChecker(shortString); //return false if string is less than 2
        assertFalse(resultShortString);

    }


    @Test
    void itemNameCheckerLong() {

        boolean resultLongString = Tracker.itemNameChecker(longString); //return false if string is more than 256
        assertFalse(resultLongString);

    }


    @Test
    void itemNameChecker() {


        boolean resultAcceptableString = Tracker.itemNameChecker(acceptableString); //return true if item is b/w
        // 2 and 256
        assertTrue(resultAcceptableString);

    }

    @Test
    void itemSerialNumberLengthCheckerShort() {


        boolean resultShortString = Tracker.itemSerialNumberLengthChecker(shortSerialNumber);
        //return false if number is less than 10
        assertFalse(resultShortString);

    }

    @Test
    void itemSerialNumberLengthCheckerLong() {

        boolean resultLongSerialNumber = Tracker.itemSerialNumberLengthChecker(longSerialNumber);
        //return false if number is more than 10
        assertFalse(resultLongSerialNumber);

    }


    @Test
    void itemSerialNumberLengthChecker() {

        boolean resultAcceptableSerialNumber = Tracker.itemSerialNumberLengthChecker(acceptableSerialNumber);
        //return true if number is 10
        assertFalse(resultAcceptableSerialNumber);

    }


    @Test
    void itemSerialNumberCheckerIsAlphaNumeric() {

        boolean resultAcceptableSerialNumber = Tracker.itemSerialNumberChecker(acceptableSerialNumber);
        //return true if number is alphanumerical
        assertTrue(resultAcceptableSerialNumber);

    }


    @Test
    void itemSerialNumberIsNotAlphaNumeric() {

        boolean resultNonAlphaNumericSerialNumber = Tracker.itemSerialNumberChecker(nonAlphaNumericalSerialNumber);
        //return false if number hase special characters
        assertFalse(resultNonAlphaNumericSerialNumber);

    }


}