package sample;

import datamodel.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogueController {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea longDescriptionArea;
    @FXML
    private DatePicker deadlinePicker;

    @FXML
    public void processResult() {
        //whenever ok is pressed in Dialog window
        // we will access user inputed data
        //create a new Todo Item
        //add it to main window
        // when ever user input a new Todo item , we need to add it to our TodoData.txt file so that next time UI runs , it will be read newly added todo item
        DataBase.addItem(shortDescriptionField.getText().trim(),longDescriptionArea.getText().trim(),deadlinePicker.getValue());
    }
}
