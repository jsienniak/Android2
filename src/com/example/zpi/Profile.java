package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.zpi.communication.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 04.12.12
 * Time: 23:06
 * To change this template use File | Settings | File Templates.
 */
public class Profile extends Activity implements ResponseListener{

    ListView lista;
    ProfileMenuAdapter adapter;
    private Button dodaj;
    private Button wroc;
    Profil p1=new Profil(1,"Nazwa1","Włączone","80%","50st",true);
    Profil p2=new Profil(2,"Nazwa2","Włączone","80%","50st",false);
    Profil p3=new Profil(3,"Nazwa3","Włączone","80%","50st",false);
    ArrayList<Profil> profile=new ArrayList<Profil>();
    Connect c;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        c=new Connect(this);
        Log.d("profilki",c.url);
        c.addResponseListener(this);
        try {
            c.requestGetProfile();
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // profile.add(p1);
      //  profile.add(p2);
       //profile.add(p3);

      /*  lista=(ListView)findViewById(R.id.profMenuList);
        adapter=new ProfileMenuAdapter(this,profile);
        Log.d("adapter",adapter.pobierzProfil(0).getNazwa());
        lista.setAdapter(adapter);                           */


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
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intencja =new Intent(getApplicationContext(),DodajProfil.class);
                intencja.putExtra("profil", adapter.pobierzProfil(i));
                startActivity(intencja);
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

    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){

        }
        if(res.getType()==Response.GETPROFILE){
            lista=(ListView)findViewById(R.id.profMenuList);
            profile=(ArrayList<Profil>)res.getExtras();
            try{

                Log.d("brakPro",profile.get(0).getNazwa());
            }
            catch (Exception e){
                Log.d("brakPro","nie ma");
            }

            Profil p1=new Profil(-1,"Brak profili","Dodaj","nowe","profile",false);
            if(profile.isEmpty()){
                profile.add(p1);
            }
            else
            adapter=new ProfileMenuAdapter(this,profile);

            lista.setAdapter(adapter);
            addListeners();
        }
    }
}