package com.example.zpi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zpi.Dodaj.ViewHolder;

public class HarmMenuAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
    private String[] naglowek;
    private String[] opis;
    
    public HarmMenuAdapter(Context context, String[] results, String[] op) {
        mInflater = LayoutInflater.from(context);
        this.naglowek = results;
        this.opis=op;
    }

    public int getCount() {
        return naglowek.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    ViewHolder holder2 = null;
    final int pos=position;
    if (convertView == null) {
        convertView = mInflater.inflate(R.layout.menu, null);
        holder = new ViewHolder();
        holder2 = new ViewHolder();
        holder.text = (TextView) convertView.findViewById(R.id.harmNagl);
        holder2.text=(TextView) convertView.findViewById(R.id.harmOpis);
        Button btn=(Button) convertView.findViewById(R.id.HarmMenuWl);
        btn.setOnClickListener(new OnClickListener() {
			@Override
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
    	}
        holder.text.setText(naglowek[position]);
        holder2.text.setText(opis[position]);
        convertView.setBackgroundColor(0xFFFFFFF);
    return convertView;
    }
}

