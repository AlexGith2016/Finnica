package com.example.jaime.finnica.clases;

import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by checho on 16/11/2016.
 */
public class Ingresos extends SugarRecord {
    private String descripcion;
    private float monto;
    private Date fechaIngreso;
    private String categoria;

    public Ingresos(String descripcion, float monto, Date fechaIngreso, String categoria) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaIngreso = fechaIngreso;
        this.categoria = categoria;
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
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
