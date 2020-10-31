package sample;

import datamodel.DataBase;
import datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogueController {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea longDescriptionArea;
    @FXML
    private DatePicker deadlinePicker;

    @FXML
    public void processResult(){
        //whenever ok is pressed in Dialog window
        // we will access user inputed data
        //create a new Todo Item
        //add it to main window
        String shortDescription = shortDescriptionField.getText().trim();
        String longDescription = longDescriptionArea.getText().trim();
        LocalDate deadline = deadlinePicker.getValue();
        TodoItem i = new TodoItem(shortDescription,longDescription,deadline);
        DataBase.getTodoItemList().add(i);

    }
}
