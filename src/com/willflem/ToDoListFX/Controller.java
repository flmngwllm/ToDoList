package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoItem;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;

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

    }
}
