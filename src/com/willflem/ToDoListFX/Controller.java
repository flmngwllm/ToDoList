package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoItem;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Mail birdthday card", "Buy a birthday card for Mom",
                LocalDate.of(2019, Month.MAY, 25));
    }
}
