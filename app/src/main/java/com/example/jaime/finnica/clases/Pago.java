package com.example.jaime.finnica.clases;

import com.orm.SugarApp;

import java.util.Date;

/**
 * Created by checho on 16/11/2016.
 */
public class Pago extends SugarApp {
    private String descripcion;
    private float monto;
    private Date fechaPago;

    public Pago(String descripcion, float monto, Date fechaPago) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaPago = fechaPago;
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
}
