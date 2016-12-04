package com.example.jaime.finnica.clases;

import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by checho on 16/11/2016.
 */
public class Gasto extends SugarRecord {
    private String descripcion;
    private float monto;
    private Date fechaGasto;

    public Gasto(String descripcion, float monto, Date fechaGasto) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaGasto = fechaGasto;
    }

    public Gasto() {
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public float getMonto() {
        return monto;
    }
    public void setMonto(float monto) {
        this.monto = monto;
    }
    public Date getFechaGasto() {
        return fechaGasto;
    }
    public void setFechaGasto(Date fechaGasto) {
        this.fechaGasto = fechaGasto;
    }
}
