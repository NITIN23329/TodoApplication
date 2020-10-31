package sample;

import datamodel.DataBase;
import datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Controller {
    @FXML
    private ListView<TodoItem> myTodoList;  //contains list of items
    @FXML
    private TextArea myTextArea;    //show long description
    @FXML
    private Label myDeadlineLabel;  //shows deadline
    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize(){
        //adding all todo items to ListView
        myTodoList.getItems().setAll(DataBase.getTodoItemList());
        //only one item will be selected at a time
        myTodoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //fist item in todo list will be default
        myTodoList.getSelectionModel().selectFirst();
        //as the user didn't selected any item , we have to explicitly call handleClickView()
        handleClickListView();

    }
    @FXML
    public void showNewItemDialog(){
        //whenever the dialog model is visible , user will not be able to interact with other part of UI
        Dialog<ButtonType> dialog = new Dialog<>();
        //to get a parent's(borderPane) instance , we need to assign a id to parent
        dialog.initOwner(borderPane.getScene().getWindow());

        try{
            Parent root = FXMLLoader.load(getClass().getResource("TodoItemDialogue.fxml"));
            dialog.getDialogPane().setContent(root);
        }
        catch (IOException e){
            System.out.println("could not load dialogue fxml");
            e.printStackTrace();
        }
        //we are adding inbuilt OK and CANCEL Buttons to dialogPane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    }
    //this eventHandler runs when any item is clicked
    @FXML
    public void handleClickListView(){
        //after user clicked on any item, we will display it's long description and due date
        TodoItem selectedItem = myTodoList.getSelectionModel().getSelectedItem();
        myTextArea.setText(selectedItem.getDetailedDescription());
        myDeadlineLabel.setText(selectedItem.getDeadline());
    }
}
