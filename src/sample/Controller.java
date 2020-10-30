package sample;

import datamodel.DataBase;
import datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML
    private ListView<TodoItem> myTodoList;  //contains list of items
    @FXML
    private TextArea myTextArea;    //show long description
    @FXML
    private Label myDeadlineLabel;  //shows deadline

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
    //this eventHandler runs when any item is clicked
    @FXML
    public void handleClickListView(){
        //after user clicked on any item, we will display it's long description and due date
        TodoItem selectedItem = myTodoList.getSelectionModel().getSelectedItem();
        myTextArea.setText(selectedItem.getDetailedDescription());
        myDeadlineLabel.setText(selectedItem.getDeadline());
    }
}
