package com.example.zpi;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.alerts.ServerAlert;
import com.example.zpi.communication.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Woda extends Activity implements ResponseListener{
	Button zmniejsz;
	Button zwieksz;
	Button wroc;
    Button ustaw;
    Button wyczysc;
	SeekBar sb;
	TextView status;
    TextView zadana;
	Connect c;
	int prog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woda);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
            c.requestGet(0,1);
            c.requestGet(0,0);
		} 
        catch (NoInternetException e) {
            InternetAlert internetAlert=new InternetAlert(this);
            internetAlert.zwrocAlert();
        }
        catch (ServerErrorException e) {
			// TODO: handle exception
		}
		
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		status =(TextView)findViewById(R.id.wdStatus);
        zadana=(TextView) findViewById(R.id.wdZadana);
		
		sb=(SeekBar)findViewById(R.id.wdSb);
		sb.setProgress(prog/10);
		
		zmniejsz =(Button)findViewById(R.id.wdZm);
		zwieksz =(Button)findViewById(R.id.wdZw);
		wroc =(Button)findViewById(R.id.wdWr);
		
		zmniejsz.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                sb.setProgress(sb.getProgress() - 5);
            }
        });
		zwieksz.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                sb.setProgress(sb.getProgress() + 5);

            }
        });
		
		wroc.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                finish();

            }
        });
		
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				   progress+=40;
                   prog=progress;

                   zadana.setText("" + progress);
               }

			    public void onStartTrackingTouch(SeekBar seekBar) {}

			   public void onStopTrackingTouch(SeekBar seekBar) {
                  try {
                       c.requestSet(0,0,""+prog*10);
                      Toast.makeText(getApplicationContext(), "Ustawiono zadana temperature na "+prog+"C", Toast.LENGTH_SHORT).show();
                   }
                   catch (NoInternetException e) {
                       InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                       internetAlert.zwrocAlert();
                   }
                   catch (ServerErrorException e) {
                   }
			   }
			       });
        ustaw=(Button) findViewById(R.id.wodaUstHarm);
        ustaw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Harmonogramy.class);
                i.putExtra("modul",0);
                startActivity(i);
            }
        });

	}

    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){
            ServerAlert serverAlert=new ServerAlert(this);
            serverAlert.zwrocAlert();
        }
        else{
            if(res.getType()==Response.GET){
                if(res.getPort()==1){
                    try{
                    prog = Integer.parseInt(res.getValue().equals(null)?"0":res.getValue());
                    }
                    catch (NullPointerException e){
                        prog=0;
                        status.setText("Brak polaczenia");
                    }
                    status.setText(""+prog/10);
                }
                if (res.getPort()==0){
                    int pom=0;
                    try{
                        pom=Integer.parseInt((res.getValue().equals(null)?"0":res.getValue()));
                    }
                    catch (NullPointerException e){}

                    sb.setProgress(pom/10-40);
                    zadana.setText(""+pom/10);
                }
            }
        }
    }
}
