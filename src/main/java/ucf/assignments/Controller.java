package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField name ;
    @FXML
    private TextField serialNumber;
    @FXML
    private TextField price;
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
        //add listener for dynamic search
        searchItem.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filter();
        });
    }

    //initialize table view
    private void initTable(){

        //setting each col properties

        cName.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        cSerial.setCellValueFactory(new PropertyValueFactory<Item, String>("SerialNumber"));
        cValue.setCellValueFactory(new PropertyValueFactory<Item, Double>("Value"));



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
        String description = name.getText().trim();
        if(description.isEmpty()){
            Tracker.showIfError("Error", "Please enter item name.");
            return;
        }
        //check length is less than 2 and more than 256 length
        if(!Tracker.nameChecker(description)){
            Tracker.showIfError("Error", "Min description length is 2\nand Max description length is 256.");
            return;
        }

        String serialNumber = serialNumber.getText().trim();


        if(serialNumber.isEmpty()){
            Tracker.showIfError("Error", "Please enter item name.");
            return;
        }

        // check if serial number is 10 chars length
        if(!Tracker.serialNumberLengthChecker(serialNumber)){
            Tracker.showIfError("Error", "Serial Number needs to contain 10 Characters.");
            return;
        }

        //check if serial number is alphanumerical
        if(!Tracker.serialNumberChecker (serialNumber)){
            Tracker.showIfError("Error", "Serial Number shall be Alphanumeric.");
            return;
        }

        //if value is nor entered - show an error
        String valueString = price.getText().trim();
        if(valueString.isEmpty()){
            Tracker.showIfError("Error", "Please enter value.");
            return;
        }
        double value = 0;

        // try ParseDouble to make sure the value is of type double
        try{
            value = Double.parseDouble(valueString);

            //if value is Double parsable but ==0 or <0 then show an error
            if(value <= 0){
                Tracker.showIfError("Error", "Please enter value greater than zero.");
                return;
            }
        }catch (NumberFormatException e){
            Tracker.showIfError("Error", "Please enter a numerical value.");
            return;
        }
        //set the new item
        Item item = new Item();
        item.setName(description);
        item.setSerialNumber(serialNumber);
        item.setPrice(value);

        //check if we have the same serial number
        if(dataList.contains(item)){
            Tracker.showIfError("Error", "You have entered a duplicate Serial Number.\nPlease enter a unique Serial Number");
            return;
        }
        //check item list capacity is full
        if(dataList.size() >= 100){
            Tracker.showIfError("Error", "Max capacity is 100.\nYou can not add anymore inventory items.");
            return;
        }

        // if all is OK add the new item
        dataList.add(item);

        //clear the fields so its ready to enter new item
        name.setText("");
        serialNumber.setText("");
        price.setText("");
    }


    // show the filtered results

    private void filter(){

        final String searchKey = searchItem.getText().toString().trim();

        filteredList.setPredicate(model -> {

            // if nothing in the searchKey filed show everything
            if(searchKey.isEmpty()){
                return true;
            }

            //if nameRadio button is selection look for the matching names
            // show nothing if as we type the value does not match

            if(nameRadio.isSelected()){
                if(!model.getName().trim().toLowerCase().startsWith(searchKey)){
                    return false;
                }

                //if serialRadio button is selection look for the matching serials
                // show nothing if as we type the value does not match
            }else if(serialRadio.isSelected()){
                if(!model.getSerialNumber().trim().toLowerCase().startsWith(searchKey)){
                    return false;
                }

                //if ValueRadio button is selection look for the matching values
                // show nothing if as we type the value does not match
            }else if(priceRadio.isSelected()){
                if(!String.valueOf(model.getPrice()).startsWith(searchKey)){
                    return false;
                }
            }
            // show the matched if we pass the items above
            return true;
        });
    }

    @FXML
    //delete item
    public void deleteItem() {

        // get the item that was selected in the list
        Item item = view.getSelectionModel().getSelectedItem();
        // make sure an item  is selected
        if(item == null)
            return;
        dataList.remove(item);
    }

    @FXML

    // opening a new window for the edit item page
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

    @FXML
    // save function

    public void saveItems() {
        // if no item to be saved then just return
        if(dataList == null || dataList.isEmpty())
            return;

        try{
            FileChooser fileChooser = new FileChooser();

            // Set extension filters for json, txt and html

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Json", "*.json"),
                    new FileChooser.ExtensionFilter("TSV", "*.txt"),
                    new FileChooser.ExtensionFilter("html", "*.html")
            );

            // Show open file dialog
            File file = fileChooser.showSaveDialog(name.getScene().getWindow());

            if (file != null) {
                // get the option the user has chosen
                String extension = fileChooser.getSelectedExtensionFilter().getExtensions().get(0);

                // handle Json alert messages
                if(extension.equals("*.json")){
                    if(saveJson(file)){
                        Tracker.showIfSuccess("Success","Inventory was successfully saved to a Json file");
                    }else{
                        Tracker.showIfError("Error","Failed to save items in Json a Json file");
                    }

                    // handle txt alert messages
                }else if(extension.equals("*.txt")){
                    if(saveTSV(file)){
                        Tracker.showIfSuccess("Success","Inventory was successfully saved to a txt file");
                    }else{
                        Tracker.showIfError("Error","Failed to save items in Json a txt file");
                    }

                    // handle html alert messages
                }else if(extension.equals("*.html")){
                    if(saveHtml(file)){
                        Tracker.showIfSuccess("Success","Inventory was successfully saved to a html file");
                    }else{
                        Tracker.showIfError("Error","Failed to save items in a html file");
                    }
                }
            }
        }catch (Exception e){
            Tracker.showIfError("Error","Fail to save items");
        }
    }

    public ObservableList<Item> getItems(){
        return this.dataList;
    }

    //create a Gson file
    private boolean saveJson(File file){
        try{
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Item>>() {}.getType();
            Writer writer = new FileWriter(file);
            gson.toJson(getItems(), listType, writer);
            writer.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //create a TSV/text file
    private boolean saveTSV(File file){
        try{
            Writer writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            for(Item item : dataList){
                // create a tabular format of each item
                String row = item.getName() + "\t" + item.getSerialNumber() + "\t" + item.getPrice();
                printWriter.println(row);
            }
            writer.close();
            printWriter.close();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    // create a HTML file
    private boolean saveHtml(File file){
        try{
            Writer writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("<html><body><table>");

            for(Item item : dataList){

                printWriter.println("<tr>");
                printWriter.print("<td>");
                printWriter.print(item.getName());
                printWriter.println("</td>");
                printWriter.print("<td>");
                printWriter.print(item.getSerialNumber());
                printWriter.println("</td>");
                printWriter.print("<td>");
                printWriter.print(item.getPrice());
                printWriter.println("</td>");
                printWriter.println("</tr>");
            }
            printWriter.println("</table></body></html>");
            writer.close();
            printWriter.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @FXML

    // load item function

    public void loadItems() {
        try {
            FileChooser fileChooser = new FileChooser();
            // Set extension filter
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Json", "*.json"),
                    new FileChooser.ExtensionFilter("TSV", "*.txt"),
                    new FileChooser.ExtensionFilter("html", "*.html")
            );

            // Show open file dialog
            File file = fileChooser.showOpenDialog(name.getScene().getWindow());


            if (file != null) {
                String extension = fileChooser.getSelectedExtensionFilter().getExtensions().get(0);
                // see which file type was chosen and display the result accordingly

                if(extension.equals("*.json")){
                    List<Item> itemList = readJson(file);
                    if(itemList != null){
                        dataList.clear();
                        dataList.addAll(itemList);
                    }
                }else if(extension.equals("*.txt")){
                    List<Item> itemList = readTSV(file);
                    if(itemList != null){
                        dataList.clear();
                        dataList.addAll(itemList);
                    }
                }else if(extension.equals("*.html")){
                    List<Item> itemList = readHtml(file);
                    if(itemList != null){
                        dataList.clear();
                        dataList.addAll(itemList);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    // read Json function

    private List<Item> readJson(File file){
        try{
            Type listOfMyClassObject = new TypeToken<ArrayList<Item>>() {}.getType();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(file));
            List<Item> itemList = gson.fromJson(reader, listOfMyClassObject);
            return itemList;
        }catch (Exception e){
            return null;
        }
    }

    // read txt file function

    private List<Item> readTSV(File file){
        try{
            List<Item> itemList = new ArrayList<>();
            try (BufferedReader TSVReader = new BufferedReader(new FileReader(file))) {
                String line = null;
                while ((line = TSVReader.readLine()) != null) {

                    // spilt the lines when we see a tab
                    String[] lineItems = line.split("\t");

                    if(lineItems.length != 3)
                        continue;

                    Item item = new Item();
                    item.setName(lineItems[0]);
                    item.setSerialNumber(lineItems[1]);
                    try{
                        item.setPrice(Double.parseDouble(lineItems[2]));
                    }catch (Exception e){
                        continue;
                    }
                    itemList.add(item);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong when processing your file");
            }
            return itemList;
        }catch (Exception e){
            return null;
        }
    }

    // read HTML function

    private List<Item> readHtml(File file){
        try{
            List<Item> itemList = new ArrayList<>();
            Document htmlDocument = Jsoup.parse(file, null);
            Elements elements = htmlDocument.getElementsByTag("tr");
            for(Element element : elements){
                Elements rows = element.getElementsByTag("td");
                Item item = new Item();
                try{
                    item.setName(rows.get(0).text());
                    item.setSerialNumber(rows.get(1).text());
                    item.setPrice(Double.parseDouble(rows.get(2).text()));
                }catch (Exception e){
                    continue;
                }
                itemList.add(item);
            }
            return itemList;
        }catch (Exception e){
            return null;
        }
    }
}
