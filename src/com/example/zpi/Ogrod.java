package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class Ogrod extends Activity {
	ToggleButton wl;
	ToggleButton auto;
	Button harm;
	Button start;
	Button stop;
	Button wroc;
	
	String s="";
	TimePicker tpP;

	TextView txt;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrod);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		tpP=(TimePicker)findViewById(R.id.timePicker1);
		tpP.setIs24HourView(true);

		txt=(TextView) findViewById(R.id.ogrHarm);
		wl=(ToggleButton) findViewById(R.id.toggleButton1);
		wl.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				
			}
		});
		
		auto=(ToggleButton) findViewById(R.id.ogrAutoB);
		auto.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				if(auto.isChecked()){
				harm.setEnabled(false);
				wl.setEnabled(false);
				wl.setChecked(false);
				}
				else{
					harm.setEnabled(true);
					wl.setEnabled(true);
				}
			}
		});
		
		harm=(Button) findViewById(R.id.ogrUst);
		harm.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				tpP.setVisibility(1);
				start.setVisibility(1);
				stop.setVisibility(1);
			}
		});
		
		stop=(Button) findViewById(R.id.ogrStop);
		start=(Button)findViewById(R.id.ogrStart);
		start.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				s="Start: "+tpP.getCurrentHour()+":"+tpP.getCurrentMinute();
				start.setEnabled(false);
				stop.setEnabled(true);
			}
		});
		stop.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				txt.setText(s+" Stop: "+tpP.getCurrentHour()+":"+tpP.getCurrentMinute());
				tpP.setVisibility(-1);
				start.setVisibility(-1);
				stop.setVisibility(-1);
			}
		});
		wroc=(Button) findViewById(R.id.ogrWr);
		wroc.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
