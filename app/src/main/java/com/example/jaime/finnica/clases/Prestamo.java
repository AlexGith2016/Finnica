package com.example.jaime.finnica.clases;

import com.orm.SugarApp;

import java.util.Date;

/**
 * Created by checho on 16/11/2016.
 */
public class Prestamo extends SugarApp{
    private String agenteFinanciero;
    private float montoEntrada;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;
    private Pago pago;

    public Prestamo(String agenteFinanciero, float montoEntrada, Date fechaInicio, Date fechaFin, String descripcion) {
        this.agenteFinanciero = agenteFinanciero;
        this.montoEntrada = montoEntrada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }

    public String getAgenteFinanciero() {
        return agenteFinanciero;
    }
    public void setAgenteFinanciero(String agenteFinanciero) {
        this.agenteFinanciero = agenteFinanciero;
    }
    public float getMontoEntrada() {
        return montoEntrada;
    }
    public void setMontoEntrada(float montoEntrada) {
        this.montoEntrada = montoEntrada;
    }
    public Date getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Pago getPago() {
        return pago;
    }
    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
