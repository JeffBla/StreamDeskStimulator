package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StreamDeskController implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene appScene;
    private Scene infoScene;
    private Scene asignerScene;
    private ObjectInputStream ois;
    private String SpotifyDirectoryPath;
    private String PhotoShopDirectoryPath;
    private String PremiereDirectoryPath;
    private String FormatFactoryDirectoryPath;
    private String ZoomItDirectoryPath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ois = new ObjectInputStream(new FileInputStream("FileDirectoryPath_record"));
            SpotifyDirectoryPath = (String) ois.readObject();
            PhotoShopDirectoryPath =(String) ois.readObject();
            PremiereDirectoryPath =(String) ois.readObject();
            FormatFactoryDirectoryPath =(String) ois.readObject();
            ZoomItDirectoryPath =(String) ois.readObject();

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void OpenSpotifyButton(ActionEvent event) throws IOException {
        List<String> commands = new ArrayList<String>();
        String commandString = SpotifyDirectoryPath+"/"+"Spotify.exe";
        commands.add(commandString);

        ProcessBuilder pb = new ProcessBuilder(commands);
        // "C:\\Users\\User\\AppData\\Local\\Microsoft\\WindowsApps"
        if (!SpotifyDirectoryPath.equals("")) {
            pb.start();
        }
        else{
            System.out.println("ERROR");
        }
    }
    public void OpenPhotoShopButton(ActionEvent event) throws IOException {
        List<String> commands = new ArrayList<String>();
        String commandString = PhotoShopDirectoryPath+"/"+"Photoshop.exe";
        commands.add(commandString);


        ProcessBuilder pb = new ProcessBuilder(commands);
        // "C:/Program Files/Adobe/Adobe Photoshop 2021"
        if (!PhotoShopDirectoryPath.equals("")) {
            System.out.println(commandString);
             Process process = pb.start();

//            try (var reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()))) {
//
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//
//            }
        }
        else{
            System.out.println("ERROR");
        }
    }
    public void OpenPremiereButton(ActionEvent event) throws IOException {
        List<String> commands = new ArrayList<String>();
        String commandString = PremiereDirectoryPath+"/"+"Adobe Premiere Pro.exe";
        commands.add(commandString);

        ProcessBuilder pb = new ProcessBuilder(commands);
        // "C:\\Program Files\\Adobe\\Adobe Premiere Pro 2021"
        if (!PremiereDirectoryPath.equals("")) {
            pb.directory(new File(PremiereDirectoryPath));
            pb.start();
        }
        else{
            System.out.println("ERROR");
        }

        System.out.println(PremiereDirectoryPath);
    }
    public void OpenFormat_FactoryButton(ActionEvent event) throws IOException {
        List<String> commands = new ArrayList<String>();
        String commandString = FormatFactoryDirectoryPath+"/"+"FormatFactory.exe";
        commands.add(commandString);

        ProcessBuilder pb = new ProcessBuilder(commands);
        // "C:\\Program Files (x86)\\FormatFactory"
        if (!FormatFactoryDirectoryPath.equals("")) {
            pb.directory(new File(FormatFactoryDirectoryPath));
            pb.start();
        }
        else{
            System.out.println("ERROR");
        }

        System.out.println(FormatFactoryDirectoryPath);
    }
    public void OpenZoomItButton(ActionEvent event) throws IOException {
        List<String> commands = new ArrayList<String>();
        String commandString = ZoomItDirectoryPath+"/"+"ZoomIt.exe";
        commands.add(commandString);

        ProcessBuilder pb = new ProcessBuilder(commands);
        // "C:\\ZoomIt"
        if (!ZoomItDirectoryPath.equals("")) {
            pb.directory(new File(ZoomItDirectoryPath));
            pb.start();
        }
        else{
            System.out.println("ERROR");
        }

        System.out.println(ZoomItDirectoryPath);
    }

    public void AppSectionNextButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("infoRemember-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        infoScene = new Scene(root);
        infoScene.getStylesheets().add(getClass().getResource("infoSetting.css").toExternalForm());
        stage.setScene(infoScene);
        stage.show();
    }

    public void DirectorySectionNextButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fileDirectoryAsigner-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        asignerScene = new Scene(root);
        asignerScene.getStylesheets().add(getClass().getResource("fileDirectoryAsignerSetting.css").toExternalForm());
        stage.setScene(asignerScene);
        stage.show();
    }
}
