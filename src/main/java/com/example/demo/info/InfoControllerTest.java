package com.example.demo.info;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.util.function.Predicate;

public class InfoControllerTest implements Initializable {

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

    // item index from 1
    TreeItem<InfoClass> item_1 = new TreeItem<>(new InfoClass(1, "google", "www.google.com"));
    TreeItem<InfoClass> item_2 = new TreeItem<>(new InfoClass(2, "facebook", "www.facebook.com"));

    // group index from -1
    TreeItem<InfoClass> group_1 = new TreeItem<>(new InfoClass(-1, "group_1", ""));
    TreeItem<InfoClass> group_2 = new TreeItem<>(new InfoClass(-2, "group_2", ""));
    TreeItem<InfoClass> group_3 = new TreeItem<>(new InfoClass(-3, "group_3", ""));
    TreeItem<InfoClass> group_4 = new TreeItem<>(new InfoClass(-4, "group_4", ""));
    TreeItem<InfoClass> group_5 = new TreeItem<>(new InfoClass(-5, "group_5", ""));

    TreeItem<InfoClass> root = new TreeItem<>(new InfoClass(0, "root", "none"));

    Hyperlink tmpHyperlink;
    TextField tmpTextField;

    @Override
    public void initialize(URL var1, ResourceBundle var2) {

        group_1.getChildren().addAll(item_1, item_2);
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
                if(param.getValue().getValue().getIndexProperty().getValue()>0) {
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

        root.getChildren().addAll(group_1,group_2,group_3,group_4,group_5);

        treeTableView.setRoot(root);

    }

    public void onEditCommitColInfo(TreeTableColumn.CellEditEvent<String, String> event) {
        TreeItem<InfoClass> currentEditingInfo = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
//        System.out.println(currentEditingInfo.getValue().getInfo_nameProperty());
        currentEditingInfo.getValue().setInfo_nameProperty(event.getNewValue());
//        System.out.println(currentEditingInfo.getValue().getInfo_nameProperty());
    }

    public void onSaveButtonClick(ActionEvent event) {

    }

    public void onPlusButtonClick(ActionEvent event) {
        String field_groupNum = enterGroupTextField.getText();
        String field_indexNum = enterIndexTextField.getText();

        if (!field_indexNum.equals("") && !field_groupNum.equals("")) {
            try {
                int num1 = Integer.parseInt(field_groupNum);
                int num2 = Integer.parseInt(field_indexNum);
                TreeItem<InfoClass> item = new TreeItem<>(new InfoClass(num2 + 1, "facebook", "www.facebook.com"));

                System.out.println(num1);

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

}
