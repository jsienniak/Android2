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
    int czasStart;
    int czasStop;
    String valStart;
    String valStop;
    String dni;
    int modul;
    int port;
    boolean wl;
    public Harmonogram(){

    }

    public Harmonogram(int czasStart, int czasStop, String valStart, String valStop, int port, int modul, String dni, boolean  wl) {
        this.czasStart = czasStart;
        this.czasStop = czasStop;
        this.valStart = valStart;
        this.valStop = valStop;
        this.port = port;
        this.modul = modul;
        this.dni = dni;
        this.wl=wl;
    }
}
