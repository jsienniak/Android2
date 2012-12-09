package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.alerts.ServerAlert;
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
    ArrayList<Profil> profile=new ArrayList<Profil>();
    ArrayList<Profil> pom=new ArrayList<Profil>();
    Connect c;
    int i=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        c=new Connect(this);
        Log.d("profilki",c.url);
        c.addResponseListener(this);
        try {
            c.login(1);
            c.requestGetProfile();
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert internetAlert=new InternetAlert(this);
            internetAlert.zwrocAlert();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        try {
            c.requestGetProfile();
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert internetAlert=new InternetAlert(this);
            internetAlert.zwrocAlert();
        }
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
    }

    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){
            if(i==0){
                ServerAlert servA=new ServerAlert(this);
                servA.zwrocAlert();
                i++;
            }
        }

        if(res.getType()==Response.GETPROFILE){
            lista=(ListView)findViewById(R.id.profMenuList);
            Profil p1=new Profil(-1,"BrakProfili","","","",false);

            pom=(ArrayList<Profil>)res.getExtras();
            try{
                for(int i=0;i<pom.size();i++){
                    profile.add(pom.get(i));
                    Log.d("brakPr2",pom.get(i).getNazwa());
                }
            }
            catch (Exception e){
                profile=pom;
            }
            if(profile.isEmpty()){
                profile.add(p1);
                lista.setEnabled(false);
                Log.d("brakPr","wejszlo");
            }

            adapter=new ProfileMenuAdapter(this,profile);

            lista.setAdapter(adapter);
            addListeners();
        }

    }
}