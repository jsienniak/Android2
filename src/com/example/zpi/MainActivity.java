package com.example.zpi;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button brama;
	Button woda;
	Button alarm;
	Button rolety;
	Button ogrod;
	Button status;
	Button koniec;
	Button harmon;
    protected AccountManager accountManager;

    final private static String SENDER_ID = "303941619301";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Log.d("cos",""+accounts[0]);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, SENDER_ID);
        } 
        
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void addListenerOnButton(){
    	brama=(Button)findViewById(R.id.brama);
    	woda=(Button)findViewById(R.id.woda);
    	alarm=(Button)findViewById(R.id.alarm);
    	rolety=(Button)findViewById(R.id.rolety);
    	ogrod=(Button)findViewById(R.id.ogrod);
    	status=(Button)findViewById(R.id.status);
    	koniec=(Button)findViewById(R.id.koniec);
    	harmon=(Button)findViewById(R.id.harmonogram);
    	
    	brama.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Brama.class);
			startActivity(i);
			}
		});
    	woda.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Woda.class);
			startActivity(i);
			}
		});

    	alarm.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Alarm.class);
			startActivity(i);
			}
		});
    	rolety.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Rolety.class);
			startActivity(i);
			}
		});
    	ogrod.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Ogrod.class);
			startActivity(i);
			}
		});
    	status.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Status.class);
			startActivity(i);
			}
		});
    	koniec.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
			finish();
			}
		});
    	harmon.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Harmonogramy.class);
			startActivity(i);
			}
		});
    }
}
