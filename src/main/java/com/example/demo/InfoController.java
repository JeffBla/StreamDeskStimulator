package com.example.demo;

import com.example.demo.info.InfoClass;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.util.function.Predicate;

public class InfoController implements Initializable {

    @FXML
    public TreeTableView<InfoClass> treeTableView;
    @FXML
    public TextField enterGroupTextField;
    @FXML
    public TextField enterIndexTextField;
    @FXML
    public TreeTableColumn<InfoClass, Number> colIndex;
    @FXML
    public TreeTableColumn<InfoClass, String> col_info;
    @FXML
    public TreeTableColumn<InfoClass, Hyperlink> col_hyperlink;
    @FXML
    public TreeTableColumn<InfoClass, TextField> col_textField;

    // group index from -1
    TreeItem<InfoClass> group_1, group_2, group_3, group_4, group_5;

    List<TreeItem<InfoClass>> groupList;

    TreeItem<InfoClass> root = new TreeItem<>(new InfoClass(0, "root", "none"));

    Hyperlink tmpHyperlink;
    TextField tmpTextField;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    private Stage stage;
    private Scene appScene;

    @Override
    public void initialize(URL var1, ResourceBundle var2) {

        load();

        oos = null;
        ois = null;

        colIndex.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InfoClass, Number> param) -> param.getValue().getValue().getIndexProperty()
        );
        col_info.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InfoClass, String> param) -> param.getValue().getValue().getInfo_nameProperty()
        );
        col_hyperlink.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<InfoClass, Hyperlink>, ObservableValue<Hyperlink>>() {
            @Override
            public ObservableValue<Hyperlink> call(TreeTableColumn.CellDataFeatures<InfoClass, Hyperlink> param) {
                tmpHyperlink = new Hyperlink(param.getValue().getValue().getInfo_URLProperty().getValue());
                tmpHyperlink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Desktop.getDesktop().browse(new URI(((Hyperlink) event.getSource()).getText()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
//                        System.out.println(event.getSource());
                    }
                });
//                System.out.println(tmpHyperlink.getText());
                return (new SimpleObjectProperty<Hyperlink>(tmpHyperlink));
            }
        });

        col_textField.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<InfoClass, TextField>, ObservableValue<TextField>>() {
            @Override
            public ObservableValue<TextField> call(TreeTableColumn.CellDataFeatures<InfoClass, TextField> param) {
                if (param.getValue().getValue().getIndexProperty().getValue() > 0) {
                    tmpTextField = new TextField(param.getValue().getValue().getInfo_URLProperty().getValue());
                    tmpTextField.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            param.getValue().getValue().setInfo_URLProperty(((TextField) event.getSource()).getText());
                        }
                    });
                    return (new SimpleObjectProperty<TextField>(tmpTextField));
                }
                return null;
            }
        });

        col_info.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        treeTableView.setEditable(true);
        treeTableView.setShowRoot(false);
        treeTableView.getSelectionModel().setCellSelectionEnabled(true);

        treeTableView.setRoot(root);

    }

    private void load() {
        groupList = Arrays.asList(group_1, group_2, group_3, group_4, group_5);

        try {
            ois = new ObjectInputStream(new FileInputStream("info_record"));

            Integer childNum;
            for (TreeItem<InfoClass> group : groupList) {
                group = new TreeItem<>((InfoClass) ois.readObject());
                childNum = (Integer) ois.readObject();
                for (int i = 0; i < childNum; i++) {
                    group.getChildren().add(new TreeItem<>((InfoClass) ois.readObject()));
                }
                root.getChildren().add(group);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("EOL");
            e.printStackTrace();
        }
    }

    public void onEditCommitColInfo(TreeTableColumn.CellEditEvent<String, String> event) {
        TreeItem<InfoClass> currentEditingInfo = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
//        System.out.println(currentEditingInfo.getValue().getInfo_nameProperty());
        currentEditingInfo.getValue().setInfo_nameProperty(event.getNewValue());
//        System.out.println(currentEditingInfo.getValue().getInfo_nameProperty());
    }

    public void onSaveButtonClick(ActionEvent event) {
        try {
            oos = new ObjectOutputStream(new FileOutputStream("info_record"));
            for (TreeItem<InfoClass> group : root.getChildren()) {
                oos.writeObject(group.getValue());
                oos.writeObject((Integer) group.getChildren().size());
                for (TreeItem<InfoClass> child : group.getChildren()) {
                    oos.writeObject(child.getValue());
                }
            }

            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlusButtonClick(ActionEvent event) {
        String field_groupNum = enterGroupTextField.getText();
        String field_indexNum = enterIndexTextField.getText();

        if (!field_indexNum.equals("") && !field_groupNum.equals("")) {
            try {
                int num1 = Integer.parseInt(field_groupNum);
                int num2 = Integer.parseInt(field_indexNum);
                TreeItem<InfoClass> item = new TreeItem<>(new InfoClass(num2 + 1, "...", "www..com"));

                TreeItem<InfoClass> group = root.getChildren().filtered(new Predicate<TreeItem<InfoClass>>() {
                    @Override
                    public boolean test(TreeItem<InfoClass> infoClassTreeItem) {
                        return infoClassTreeItem.getValue().getIndexProperty().getValue() == num1;
                    }
                }).get(0);

                group.getChildren().add(item);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // later add more...
            }
        }
    }

    public void ongotoAppSessionButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("streamDesk-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appScene = new Scene(root);
        appScene.getStylesheets().add(getClass().getResource("AppButton.css").toExternalForm());
        stage.setScene(appScene);
        stage.show();

    }
}
