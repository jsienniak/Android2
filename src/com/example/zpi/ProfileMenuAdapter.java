package com.example.zpi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.zpi.Harmonogramy.ViewHolder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 04.12.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class ProfileMenuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private String[] naglowek;
    private String[] opis;
    private ArrayList<Profil> profile;

    public ProfileMenuAdapter(Context context,ArrayList<Profil> p){
        mInflater = LayoutInflater.from(context);
        this.naglowek = nazwy(p);
        this.opis=opisy(p);
        profile=p;
    }
    public String[] nazwy(ArrayList<Profil> prof){

        String pom="";
        for(int i=0;i<prof.size();i++){
            pom+=prof.get(i).getNazwa()+" ";
        }
        String[] nazwy=pom.split("[ ]+");
        return nazwy;
    }
    public String[] opisy(ArrayList<Profil> prof){
        String[] opisy=new String[prof.size()];
        for(int i=0;i<prof.size();i++){
            opisy[i]="Swiatlo "+(prof.get(i).getSwiatlo().equals("true")?"Wł.":"Wył")+" Roleta "+prof.get(i).getRoleta()+"%"+" Woda "+prof.get(i).getWoda()+"C";
        }
        return opisy;
    }
    public Profil pobierzProfil(int i){
        return profile.get(i);
    }
    @Override
    public int getCount() {
        return naglowek.length;
    }

    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ToggleButton btn;
        final int pos=position;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.harm_menu_adapter, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.harmNagl);
            holder.text2=(TextView) convertView.findViewById(R.id.harmOpis);
            btn=(ToggleButton) convertView.findViewById(R.id.HarmMenuWl);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println(pos);
                }
            });
            btn.setFocusable(false);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(naglowek[position]);
        holder.text2.setText(opis[position]);
        try{
            btn=(ToggleButton) convertView.findViewById(R.id.HarmMenuWl);
            btn.setChecked(profile.get(position).isWl());
        }
        catch (Exception e){}
        convertView.setBackgroundColor(0xFFFFFFF);
        return convertView;
    }
}
