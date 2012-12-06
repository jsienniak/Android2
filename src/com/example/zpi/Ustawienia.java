package com.example.zpi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import com.example.zpi.communication.Connect;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 06.12.12
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class Ustawienia extends PreferenceActivity {
    WywolanieAlarmu al;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.opcje);
        Connect.url="sds";
        al.tel="tel:";

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putString("adres_ip",sp.getString("adres_ip",""+"dds"));

    }
}