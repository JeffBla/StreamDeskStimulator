package com.example.demo;

import com.example.demo.info.InfoClass;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class StaticForRunning {

    public static int appNum= 5; // Now, only can open 4 app

    public static void main(String[] args) {
        ObjectOutputStream oos = null;

        // For infoTreeTable
        {
            InfoClass group_1 = new InfoClass(-1, "group_1", "");
            InfoClass group_2 = new InfoClass(-2, "group_2", "");
            InfoClass group_3 = new InfoClass(-3, "group_3", "");
            InfoClass group_4 = new InfoClass(-4, "group_4", "");
            InfoClass group_5 = new InfoClass(-5, "group_5", "");

            List<InfoClass> groupList = Arrays.asList(group_1, group_2, group_3, group_4, group_5);
            Integer childNum = 0;

            try {
                oos = new ObjectOutputStream(new FileOutputStream("info_record"));

                for (InfoClass info : groupList) {
                    oos.writeObject(info);
                    oos.writeObject(childNum);
                }

                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // For AppPathFinding
        {
            try {
                oos = new ObjectOutputStream(new FileOutputStream("FileDirectoryPath_record"));
                for(int i=0; i<appNum; i++){
                    oos.writeObject("");
                }

                oos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
