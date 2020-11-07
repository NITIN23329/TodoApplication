package sample;

import datamodel.DataBase;
import datamodel.TodoItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private ContextMenu myContextMenu;  //comes up when we right click a Todo item

    @FXML
    public void initialize(){
        myContextMenu=new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        myContextMenu.getItems().add(deleteMenuItem);
        // when we select Delete option in drop down , below even will be executed
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TodoItem item = myTodoList.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        //adding all todo items to ListView
        myTodoList.getItems().setAll(DataBase.getTodoItemList());
        //only one item will be selected at a time
        myTodoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //fist item in todo list will be default
        myTodoList.getSelectionModel().selectFirst();
        //as the user didn't selected any item , we have to explicitly call handleClickView()
        handleClickListView();

        // set the cellFactory of ListView
        //cellFactory is associated with each element in listView
        //List cell is the list of rows in ListView which contains Short Description of TodoItem
        // this will color those cells in red whose deadline is today
        //this will color those cells in orange whose deadline is tomorrow
        //we will color green to chose cells whose deadline is passed
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
                           else if(ld.equals(LocalDate.now().plusDays(1)))
                               setTextFill(Color.ORANGE);
                           else if(ld.compareTo(LocalDate.now())<0)
                               setTextFill(Color.GREEN);
                         //  else setTextFill(Color.BLACK);
                       }
                   }
               };
               //adding our contextMenu to cell factory using lambda expressing.
               cell.emptyProperty().addListener(
                       (obs,wasEmpty,nowEmpty) ->{
                           if(nowEmpty)cell.setContextMenu(null);
                           else cell.setContextMenu(myContextMenu);
                       }
               );
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

        //adding new todo item if user press OK
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            DialogueController dialogueController = dialogLoader.getController();
            // newly created todo item is added to Database (TodoData.txt) and also to ListView
            TodoItem newItem=dialogueController.processResult();
            myTodoList.getItems().add(newItem);
            // it will be selected when user returns to Main window
            myTodoList.getSelectionModel().select(newItem);
            //display info of newly created todo item
            handleClickListView();
            System.out.println("OK pressed");
        }
        else{
            System.out.println("CANCEL pressed");
        }
    }
    // this eventHandler invoked  when user press delete key  , then selected todo item will be deleted
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        TodoItem item = myTodoList.getSelectionModel().getSelectedItem();
        if(item!=null){
            if(keyEvent.getCode().equals(KeyCode.DELETE))
                deleteItem(item);
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

    //deleteItem is a controller which when a user right click on any todo item , it gives a option to delete that particular todo item
    // then user chooses to delete  , a conformation tab will be opened for conformation, asking to select Ok/Cancel . if ok pressed that todo item will be deleted
    public void deleteItem(TodoItem item){
        // it's a conformation before deletion
        Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete Item : "+item.toString());
        alert.setContentText("Are you sure? Press OK/CANCEL.");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            System.out.println(item+" is deleted");
            DataBase.delete(item);
            myTodoList.getItems().remove(item);
            myTodoList.getSelectionModel().selectFirst();
            handleClickListView();
        }
        else System.out.println(item+" is not deleted");
    }

}
