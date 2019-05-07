package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;

    @FXML
    private ListView toDoListView;

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Mail birdthday card", "Buy a birthday card for Mom",
                LocalDate.of(2019, Month.MAY, 7));
        ToDoItem item2 = new ToDoItem("Study Java Databases", " Finish JavaFX with SQL databases",
                LocalDate.of(2019, Month.MAY, 8));
        ToDoItem item3 = new ToDoItem("Start React Course", "Start on course Kevin gave you",
                LocalDate.of(2019, Month.MAY, 10));
        ToDoItem item4 = new ToDoItem("Email Felicia", "Email Felicia information",
                LocalDate.of(2019, Month.MAY, 7));
        ToDoItem item5 = new ToDoItem("Clean up", "Clean apartment",
                LocalDate.of(2019, Month.MAY, 13));

        //add array of items
        toDoItems = new ArrayList<ToDoItem>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);

        //populate the listview with the items created
        toDoListView.getItems().setAll(toDoItems);
        //select one item at a time
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    //gets the selected item
    @FXML
    public void handleClickListView(){
        ToDoItem item = (ToDoItem) toDoListView.getSelectionModel().getSelectedItem();
        System.out.println("The selected item is " + item);
    }


}
