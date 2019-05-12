package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoData;
import com.willflem.ToDoListFX.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

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

    public void initialize() {
//        ToDoItem item1 = new ToDoItem("Mail birdthday card", "Buy a birthday card for Mom",
//                LocalDate.of(2019, Month.MAY, 7));
//        ToDoItem item2 = new ToDoItem("Study Java Databases", " Finish JavaFX with SQL databases",
//                LocalDate.of(2019, Month.MAY, 8));
//        ToDoItem item3 = new ToDoItem("Start React Course", "Start on course Kevin gave you",
//                LocalDate.of(2019, Month.MAY, 10));
//        ToDoItem item4 = new ToDoItem("Email Felicia", "Email Felicia information",
//                LocalDate.of(2019, Month.MAY, 7));
//        ToDoItem item5 = new ToDoItem("Clean up", "Clean apartment",
//                LocalDate.of(2019, Month.MAY, 13));
//
//        //add array of items
//        toDoItems = new ArrayList<ToDoItem>();
//        toDoItems.add(item1);
//        toDoItems.add(item2);
//        toDoItems.add(item3);
//        toDoItems.add(item4);
//        toDoItems.add(item5);
//
//        //create file
//        ToDoData.getInstance().setToDoItems(toDoItems);

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
