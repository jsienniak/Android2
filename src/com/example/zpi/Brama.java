package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.zpi.communication.*;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

public class Brama extends Activity implements ResponseListener {
	Button otw;
	Button zamk;
	Button wroc;
	TextView txt;
    Connect c;
    //Boolean status=false;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brama);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
            c.requestGet(2,0);
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        addListenerOnButton();
    }
	public void addListenerOnButton(){

		otw =(Button) findViewById(R.id.brOtw);
		otw.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    c.requestSet(2,0,"false") ;
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
                    c.requestSet(2,0,"true");
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                txt.setText("Zamknięta");
            }
        });
		
		wroc =(Button) findViewById(R.id.brWroc);
		wroc.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
		
	}

    @Override
    public void processResponse(Response res) {
        if(res.getType()==Response.GET){
            Boolean status = Boolean.valueOf(res.getValue());
            txt=(TextView) findViewById(R.id.brStat);
            if(status){
                txt.setText("Zamknięta");
            }
            else
                txt.setText("Otwarta");
        }
    }
}
