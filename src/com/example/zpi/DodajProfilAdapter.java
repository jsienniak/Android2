package com.example.zpi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 05.12.12
 * Time: 02:41
 * To change this template use File | Settings | File Templates.
 */
public class DodajProfilAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private String[] data;
    private Profil profil;
   // private String[] pom={"Roleta","Woda"};

    public DodajProfilAdapter(Context context,String[] d,Profil profil){
        mInflater=LayoutInflater.from(context);
        this.data=adapter(d,profil);
        this.profil=profil;
    }
    public DodajProfilAdapter(Context context,String[] data){
        mInflater=LayoutInflater.from(context);
        this.data=data;
    }

    private String[] adapter(String[] d,Profil profil) {
        Log.d("adapterP",d[0]);
        d[1]+=" "+profil.getRoleta()+"%";
        d[2]+=" "+profil.getWoda()+"C";
        d[0]+=" "+profil.getNazwa();
        Log.d("adapterP",d[0]);
        return d;
    }

    @Override
    public int getCount() {
        return data.length;

    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Dodaj.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.szab_dod_adapter, null);
            holder = new Dodaj.ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.headline);
            convertView.setTag(holder);

        }
        else
            holder = (Dodaj.ViewHolder) convertView.getTag();


        holder.text.setText(data[position]);
//        Log.d("data", data[position]);
        convertView.setBackgroundColor(0xFFFFFFF);
        return convertView;
    }
}
