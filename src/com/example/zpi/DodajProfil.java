package com.example.zpi;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.TooManyListenersException;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 05.12.12
 * Time: 02:27
 * To change this template use File | Settings | File Templates.
 */
public class DodajProfil extends Activity {

    ToggleButton wl;
    Button dodaj;
    Button wroc;
    Button usun;
    ListView lista;
    DodajProfilAdapter adapter;
    Profil prof;
    boolean wybor;
    String roleta;
    String woda;
    int progres=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_profil);

        lista=(ListView)findViewById(R.id.prof_list);

        adapter=new DodajProfilAdapter(this);
        lista.setAdapter(adapter);

        addListeners();
    }

    private void addListeners() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wyswietlDialog(i);
            }
        });
    }
    private void wyswietlDialog(int i){
        final Dialog sk=new Dialog(this);
        final TextView txt;
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.seek_dialog, (ViewGroup)findViewById(R.id.dialog_lay));
        sk.setContentView(layout);
        sk.setTitle(pobZasob(R.string.ustPoz));

        txt=(TextView)layout.findViewById(R.id.dialog_status);
        SeekBar seek=(SeekBar)layout.findViewById(R.id.dialog_seek_bar);
        switch (i){
            case 0:seek.setMax(100);
                wybor=false;
                break;
            case 1:seek.setMax(40);
                wybor=true;
                break;
        }

        seek.setProgress(progres);
        txt.setText(""+progres);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {}

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int pom=0;
             if(wybor){
                pom+=progress+40;
                Log.d("profil_pr", "" + pom);
                txt.setText(""+pom);
                woda=""+pom;
            }
            else{
                txt.setText(""+progress);
                roleta=""+progress;
            }

        }
        });
        Button ok=(Button)layout.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sk.cancel();

            }
        });
        Button anl=(Button)layout.findViewById(R.id.dialog_anl);
        anl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sk.cancel();

            }
        });
        sk.show();
    }
    public String pobZasob(int z){
        return getResources().getString(z);
    }
    /*public int suwakProgres(Profil p){
      8  try{
            String[] pom=p..split("[st,% ]+");
            return Integer.parseInt(pom[0]);
        }
        catch (Exception e){
            return 0;
        }
    }  */
}