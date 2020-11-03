package sample;

import datamodel.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }
    //called just before launching UI javafx program
    //before start method call
    @Override
    public void init(){
        try {
//              either use FileWriter to write the file or write in text file itself with proper format
//              DataBase.writeFile();
                DataBase.readFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // executed when user cuts/stops UI
    @Override
    public void stop() {
        try {
            DataBase.writeFile();
        }
       catch (IOException e){
            e.printStackTrace();
       }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
