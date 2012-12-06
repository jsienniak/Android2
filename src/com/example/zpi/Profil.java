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
    private int id;
    private String nazwa;
    private String swiatlo;
    private String roleta;
    private String woda;
    private boolean wl;

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

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getSwiatlo() {
        return swiatlo;
    }

    public void setSwiatlo(String swiatlo) {
        this.swiatlo = swiatlo;
    }

    public String getRoleta() {
        return roleta;
    }

    public void setRoleta(String roleta) {
        this.roleta = roleta;
    }

    public String getWoda() {
        return woda;
    }

    public void setWoda(String woda) {
        this.woda = woda;
    }

    public boolean isWl() {
        return wl;
    }

    public void setWl(boolean wl) {
        this.wl = wl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
