/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Tanushka Kommoju
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemChecker implements Initializable {
    @FXML
    TextField itemName;

    @FXML
    TextField itemSerialNumber;

    @FXML
    TextField itemPrice;

    Item item;
    Controller parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setItem(Controller parent, Item item){
        this.parent = parent;
        this.item = item;
        this.itemName.setText(item.getItemName());
        this.itemSerialNumber.setText(item.getItemSerialNumber());
        this.itemPrice.setText(String.valueOf(item.getItemPrice()));
    }
    @FXML

    public void updateItem() {

        String description = itemName.getText().trim();

        if(description.isEmpty()){
            Tracker.showIfError("Error", "Please enter item itemName.");
            return;
        }

        //check if length is more than 256
        if(!Tracker.itemNameChecker(description)){
            Tracker.showIfError("Error", "Min description length should be 2 " +
                    "\nand Max description length should be 256.");
            return;
        }

        String itemSerialNumber = itemSerialNumber.getText().trim();

        if(itemSerialNumber.isEmpty()){
            Tracker.showIfError("Error", "Please enter a valid serial number.");
            return;
        }

        if(!Tracker.itemSerialNumberLengthChecker(itemSerialNumber)){
            Tracker.showIfError("Error", "Serial Number needs to have 10 characters.");
            return;
        }

        if(!Tracker.itemSerialNumberChecker (itemSerialNumber)){
            Tracker.showIfError("Error", "Serial Number should be alphanumeric.");
            return;
        }

        String valueString = itemPrice.getText().trim();

        if(valueString.isEmpty()){
            Tracker.showIfError("Error", "Please enter price.");
            return;
        }

        double value = 0;
        try{
            value = Double.parseDouble(valueString);
            if(value <= 0){
                Tracker.showIfError("Error", "Please enter a value greater than zero.");
                return;
            }
        }catch (NumberFormatException e){
            Tracker.showIfError("Error", "You have to enter a numerical value.");
            return;
        }

        Item updatedItem = new Item();
        updatedItem.setName(description);
        updatedItem.setItemSerialNumber(itemSerialNumber);

        //check if edited serial number matches a previously entered one
        if(!updatedItem.getItemSerialNumber().equals(item.getItemSerialNumber())){
            Tracker.showIfError("Error", "Duplicate serial number was entered." +
                    "\nPlease enter a unique Serial Number");
            return;
        }

        item.setName(description);
        item.setItemSerialNumber(itemSerialNumber);

        Stage stage = (Stage) itemName.getScene().getWindow();
        stage.close();
    }
}
