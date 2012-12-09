package com.example.zpi;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 05.12.12
 * Time: 02:27
 * To change this template use File | Settings | File Templates.
 */
public class DodajProfil extends Activity {
    ToggleButton wlacz;
    ToggleButton swiatlo;
    Button dodaj;
    Button wroc;
    Button usun;
    ListView lista;
    DodajProfilAdapter adapter;
    Profil prof=new Profil();
    boolean wybor;
    String nazwa="";
    String progres="0";
    String[] temp=null;
    boolean edycja=false;
    EditText haslo;
    int opcja;
    Dialog sk;
    String[] items={"Nazwa","Roleta","Woda"};
    Connect c;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_profil);
        c=new Connect(this);
        temp=items;
        swiatlo =(ToggleButton)findViewById(R.id.profDodSwiatlo);
        wlacz=(ToggleButton) findViewById(R.id.profDodWlacz);
        try{
            prof= (Profil) getIntent().getExtras().get("profil");
            adapter=new DodajProfilAdapter(this,temp,prof);
            Log.d("statusWod",prof.getWoda());
            swiatlo.setChecked(Boolean.valueOf(prof.getSwiatlo()));
            wlacz.setChecked(Boolean.valueOf(prof.isWl()));
            setTitle("Edycja profilu "+prof.getNazwa());
            edycja=true;
        }
        catch (Exception e){
            adapter=new DodajProfilAdapter(this,temp);
            setTitle("Dodawanie profilu");

        }
        lista=(ListView)findViewById(R.id.prof_list);


        lista.setAdapter(adapter);

        addListeners();
    }

    private void addListeners() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                    wyswietlDialog(i);
                else
                    wprowadzNazwe();
                opcja=i;
            }
        });
        usun=(Button) findViewById(R.id.profUsun);
        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    c.requestDelProfile(prof.getId());
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        wroc=(Button)findViewById(R.id.profAnl);
        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(!edycja){
            wlacz.setChecked(true);
            usun.setEnabled(false);
        }
        else{
            wlacz.setChecked(prof.isWl());
            usun.setEnabled(true);
        }
        wlacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ustawProfil("");
                opcja=-1;
            }
        });

        swiatlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ustawProfil("");
                opcja=-1;
            }
        });

        dodaj=(Button)findViewById(R.id.profDod);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("profil",prof.getRoleta());
                Log.d("profil2",prof.getWoda());
                Log.d("profil3",prof.getSwiatlo());
                Log.d("profil4",prof.getNazwa());
                Log.d("profil5",""+prof.isWl());
                try{
                    if(czyPoprawnieWypelniony()){
                        c.requestDelProfile(prof.getId());
                        c.requestSetProfile(prof);
                        finish();
                        Toast.makeText(getApplicationContext(), "Dodano profil", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie dane!", Toast.LENGTH_LONG).show();
                }
                catch (ServerErrorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                catch (NoInternetException e) {
                e.printStackTrace();
                InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                internetAlert.zwrocAlert();
                }
            }
        });
    }
    private void changeItems(){
        Log.d("opcja",""+opcja);

        switch (opcja){
            case 0:
                temp[opcja]="Nazwa "+nazwa;
                Log.d("ustaw3",nazwa);
                break;
            case 2:
                temp[opcja]="Woda "+progres+"C";
                break;
            case 1:
                temp[opcja]="Roleta "+progres+"%";
                break;


        }
        DodajProfilAdapter adapter2;
        adapter2=new DodajProfilAdapter(this,temp);

        lista.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

    }
    private void wyswietlDialog(int i){
        sk=new Dialog(this);
        final TextView txt;
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.seek_dialog, (ViewGroup)findViewById(R.id.dialog_lay));
        sk.setContentView(layout);
        sk.setTitle(pobZasob(R.string.ustPoz));
        txt=(TextView)layout.findViewById(R.id.dialog_status);
        SeekBar seek=(SeekBar)layout.findViewById(R.id.dialog_seek_bar);
        switch (i){
            case 1:seek.setMax(100);
                wybor=false;
                if (edycja){
                    seek.setProgress(Integer.parseInt(prof.getRoleta()));
                    txt.setText(Integer.parseInt(prof.getRoleta()));
                }
                else{
                    seek.setProgress(Integer.parseInt(progres));
                    txt.setText(""+progres);
                }
                break;
            case 2:seek.setMax(40);
                if (edycja){
                    seek.setProgress(Integer.parseInt(prof.getWoda()));
                    txt.setText(Integer.parseInt(prof.getWoda()));
                }
                else{
                    seek.setProgress(Integer.parseInt(progres)-40);
                    txt.setText(""+progres);
                    }
                wybor=true;
                break;
        }
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {}

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int pom=0;
             if(wybor){
                pom+=progress+40;
                txt.setText("" + pom);
                progres=""+pom;
            }
            else{
                txt.setText("" + progress);
                progres=""+progress;
            }

        }
        });
        Button ok=(Button)layout.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                changeItems();
                ustawProfil(progres);
                sk.cancel();
                sk.dismiss();
            }
        });
        Button anl=(Button)layout.findViewById(R.id.dialog_anl);
        anl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sk.cancel();
                sk.dismiss();

            }
        });
        sk.show();
    }
    public void wprowadzNazwe(){

        sk=new Dialog(this);
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.nazwa_dialog, (ViewGroup)findViewById(R.id.dialog_lay_profil));
        haslo=(EditText)layout.findViewById(R.id.profNazwDial);

        if(edycja)
            haslo.setText(prof.getNazwa());
        else
            haslo.setText(nazwa);
        sk.setContentView(layout);
        sk.setTitle("Wprowadz nazwę dla profilu");
        Button ok=(Button)layout.findViewById(R.id.profOkDial);
        Button anl=(Button)layout.findViewById(R.id.profAnlDial);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String pom=""+haslo.getText();
                if(pom.split("[ ]+").length>1){
                    Toast.makeText(getApplicationContext(), "Podaj nazwe bez spacji!", Toast.LENGTH_LONG).show();
                }
                else{
                    nazwa=""+haslo.getText();
                    changeItems();
                    ustawProfil(nazwa);
                    sk.cancel();
                    sk.dismiss();
                }

            }});

        anl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sk.cancel();
            }});
        sk.show();
    }
    public String pobZasob(int z){
        return getResources().getString(z);
    }
    public void ustawProfil(String wart){

        prof.setWl(wlacz.isChecked());
        prof.setSwiatlo(""+swiatlo.isChecked());
        switch (opcja){
            case 0:
                Log.d("ustaw3",wart);
                prof.setNazwa(wart);
                break;
            case 1:
                Log.d("ustaw1",wart!=null?wart:"0");
                prof.setRoleta(wart!=null?wart:"0");
                break;
            case 2:
                Log.d("ustaw2",wart);
                prof.setWoda(wart!=null?wart:"0");
                break;
        }
        opcja=-5;
    }
    public boolean czyPoprawnieWypelniony(){
        boolean wypelnione=false;

        for(int i=0;i<temp.length;i++){
            if((temp[i].split("[ ]+")).length>1)
                wypelnione=true;
            else
                wypelnione=false;
        }

        return wypelnione;
    }
}