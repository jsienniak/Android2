package com.example.zpi;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 04.12.12
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
public class Profil implements Serializable{
    int id;
    String nazwa;
    String swiatlo;
    String roleta;
    String woda;
    boolean wl;

    public Profil(){

    }
    public Profil(int id, String nazwa, String swiatlo, String roleta, String woda, boolean wl) {
        this.id = id;
        this.nazwa = nazwa;
        this.swiatlo = swiatlo;
        this.roleta = roleta;
        this.woda = woda;
        this.wl = wl;
    }
}
