package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

public class Brama extends Activity {
	Button otw;
	Button zamk;
	Button wroc;
	TextView txt;
    Connect c;
    Boolean status;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brama);
        c=new Connect(this);
        try {
            status= Boolean.valueOf(c.request("2", "0").getValue());
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView) findViewById(R.id.brStat);
        if(status){
            txt.setText("Zamknięta");
        }
        else
            txt.setText("Zamknięta");

		otw =(Button) findViewById(R.id.brOtw);
		otw.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    c.request("2", "0", "false");
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                txt.setText("Otwarta");
            }
        });
		
		zamk =(Button) findViewById(R.id.brZam);
		zamk.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    c.request("2", "0", "true");
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                txt.setText("Zamknięta");
            }
        });
		
		wroc =(Button) findViewById(R.id.brWr);
		wroc.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
		
	}

}
