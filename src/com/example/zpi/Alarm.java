package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

public class Alarm extends Activity {
	Button ok;
	ToggleButton wlacznik;
	Button wroc;
	TextView txt;
	EditText haslo;
    Connect c;
    boolean status;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        EditText editText = (EditText) findViewById(R.id.alrHasl);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        c=new Connect(this);
        try {
            status=Boolean.valueOf(c.request("3", "0").getValue());
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView) findViewById(R.id.alrPotw);
		haslo=(EditText)findViewById(R.id.alrHasl);
		wroc =(Button) findViewById(R.id.alrWr);
		wroc.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
		ok =(Button) findViewById(R.id.alrOK);
		ok.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                String klej = "" + haslo.getText();
                if (klej.equals("1234")) {
                    wlacznik.setEnabled(true);
                    txt.setVisibility(1);
                    txt.setText("Hasło prawidłowe");
                } else {
                    wlacznik.setEnabled(false);
                    txt.setVisibility(1);
                    txt.setText("Hasło nieprawidłowe!");
                }
            }
        });
		wlacznik =(ToggleButton) findViewById(R.id.alrWl);
        wlacznik.setChecked(status);
        wlacznik.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wlacznik.isChecked())
                    try {
                        c.request("3","0","true");
                    } catch (NoInternetException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (ServerErrorException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                else
                    try {
                        c.request("3","0","false");
                    } catch (NoInternetException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (ServerErrorException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
            }
        });
		//wroc=(Button) findViewById(R.id.alrWr);
		
	}

}
