package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Ogrod extends Activity {
	ToggleButton wl;
	ToggleButton auto;
	Button harm;
	Button start;
	Button stop;
	Button wroc;
	
	String s="";
	TextView txt;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrod);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){

		txt=(TextView) findViewById(R.id.ogrHarm);
		wl=(ToggleButton) findViewById(R.id.ogrOswWl);
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
