package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Status extends Activity {
	Button br;
	Button wd;
	Button rol;
	Button alr;
	Button wr;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		br=(Button) findViewById(R.id.statBrZm);
		br.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				Intent i=new Intent(getApplicationContext(),Brama.class);
				startActivity(i);
			}
		});
		
		wd=(Button) findViewById(R.id.statWdZm);
		wd.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				Intent i=new Intent(getApplicationContext(),Woda.class);
				startActivity(i);
			}
		});
		rol=(Button) findViewById(R.id.statRolZm);
		rol.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				Intent i=new Intent(getApplicationContext(),Rolety.class);
				startActivity(i);
			}
		});
		alr=(Button) findViewById(R.id.statAlrZm);
		alr.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				Intent i=new Intent(getApplicationContext(),Alarm.class);
				startActivity(i);
			}
		});
		
		wr=(Button) findViewById(R.id.statWr);
		wr.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
}
