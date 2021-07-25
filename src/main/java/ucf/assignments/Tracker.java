/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Tanushka Kommoju
 */

package ucf.assignments;

import javafx.scene.control.Alert;


public class Tracker {


    public static void showIfError(String title, String text){ // show if there's an error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }


    public static void showIfSuccess(String title, String text){ //show if it goes through
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }


    public static boolean itemNameChecker (String Name){ //description has to be more than 2 and less than 256
        if (Name.length() > 256 || Name.length() < 2){
            return false;
        }
        return true;
    }


    public static boolean itemSerialNumberLengthChecker (String serialNumber){ //serial no. has to be 10 characters
        if (serialNumber.length() != 10){
            return false;
        }
        return true;
    }


    public static boolean itemSerialNumberChecker (String serialNumber){ //alphanumerical serial number

        for (int i=0; i<serialNumber.length(); i++) {
            char x = serialNumber.charAt(i);
            boolean letterOrDigit = Character.isLetterOrDigit(x);
            if (!letterOrDigit){
                return false;
            }
        }
        return true;
    }
}