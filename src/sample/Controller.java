package sample;

import datamodel.DataBase;
import datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class Controller {
    @FXML
    private ListView<TodoItem> myTodoList;  //contains list of items
    @FXML
    private TextArea myTextArea;    //show long description
    @FXML
    private Label myDeadlineLabel;  //shows deadline
    @FXML
    private BorderPane borderPane;  //refer to border pane

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

        // set the cellFactory of ListView
        //List cell is the list of rows in ListView which contains Short Description of TodoItem
        // this will color those cells in red whose deadline is today
       myTodoList.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
           @Override
           public ListCell<TodoItem> call(ListView<TodoItem> todoItemListView) {
               ListCell<TodoItem> cell = new ListCell<>(){
                   @Override
                   protected void updateItem(TodoItem item, boolean empty) {
                       super.updateItem(item, empty);
                       if(empty)setText(null);
                       else {
                           setText(item.toString());
                           LocalDate ld =  item.getLocalDate(); //getting local date of each todo item
                           if(ld.equals(LocalDate.now()))
                               setTextFill(Color.RED);
                       }
                   }
               };
               return cell;
           }
       });
    }
    @FXML
    public void showNewItemDialog(){
        //whenever the dialog model is visible , user will not be able to interact with other part of UI
        Dialog<ButtonType> dialog = new Dialog<>();
        //to get a parent's(borderPane) instance , we need to assign a id to parent
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Add new Todo Item");
        dialog.setHeaderText("Use this dialog to add a new Todo item.");
        FXMLLoader dialogLoader = new FXMLLoader();
        dialogLoader.setLocation(getClass().getResource("TodoItemDialogue.fxml"));
        try{
            dialog.getDialogPane().setContent(dialogLoader.load());
        }
        catch (IOException e){
            System.out.println("could not load dialogue fxml");
            e.printStackTrace();
        }
        //we are adding inbuilt OK and CANCEL Buttons to dialogPane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            DialogueController dialogueController = dialogLoader.getController();
            dialogueController.processResult();
            System.out.println("OK pressed");
        }
        else{
            System.out.println("CANCEL pressed");
        }
    }
    //this eventHandler runs when any item is clicked
    @FXML
    public void handleClickListView(){
        //after user clicked on any item, we will display it's long description and due date
        TodoItem selectedItem = myTodoList.getSelectionModel().getSelectedItem();
        myTextArea.setText(selectedItem.getDetailedDescription());
        myDeadlineLabel.setText(selectedItem.getDeadline());
        //show event is a non-blocking event. It will show these 2 buttons and immediately return to main window
        //show+wait is a blocking even. It will show buttons and wait for user interaction with dialog while main window remains suspended
    }
}
