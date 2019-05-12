package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoData;
import com.willflem.ToDoListFX.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
@FXML
    private TextField shortDescriptionField;
@FXML
    private TextArea detailsAreaField;
@FXML
    private DatePicker deadlinePicker;

//gather users input create to do item then add it to the to do item instance
public void processResults(){
    String shortDescription = shortDescriptionField.getText().trim();
    String details = detailsAreaField.getText().trim();
    LocalDate deadlineValue = deadlinePicker.getValue();

    ToDoData.getInstance().addToDoItem(new ToDoItem(shortDescription, details, deadlineValue));
}

}
