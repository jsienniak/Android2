package com.example.zpi;

import java.util.Currency;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button bt;
	Button bt2;
	Button bt3;
	Button bt4;
	Button bt5;
	Button bt6;
	Button bt7;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void addListenerOnButton(){
    	bt=(Button)findViewById(R.id.button1);
    	bt2=(Button)findViewById(R.id.button2);
    	bt3=(Button)findViewById(R.id.button3);
    	bt4=(Button)findViewById(R.id.button4);
    	bt5=(Button)findViewById(R.id.button5);
    	bt6=(Button)findViewById(R.id.button6);
    	bt7=(Button)findViewById(R.id.button7);
    	
    	bt.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Brama.class);
			startActivity(i);
			}
		});
    	bt2.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Woda.class);
			startActivity(i);
			}
		});

    	bt3.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Alarm.class);
			startActivity(i);
			}
		});
    	bt4.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Rolety.class);
			startActivity(i);
			}
		});
    	bt5.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Ogrod.class);
			startActivity(i);
			}
		});
    	bt6.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Status.class);
			startActivity(i);
			}
		});
    	bt7.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
			finish();
			}
		});
    }
}
