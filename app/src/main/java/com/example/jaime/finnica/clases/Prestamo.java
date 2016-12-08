package com.example.jaime.finnica.clases;

import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by checho on 16/11/2016.
 */
public class Prestamo extends SugarRecord{
    private String agenteFinanciero;
    private float montoEntrada;
    private Date fecha;
    private String descripcion;
    private int ncuotas;
    private int notificado;
    private int ncuocan;
    private int ultimoMes;
    public Prestamo() {
    }

    public Prestamo(String agenteFinanciero, float montoEntrada, Date fecha, String descripcion, int nCuotas) {
        this.agenteFinanciero = agenteFinanciero;
        this.montoEntrada = montoEntrada;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.ncuotas = nCuotas;

        ultimoMes = 0;
        ncuocan = 0;
        notificado = 0;
    };

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
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getnCuotas() {
        return ncuotas;
    }
    public void setnCuotas(int nCuotas) {
        this.ncuotas = nCuotas;
    }
    public int getUltimoMes() {
        return ultimoMes;
    }
    public void setUltimoMes(int ultimoMes) {
        this.ultimoMes = ultimoMes;
    }
    public int getNcuoCan() {
        return ncuocan;
    }
    public void setNcuoCan(int nCuotasCancel) {
        this.ncuocan = nCuotasCancel;
    }
    public int getNotificado() {
        return notificado;
    }
    public void setNotificado(int tnotificado) {
        this.notificado = tnotificado;
    }
}
