package com.example.jaime.finnica.clases;


import com.orm.SugarRecord;

/**
 * Created by domestic on 20/11/2016.
 */

public class Conf extends SugarRecord {
    private String contra;

    public Conf(String contra) {
        this.setContra(contra);

    }

    public Conf(){

    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
