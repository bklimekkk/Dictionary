package com.example.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage.setTitle("Translator");
        stage.setScene(new Scene(root, 600, 440));
//        root.getStylesheets().add("sample/style.css");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}