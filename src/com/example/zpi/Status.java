package com.example.zpi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zpi.communication.*;

public class Status extends Activity implements ResponseListener{
	Button br;
	Button wd;
	Button rol;
	Button alr;
    Button ogr;
	Button wr;
    Button odswiez;
    TextView statusBrama;
    TextView statusWoda;
    TextView statusRoleta;
    TextView statusAlarm;
    TextView statusOgrod;
	Connect c;
    Dialog sk;
    Boolean statAlr;
    Boolean statBr;
    EditText haslo;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
            c.requestGet(0,0);
            c.requestGet(1,0);
            c.requestGet(2,0);
            c.requestGet(3,0);
            c.requestGet(4,0);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        addListenerOnButton();
    }
	public void addListenerOnButton(){
        statusBrama=(TextView)findViewById(R.id.statBr2);
        statusWoda=(TextView)findViewById(R.id.statWd2);
        statusRoleta=(TextView)findViewById(R.id.statRol2);
        statusAlarm=(TextView)findViewById(R.id.statAlr2);
        statusOgrod=(TextView)findViewById(R.id.statOg2);
		br=(Button) findViewById(R.id.statBrZm);
		br.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
                try {
                    c.requestSet(2,0,new Boolean(!statBr).toString());
                    odswiez(2,0);
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
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
                onCreateDialog();
			}
		});
        ogr=(Button) findViewById(R.id.statOgZm);
        ogr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Rolety.class);
                startActivity(i);
            }
        });
        odswiez=(Button)findViewById(R.id.statOdsw);
        odswiez.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    c.requestGet(0,0);
                    c.requestGet(1,0);
                    c.requestGet(2,0);
                    c.requestGet(3,0);
                    c.requestGet(4,0);
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
		
		wr=(Button) findViewById(R.id.statWr);
		wr.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
    public void odswiez(int mod,int port){
        try {
            c.requestGet(mod,port);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    protected void onCreateDialog() {

        sk=new Dialog(this);
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.alarm_dialog, (ViewGroup)findViewById(R.id.dialog_lay_alr));
        haslo=(EditText)layout.findViewById(R.id.alrHaslDial);
        haslo.setInputType(InputType.TYPE_CLASS_NUMBER);
        haslo.setTransformationMethod(PasswordTransformationMethod.getInstance());
        sk.setContentView(layout);
        sk.setTitle("Wprowadz PIN");
        Button ok=(Button)layout.findViewById(R.id.alrOkDial);
        Button anl=(Button)layout.findViewById(R.id.alrAnlDial);
        ok.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                try {
                    c.requestGet(3,1);
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }});

        anl.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                sk.cancel();
            }});
        sk.show();
    }
    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){

        }
        if(res.getType()==Response.GET){
            switch (res.getModule()){
                case 0:
                    statusWoda.setText(""+Integer.parseInt(res.getValue())/10);
                     break;
                case 1:
                    statusRoleta.setText(res.getValue());
                case 2:
                    statBr=Boolean.valueOf(res.getValue());
                    if(statBr)
                        statusBrama.setText("Zamknięta");
                    else
                        statusBrama.setText("Otwarta");
                    break;
                case 3:
                    if(res.getPort()==0){
                        statAlr = Integer.parseInt(res.getValue())>0;
                    if(statAlr)
                        statusAlarm.setText("Uzbrojony");
                    else
                        statusAlarm.setText("Rozbrojony");
                    }
                    else{
                        if(res.getValue().equals(haslo.getText().toString())){
                            try {
                                c.requestSet(3,0,""+(statAlr?0:1));
                                odswiez(3,0);
                                sk.cancel();
                            } catch (ServerErrorException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            } catch (NoInternetException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Hasło nieprawidłowe, spróbuj ponownie", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 4:
                    if(Boolean.valueOf(res.getValue()))
                        statusOgrod.setText("Włączone");
                    else
                        statusOgrod.setText("Wyłączone");

            }
        }
    }
}
