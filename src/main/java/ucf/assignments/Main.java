/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Tanushka Kommoju
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    } //main call


    @Override
    public void start(Stage stage) {
        try {
            //access the file needed to load FXML
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ucf/assignments/new.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Tracker");
            stage.setScene(scene); //attach scene to stage
            scene.getStylesheets().add("ucf/assignments/");
            stage.show(); //show stage
        } catch (Exception ignored) {

        }
    }
}


