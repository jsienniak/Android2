package com.example.zpi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 27.11.12
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class Harmonogram implements Serializable {

    private int id;
    private String czasStart;
    private String czasStop;
    private String valStart;
    private String valStop;
    private String dni;
    private int modul;
    private int port;
    private boolean wl;

    public Harmonogram(){

    }

    public Harmonogram(int id, String czasStart, String czasStop, String valStart, String valStop, int port, int modul, String dni, boolean  wl) {
        this.id=id;
        this.czasStart = czasStart;
        this.czasStop = czasStop;
        this.valStart = valStart;
        this.valStop = valStop;
        this.port = port;
        this.modul = modul;
        this.dni = dni;
        this.wl=wl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCzasStart() {
        return czasStart;
    }

    public void setCzasStart(String czasStart) {
        this.czasStart = czasStart;
    }

    public String getCzasStop() {
        return czasStop;
    }

    public void setCzasStop(String czasStop) {
        this.czasStop = czasStop;
    }

    public String getValStart() {
        return valStart;
    }

    public void setValStart(String valStart) {
        this.valStart = valStart;
    }

    public String getValStop() {
        return valStop;
    }

    public void setValStop(String valStop) {
        this.valStop = valStop;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getModul() {
        return modul;
    }

    public void setModul(int modul) {
        this.modul = modul;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isWl() {
        return wl;
    }

    public void setWl(boolean wl) {
        this.wl = wl;
    }
}
