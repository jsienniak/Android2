package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// TODO: Stringi z plików (lokalizacje), tak samo haselko

public class Alarm extends Activity {
	Button bt;
	Button bt2;
	Button bt3;
	TextView txt;
	EditText haslo;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView) findViewById(R.id.alrPotw);
		/*
		bt=(Button) findViewById(R.id.alrWl);
		bt.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				txt.setText("Uzbrojony");
			}
		});
		
		bt2=(Button) findViewById(R.id.alrWy);
		bt2.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				txt.setText("Rozbrojony");
			}
		});
		*/
		haslo=(EditText)findViewById(R.id.alrHasl);
		bt3=(Button) findViewById(R.id.alrWr);
		bt3.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				finish();
			}
		});
		bt=(Button) findViewById(R.id.alrOK);
		bt.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				String klej=""+haslo.getText();
				if(klej.equals("1234")){
					bt2.setEnabled(true);
					txt.setVisibility(1);
					txt.setText("Hasło prawidłowe");
				}
				else{
					bt2.setEnabled(false);
					txt.setVisibility(1);
					txt.setText("Hasło nieprawidłowe!");
				}
			}
		});
		bt2=(Button) findViewById(R.id.alrWl);
		//bt3=(Button) findViewById(R.id.alrWr);
		
	}

}
