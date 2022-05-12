package com.example.demo.info;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class InfoClass implements Serializable {
    private int index;
    private String info_name;
    private String info_URL;

    public InfoClass(int index, String name, String url) {
        this.index = index;
        info_name = name;
        info_URL = url;
    }

    public SimpleStringProperty getInfo_URLProperty() {
        return new SimpleStringProperty(info_URL);
    }

    public void setInfo_URLProperty(String info_URL) {
        this.info_URL = info_URL;
    }

    public SimpleStringProperty getInfo_nameProperty() {
        return new SimpleStringProperty(info_name);
    }

    public void setInfo_nameProperty(String info_name) {
        this.info_name = info_name;
    }

    public SimpleIntegerProperty getIndexProperty() {
        return new SimpleIntegerProperty(index);
    }

    public void setIndexProperty(int index) {
        this.index = index;
    }
}
