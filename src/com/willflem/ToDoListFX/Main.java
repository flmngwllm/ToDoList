package com.willflem.ToDoListFX;

import com.willflem.ToDoListFX.datamodel.ToDoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //store items when we stop application
    @Override
    public void stop() throws Exception {
        try {
            //How we access the singleton
            ToDoData.getInstance().storeTodoItems();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //loads items from text file we created
    @Override
    public void init() throws IOException {
        try {
            //How we access the singleton
            ToDoData.getInstance().loadToDoItems();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
