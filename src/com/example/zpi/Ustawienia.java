package com.example.zpi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import com.example.zpi.communication.Connect;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 06.12.12
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class Ustawienia extends PreferenceActivity{
    WywolanieAlarmu al;
    EditTextPreference adres;
    EditTextPreference telefon;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.opcje);

        adres=(EditTextPreference) findPreference("adres_ip");
        telefon=(EditTextPreference)findPreference("tel_alarm");
        setDefaults("adres_ip",adres.getText().toString(),this);
        setDefaults("tel_alarm",telefon.getText().toString(),this);

    }
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

  /*  public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }*/
}