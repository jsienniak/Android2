package com.example.zpi;

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

     public SpecialAdapter(Context context, String[] results) {
         mInflater = LayoutInflater.from(context);
         this.data = results;

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
     convertView.setBackgroundColor(0xFFFFFFF);
     return convertView;
     }
}
	
