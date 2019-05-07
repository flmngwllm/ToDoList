package com.willflem.ToDoListFX.datamodel;

import java.time.LocalDate;

//adding items for to do list
public class ToDoItem {
    private String shortDescription;
    private String details;
    private LocalDate deadline;

    public ToDoItem(String shortDescription, String details, LocalDate deadline) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
    }
}
