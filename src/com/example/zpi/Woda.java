package com.example.zpi;

import android.content.Intent;
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
		status =(TextView)findViewById(R.id.wdStatus);
		
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

			   public void onProgressChanged(SeekBar seekBar, int progress,
			     boolean fromUser) {
			    // TODO Auto-generated method stub
				   progress+=40;
                   prog=progress;
			    status.setText("" + progress);
                   try {
                       c.requestGet(5,0);
                   } catch (ServerErrorException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   } catch (NoInternetException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   }
               }

			    public void onStartTrackingTouch(SeekBar seekBar) {
			    // TODO Auto-generated method stub
			   }

			   public void onStopTrackingTouch(SeekBar seekBar) {
                  /* try {
                       //c.requestSet(0,0,""+prog*10);
                       c.requestGet(5,0);
                   }
                   catch (NoInternetException e) {
                       // TODO: handle exception
                   }
                   catch (ServerErrorException e) {
                       // TODO: handle exception
                   }*/
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

        wyczysc=(Button) findViewById(R.id.wodWyczysc);
	}

    @Override
    public void processResponse(Response res) {
        if(res.getType()==Response.GET){
            prog = Integer.parseInt(res.getValue());
        }
    }
}
