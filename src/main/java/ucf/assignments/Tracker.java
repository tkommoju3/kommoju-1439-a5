package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Tracker implements Initializable {

    @FXML
    private TextField itemName ;
    @FXML
    private TextField itemSerialNumber;
    @FXML
    private TextField itemPrice;

    @FXML
    private TableView<Item> itemView ;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Item> items = loadItems();
        if(items != null){
            itemView.getItems().addAll(items);
        }
    }


    @FXML
    public void addItem(MouseEvent mouseEvent) {

        String description = itemName.getText().trim();


        if(description.isEmpty()){
            showIfError("Error", "Please enter item name.");
            return;
        }
        //check whether length is more than 256
        if(!nameChecker(description)){
            showIfError("Error", "Min description length is 2 \n and Max description length is 257.");
            return;
        }

        String serialNumber = itemSerialNumber.getText().trim();

        if(serialNumber.isEmpty()){
            showIfError("Error", "Please enter item name.");
            return;
        }

        if(!serialNumberLengthChecker(serialNumber)){

            showIfError("Error", "Serial Number needs to contain 10 Characters.");
            return;

        }

        if(!serialNumberChecker (serialNumber)){

            showIfError("Error", "Serial Number shall be Alphanumeric.");
            return;

        }

        Item item = new Item();
        item.setName(description);
        item.setSerialNumber(serialNumber);
        item.setPrice(Double.parseDouble(itemPrice.getText()));
        //check the capacity
        if(itemView.getItems().size() >= 100){
            showIfError("Error", "Max capacity is 100.\nYou can not add anymore from now.");
            return;
        }
        itemView.getItems().add(item);
        //enter new item
        itemName.setText("");
        itemSerialNumber.setText("");
        itemPrice.setText("");
    }

    public boolean nameChecker (String Name){

        return true;
    }

    public boolean serialNumberLengthChecker (String serialNumber){

        if (serialNumber.length() != 10)
            return false;

        return true;
    }

    public boolean serialNumberChecker (String serialNumber){

        for (int i=0; i<serialNumber.length(); i++) {
            char x = serialNumber.charAt(i);
            boolean letterOrDigit = Character.isLetterOrDigit(x);
            if (!letterOrDigit)
                return false;
        }
        return true;
    }

    public boolean uniqueChecker(String serialNumber){

        if ()
            return true;
    }
    public void searchItem(MouseEvent mouseEvent) { //user searches item
    }

    public void deleteItem(MouseEvent mouseEvent) { //user can delete ite,
    }

    public void editItem(MouseEvent mouseEvent) { //user can edit item
    }

    public void saveItem(MouseEvent mouseEvent) { //allows user to save item
    }

    private void showIfError(String title, String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }
    private void showIfCorrect(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public List<Item> loadItems() {
        try {
            FileInputStream fi = new FileInputStream("Database/items");
            ObjectInputStream oi = new ObjectInputStream(fi);
            List<Item> items = (List<Item>) oi.readObject();
            oi.close();
            fi.close();

            return items;
        } catch (Exception e) {
            return null;
        }
    }
}
