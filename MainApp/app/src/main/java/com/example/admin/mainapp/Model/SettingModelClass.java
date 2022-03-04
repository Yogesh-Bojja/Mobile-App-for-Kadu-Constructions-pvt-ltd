package com.example.admin.mainapp.Model;

/**
 * Created by Admin on 20-01-2018.
 */

public class SettingModelClass {
    private int rate1,rate2,plates;

    public SettingModelClass() {

    }

    public SettingModelClass(int rate1, int rate2, int plates) {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.plates = plates;
    }

    public int getRate1() {
        return rate1;
    }

    public int getRate2() {
        return rate2;
    }

    public int getPlates() {
        return plates;
    }
}
