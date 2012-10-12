package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Brama extends Activity {
	Button bt;
	Button bt2;
	Button bt3;
	TextView txt;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brama);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView) findViewById(R.id.brStat);
		bt=(Button) findViewById(R.id.brOtw);
		bt.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				txt.setText("Otwarta");
			}
		});
		
		bt2=(Button) findViewById(R.id.brZam);
		bt2.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				txt.setText("Zamknięta");
			}
		});
		
		bt3=(Button) findViewById(R.id.brWr);
		bt3.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}

}
