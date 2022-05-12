package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StreamDeskStimulation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("streamDesk-view.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("AppButton.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
