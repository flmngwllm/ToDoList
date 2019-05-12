package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoData;
import com.willflem.ToDoListFX.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observableValue, ToDoItem oldValue, ToDoItem newValue) {
                if(newValue != null){
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    // pattern of the date you want displayed
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        //populate the listview with the items created
        toDoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
        //select one item at a time
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //selects the first item
        toDoListView.getSelectionModel().selectFirst();

    }

    @FXML
    public void showNewItemDialog(){
        // create a new instance of dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        // catch the IOexception
        try{
            Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.fxml"));
            dialog.getDialogPane().setContent(root);
            
        }catch(IOException e){
            System.out.println("Couldnt load the dialog");
            e.printStackTrace();
            return;
        }
    }

    //gets the selected item
    @FXML
    public void handleClickListView(){
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());
//        System.out.println("The selected item is " + item);
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        //gets the deadline string
//        sb.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(sb.toString());
    }
}
