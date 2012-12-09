package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.alerts.ServerAlert;
import com.example.zpi.communication.*;

public class Ogrod extends Activity implements ResponseListener {
	ToggleButton wl;
	ToggleButton auto;
	Button ustaw;
	Button wroc;
    Button wyczysc;
	String s="";
	TextView txt;
    Connect c;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrod);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
            c.requestGet(4,0);
            c.requestGet(4,1);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert internetAlert=new InternetAlert(this);
            internetAlert.zwrocAlert();
        }
        addListenerOnButton();
    }
	public void addListenerOnButton(){

		wl=(ToggleButton) findViewById(R.id.ogrOswWl);
        ustaw=(Button) findViewById(R.id.ogrUst);
		wl.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
                try {
                    c.requestSet(4,0,""+wl.isChecked());
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();
                    InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                    internetAlert.zwrocAlert();
                }
            }
		});
		
		auto=(ToggleButton) findViewById(R.id.ogrAutoB);
		auto.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
                Log.d("fotokomora",""+auto.isChecked());

                    try {
                        Log.d("fotokomora2",""+auto.isChecked());
                        c.requestSet(4,1,""+auto.isChecked());
                    } catch (ServerErrorException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (NoInternetException e) {
                        e.printStackTrace();
                        InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                        internetAlert.zwrocAlert();
                    }
                if(auto.isChecked()){
                    ustaw.setEnabled(false);
				    wl.setEnabled(false);
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

        ustaw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Harmonogramy.class);
                i.putExtra("modul",4);
                startActivity(i);
            }
        });

	}

    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){
            auto.setEnabled(false);
            wl.setEnabled(false);
            ustaw.setEnabled(false);
            ServerAlert serverAlert=new ServerAlert(this);
            serverAlert.zwrocAlert();
        }
        else{
            if(res.getType()==Response.GET){
                switch (res.getPort()){
                    case 0:
                        wl.setChecked(Boolean.valueOf(res.getValue()));
                        ustaw.setEnabled(true);
                        break;
                    case 1:
                        auto.setChecked(Boolean.valueOf(res.getValue()));
                        if(Boolean.valueOf(res.getValue())){
                            wl.setEnabled(false);
                            ustaw.setEnabled(false);
                        }
                        break;
                }
            }
        }
    }
}
