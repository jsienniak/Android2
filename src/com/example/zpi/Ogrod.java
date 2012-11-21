package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Ogrod extends Activity {
	ToggleButton wl;
	ToggleButton auto;
	Button ustaw;
	Button start;
	Button stop;
	Button wroc;
    Button wyczysc;
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
				ustaw.setEnabled(false);
				wl.setEnabled(false);
				wl.setChecked(false);
				}
				else{
					ustaw.setEnabled(true);
					wl.setEnabled(true);
				}
			}
		});

		
		wroc=(Button) findViewById(R.id.ogrWr);
		wroc.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				finish();
			}
		});
        ustaw=(Button) findViewById(R.id.ogrHarm);
        ustaw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Harmonogramy.class);
                i.putExtra("modul",2);
                startActivity(i);
            }
        });


        wyczysc=(Button) findViewById(R.id.ogrWyczysc);
	}

}
