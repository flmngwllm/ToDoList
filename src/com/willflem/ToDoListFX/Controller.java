package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoData;
import com.willflem.ToDoListFX.datamodel.ToDoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList<ToDoItem> filteredList;

    private Predicate<ToDoItem> wantAllItems;
    private Predicate<ToDoItem> wantTodaysItems;


    public void initialize() {

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        //add the event handler when button is clicked pressed
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // getting the item that is currently selected in the list
                ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

listContextMenu.getItems().addAll(deleteMenuItem);
        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if(newValue != null){
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    // pattern of the date you want displayed
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });


        wantAllItems = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem toDoItem) {
                return true;
            }
        };


        wantTodaysItems = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem toDoItem) {
                return (toDoItem.getDeadline().equals(LocalDate.now()));
            }
        };

        //provide items we want to filter and call the test method for every item in the list that has been passed to it
        filteredList = new FilteredList<ToDoItem>(ToDoData.getInstance().getToDoItems(), wantAllItems);

        // sorting items by deadline
        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(filteredList,
                new Comparator<ToDoItem>() {
                    @Override
                    public int compare(ToDoItem o1, ToDoItem o2) {
                        return o1.getDeadline().compareTo(o2.getDeadline());
                    }
                });




        //bind the listview with the items in the sortedlist
//        toDoListView.setItems(ToDoData.getInstance().getToDoItems());
        toDoListView.setItems(sortedList);
        //select one item at a time
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //selects the first item
        toDoListView.getSelectionModel().selectFirst();

        //cell factory
        toDoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> toDoItemListView) {
                ListCell<ToDoItem> cell = new ListCell<ToDoItem>(){
                    @Override
                    protected void updateItem(ToDoItem item, boolean b) {
                        super.updateItem(item, b);
                        if(b) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))){
                               setTextFill(Color.RED);
                            } else if(item.getDeadline().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.BLUE);
                            }
                        }
                    }
                };
                // listener for when the cell is empty
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if(isNowEmpty) {
                                cell.setContextMenu(null);
                            }else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
                return cell;
            }
        });

    }

    @FXML
    public void showNewItemDialog(){
        // create a new instance of dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New ToDo Item");
        dialog.setHeaderText("Use this dialog to create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        // catch the IOexception
        try{
            //get controller from fxml loader instance
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch(IOException e){
            System.out.println("Couldnt load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            // storing the value that was return from the process results method
            ToDoItem newItem = controller.processResults();
            // grabs the list of to do items and saves them on the left borderpane
            toDoListView.getSelectionModel().select(newItem);

        }
    }

// gets the selected item when the key is pressed
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            if(keyEvent.getCode().equals(KeyCode.DELETE)){
                deleteItem(selectedItem);
            }
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

    public void deleteItem(ToDoItem item){
        //creating a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //set properties to see what the dialog will display
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete item" + item.getShortDescription());
        alert.setContentText("Are you sure? Press OK to continue, or cancel to back out.");
        //Show the dialog
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            ToDoData.getInstance().deleteToDoItem(item);
        }
    }

    @FXML
    //filter which items display and which items are hidden by date
    public void handleFilterButton(){
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if(filterToggleButton.isSelected()) {
            filteredList.setPredicate(wantTodaysItems);
            if(filteredList.isEmpty()){
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            } else if(filteredList.contains(selectedItem)){
                toDoListView.getSelectionModel().select(selectedItem);
            } else {
                toDoListView.getSelectionModel().selectFirst();
            }
        } else{
            filteredList.setPredicate(wantAllItems);
            toDoListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }
}
