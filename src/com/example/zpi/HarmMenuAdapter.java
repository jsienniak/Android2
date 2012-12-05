package com.example.zpi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.widget.ToggleButton;
import com.example.zpi.Dodaj.ViewHolder;

import java.util.ArrayList;

public class HarmMenuAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
    private String[] naglowek;
    private String[] opis;
    private ArrayList<Harmonogram> harm;
    
    public HarmMenuAdapter(Context context, String[] results, String[] op) {
        mInflater = LayoutInflater.from(context);
        this.naglowek = results;
        this.opis=op;
    }
    public HarmMenuAdapter(Context context, ArrayList<Harmonogram> h){
        mInflater=LayoutInflater.from(context);
        naglowek=naglowki(h);
        opis=opisy(h);
        harm=h;
    }


    public String[] naglowki(ArrayList<Harmonogram> h){
        String[] naglowki=null;
        String pom = "";
        if(!h.isEmpty()){
            Log.d("kleje1234",""+h.size());
            for(int i=0;i<h.size();i++){
                switch(h.get(i).getModul()){
                    case 0:
                        pom+="Woda/";
                        break;
                    case 1:
                        pom+="Roleta/";
                        break;
                    case 4:
                        pom+="Oświetlenie/";
                        break;
                    default:
                        pom+="Klej/";
                }
            }
            naglowki=pom.split("/");
        }
        Log.d("kleje123",""+naglowki.length);
        return naglowki;
    }
    public String[] opisy(ArrayList<Harmonogram> h){
        String[] opisy=null;
        String pom = "";
        if(!h.isEmpty()){
            for(int i=0;i<h.size();i++){
                pom+=czas(h.get(i).getCzasStart())+"-";
                pom+=czas(h.get(i).getCzasStop())+" ";
                if(!h.get(i).getValStart().equals(""))
                pom+=h.get(i).getValStart()+" ";
                pom+=h.get(i).getDni();
                pom+="/";
            }
            opisy=pom.split("/");
        }
        Log.d("kleje12",""+opisy.length);
        return opisy;
    }
    public String czas(int cz){
        String czas="";

        if(cz/1000!=0){
            czas+=cz/1000;
            cz-=(cz/1000)*1000;
        }
        czas+=cz/100+":";
        cz-=(cz/100)*100;
        if(cz/10!=0){
            czas+=cz;
        }
        else{
            if(cz/1!=0)
               czas+="0"+cz;
            else
               czas+="00";
            }

        return czas;
    }
    public Harmonogram getHarmonogram(int position){
        return harm.get(position);
    }
    public int getCount() {
        return naglowek.length;
    }

    public Object getItem(int position) {
        return position;
    }
    public String getNaglowek(int position){
        return naglowek[position];
    }
    public String getOpis(int position){
        return opis[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    ViewHolder holder2 = null;
    ToggleButton btn = null;
    final int pos=position;
    if (convertView == null) {
        convertView = mInflater.inflate(R.layout.harm_menu_adapter, null);
        holder = new ViewHolder();
        holder2 = new ViewHolder();
        holder.text = (TextView) convertView.findViewById(R.id.harmNagl);
        holder2.text=(TextView) convertView.findViewById(R.id.harmOpis);
        btn=(ToggleButton) convertView.findViewById(R.id.HarmMenuWl);
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.out.println(pos);			
			}
		});
        btn.setFocusable(false);
        convertView.setTag(holder);
        convertView.setTag(holder2);
    } 
    else {
        holder = (ViewHolder) convertView.getTag();
        holder2 = (ViewHolder) convertView.getTag();
        //btn=(ToggleButton) convertView.findViewById()
    	}
        holder.text.setText(naglowek[position]);
        holder2.text.setText(opis[position]);
        btn.setChecked(harm.get(position).isWl());
        convertView.setBackgroundColor(0xFFFFFFF);
    return convertView;
    }
}

