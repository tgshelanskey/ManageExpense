package com.example.dataObject;

/**
 * Created by woodw on 9/30/2017.
 */

//Shelanskey US4 - data object to hold settings values
public class Setting {
    private int id;
    private String setting_name;
    private String setting_value;

    public Setting(int id, String setting_name, String setting_value) {
        this.id = id;
        this.setting_name = setting_name;
        this.setting_value = setting_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetting_name() {
        return setting_name;
    }

    public void setSetting_name(String setting_name) {
        this.setting_name = setting_name;
    }

    public String getSetting_value() {
        return setting_value;
    }

    public void setSetting_value(String setting_value) {
        this.setting_value = setting_value;
    }

}
