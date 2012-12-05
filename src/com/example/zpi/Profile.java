package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 04.12.12
 * Time: 23:06
 * To change this template use File | Settings | File Templates.
 */
public class Profile extends Activity {

    ListView lista;
    ProfileMenuAdapter adapter;
    private Button dodaj;
    private Button wroc;
    Profil p1=new Profil(1,"Nazwa1","Włączone","80%","50st",true);
    Profil p2=new Profil(2,"Nazwa2","Włączone","80%","50st",false);
    Profil p3=new Profil(3,"Nazwa3","Włączone","80%","50st",false);
    ArrayList<Profil> profile=new ArrayList<Profil>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profile.add(p1);
        profile.add(p2);
        profile.add(p3);

        lista=(ListView)findViewById(R.id.profMenuList);
        adapter=new ProfileMenuAdapter(this,profile);
        Log.d("adapter",adapter.pobierzProfil(0).nazwa);
        lista.setAdapter(adapter);

        addListeners();
    }

    private void addListeners() {
        dodaj=(Button) findViewById(R.id.profDodaj);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),DodajProfil.class);
                startActivity(i);
            }
        });

        wroc=(Button) findViewById(R.id.profWroc);
        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}