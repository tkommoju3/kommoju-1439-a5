/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Tanushka Kommoju
 */

package ucf.assignments;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField itemName ;
    @FXML
    private TextField itemSerialNumber;
    @FXML
    private TextField itemPrice;
    @FXML
    private TextField searchItem;

    @FXML
    private TableView<Item> view ;

    @FXML private TableColumn<Item, String> cName;
    @FXML private TableColumn<Item, String> cSerial;
    @FXML private TableColumn<Item, Double> cValue;

    @FXML
    RadioButton nameRadio;

    @FXML
    RadioButton serialRadio;

    @FXML
    RadioButton priceRadio;

    private ObservableList<Item> dataList;
    private FilteredList<Item> filteredList;
    private SortedList<Item> sortableData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        searchItem.textProperty().addListener((ObservableValue<? extends String> observable,
                                               String oldValue, String newValue) -> {
            filter();
        });
    }

    private void initTable(){
        //set column for each property

        cName.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        cSerial.setCellValueFactory(new PropertyValueFactory<Item, String>("SerialNumber"));
        cValue.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));

        dataList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(dataList, p -> true);
        sortableData = new SortedList<>(filteredList);
        view.setItems(sortableData);
        sortableData.comparatorProperty().bind(view.comparatorProperty());
        filteredList.addListener((Observable observable) -> {
            Platform.runLater(() -> {

            });
        });
    }


    @FXML
    public void addItem() {
        String description = itemName.getText().trim();
        if(description.isEmpty()){
            Tracker.showIfError("Error", "Please enter item name.");
            return;
        }
        //check if length is less than 2 and more than 256
        if(!Tracker.itemNameChecker(description)){
            Tracker.showIfError("Error", "Min description length is 2\nand Max description length is 256.");
            return;
        }

        String serialNumber = itemSerialNumber.getText().trim();


        if(serialNumber.isEmpty()){
            Tracker.showIfError("Error", "Please enter item name.");
            return;
        }

        // check if serial number is 10
        if(!Tracker.itemSerialNumberLengthChecker(serialNumber)){
            Tracker.showIfError("Error", "Serial Number needs to contain 10 Characters.");
            return;
        }

        //check if serial number is alphanumerical
        if(!Tracker.itemSerialNumberChecker (serialNumber)){
            Tracker.showIfError("Error", "Serial Number shall be Alphanumeric.");
            return;
        }

        //show an error if value is not entered
        String valueString = itemPrice.getText().trim();
        if(valueString.isEmpty()){
            Tracker.showIfError("Error", "Please enter value.");
            return;
        }
        double value = 0;

        //value has to be double
        try{
            value = Double.parseDouble(valueString);

            if(value <= 0){
                Tracker.showIfError("Error", "Please enter value greater than zero.");
                return;
            }
        }catch (NumberFormatException e){
            Tracker.showIfError("Error", "Please enter a numerical value.");
            return;
        }

        //set new item
        Item item = new Item();
        item.setName(description);
        item.setItemSerialNumber(serialNumber);


        if(dataList.contains(item)){
            Tracker.showIfError("Error", "You have entered a duplicate Serial Number." +
                    "\nPlease enter a unique Serial Number");
            return;
        }
        //check item list capacity
        if(dataList.size() >= 100){
            Tracker.showIfError("Error", "Max capacity is 100.\nYou can not add anymore inventory items.");
            return;
        }

        dataList.add(item);

        //clear to enter new item
        itemName.setText("");
        itemSerialNumber.setText("");
        itemPrice.setText("");
    }


    private void filter(){

        final String searchKey = searchItem.getText().toString().trim();

        filteredList.setPredicate(model -> {

            if(searchKey.isEmpty()){
                return true;
            }

            //if nameRadio button is selected look for matching names
            if(nameRadio.isSelected()){
                if(!model.getItemName().trim().toLowerCase().startsWith(searchKey)){
                    return false;
                }

                //if serialRadio button is selected look for the matching numbers
            }else if(serialRadio.isSelected()){
                if(!model.getItemSerialNumber().trim().toLowerCase().startsWith(searchKey)){
                    return false;
                }

                //if ValueRadio button is selected look for the matching prices
            }else if(priceRadio.isSelected()){
                if(!String.valueOf(model.getItemPrice()).startsWith(searchKey)){
                    return false;
                }
            }
            return true;
        });
    }

    @FXML
    public void deleteItem() {

        // get item that was selected
        Item item = view.getSelectionModel().getSelectedItem();
        if(item == null)
            return;
        dataList.remove(item);
    }

    @FXML

    public void editItem() throws IOException {

        Item item = view.getSelectionModel().getSelectedItem();
        if(item == null)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditItemPage.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        ItemChecker controller = fxmlLoader.getController();
        controller.setItem(this, item);

        Stage stage = new Stage();

        stage.setTitle("Edit Item Page");
        stage.setResizable(false);
        stage.setScene(new Scene(root1));
        stage.showAndWait();

        view.refresh();
    }

}
