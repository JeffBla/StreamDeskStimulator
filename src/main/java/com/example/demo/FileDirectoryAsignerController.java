package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FileDirectoryAsignerController implements Initializable {

    @FXML
    public TextField SpotifyTextField;
    @FXML
    public TextField PhotoshopTextField;
    @FXML
    public TextField PremiereTextField;
    @FXML
    public TextField FormatFactoryTextField;
    @FXML
    public TextField ZoomItTextField;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private Stage stage;
    private Scene appScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ois = new ObjectInputStream(new FileInputStream("FileDirectoryPath_record"));
            SpotifyTextField.setText((String) ois.readObject());
            PhotoshopTextField.setText((String) ois.readObject());
            PremiereTextField.setText((String) ois.readObject());
            FormatFactoryTextField.setText((String) ois.readObject());
            ZoomItTextField.setText((String) ois.readObject());

            ois.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onSaveButtonClick_FileDirectorySession(ActionEvent event) {
        String SpotifyPath = SpotifyTextField.getText();
        String PhotoshopPath = PhotoshopTextField.getText();
        String PremierePath = PremiereTextField.getText();
        String FormatFactoryPath = FormatFactoryTextField.getText();
        String ZoomItPath = ZoomItTextField.getText();

        try {
            oos = new ObjectOutputStream(new FileOutputStream("FileDirectoryPath_record"));
            oos.writeObject(SpotifyPath);
            oos.writeObject(PhotoshopPath);
            oos.writeObject(PremierePath);
            oos.writeObject(FormatFactoryPath);
            oos.writeObject(ZoomItPath);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAppSectionButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("streamDesk-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appScene = new Scene(root);
        appScene.getStylesheets().add(getClass().getResource("AppButton.css").toExternalForm());
        stage.setScene(appScene);
        stage.show();
    }
}
