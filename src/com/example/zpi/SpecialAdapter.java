package com.example.zpi;

import android.util.Log;
import com.example.zpi.Dodaj.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpecialAdapter extends BaseAdapter {

	 private LayoutInflater mInflater;
     private String[] data;
     private Harmonogram harm;

     public SpecialAdapter(Context context, String[] results) {
         mInflater = LayoutInflater.from(context);
         this.data = results;

     }
    public SpecialAdapter(Context context, String[] data,Harmonogram harm){
        mInflater=LayoutInflater.from(context);
        this.data=adapter(data,harm);
        this.harm=harm;
    }
    public String[] adapter(String [] d,Harmonogram h){
        d[0]+=" "+czas(h.czasStart);
        d[1]+=" "+czas(h.czasStop);
        d[2]+=" "+h.dni;
        if(d.length>3){
            d[3]+=" "+h.valStart;
        }
        return d;
    }
    public String czas(int cz){
        String czas="";
        /*if(cz/1000!=0){
            czas+=cz/1000;
            cz-=(cz/1000)*1000;
        } */
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
     ViewHolder holder;
     if (convertView == null) {
         convertView = mInflater.inflate(R.layout.szab_dod_adapter, null);
         holder = new ViewHolder();
         holder.text = (TextView) convertView.findViewById(R.id.headline);
         convertView.setTag(holder);

     } 
     else
    	 holder = (ViewHolder) convertView.getTag();


     holder.text.setText(data[position]);
         Log.d("data",data[position]);
     convertView.setBackgroundColor(0xFFFFFFF);
     return convertView;
     }

}
	
