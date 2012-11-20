package com.example.zpi;

import com.example.zpi.communication.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.prefs.Preferences;

public class Woda extends Activity implements ResponseListener{
	Button bt;
	Button bt2;
	Button bt3;
	SeekBar sb;
	TextView txt;
	TextView txt2;
	Connect c;
	int prog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woda);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
		//prog=Integer.parseInt(c.request("0","1").getValue());
            c.requestGet(0,1);
            System.out.print(prog);
		} 
        catch (NoInternetException e) {
			// TODO: handle exception
        }
        catch (ServerErrorException e) {
			// TODO: handle exception
		}
		
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView)findViewById(R.id.woda);
		txt2=(TextView)findViewById(R.id.wdStatus);
		
		sb=(SeekBar)findViewById(R.id.wdSb);
		sb.setProgress(prog/10);
		
		bt=(Button)findViewById(R.id.wdZm);
		bt2=(Button)findViewById(R.id.wdZw);
		bt3=(Button)findViewById(R.id.wdWr);
		
		bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				sb.setProgress(sb.getProgress()-5);
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				sb.setProgress(sb.getProgress()+5);
				
			}
		});
		
		bt3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			   public void onProgressChanged(SeekBar seekBar, int progress,
			     boolean fromUser) {
			    // TODO Auto-generated method stub
				   progress+=40;
                   prog=progress;
			    txt2.setText(""+progress);
			   }

			    public void onStartTrackingTouch(SeekBar seekBar) {
			    // TODO Auto-generated method stub
			   }

			   public void onStopTrackingTouch(SeekBar seekBar) {
                   try {
                       c.requestSet(0,0,""+prog*10);
                   }
                   catch (NoInternetException e) {
                       // TODO: handle exception
                   }
                   catch (ServerErrorException e) {
                       // TODO: handle exception
                   }
			   }
			       });
			   
	}

    @Override
    public void processResponse(Response res) {
        if(res.getType()==Response.GET){
            prog = Integer.parseInt(res.getValue());
        }
    }
}
