package com.example.zpi.communication;

import com.example.zpi.Harmonogram;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 30.11.12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class HarmonogramyResponse extends Response {

    private ArrayList<Harmonogram> lista;

    public ArrayList<Harmonogram> getHarmonogramy(){
        return lista;
    }
}
