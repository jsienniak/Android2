package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Woda extends Activity{
	Button bt;
	Button bt2;
	Button bt3;
	SeekBar sb;
	TextView txt;
	TextView txt2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woda);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView)findViewById(R.id.woda);
		txt2=(TextView)findViewById(R.id.wdStatus);
		
		sb=(SeekBar)findViewById(R.id.wdSb);
		
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

			   @Override
			   public void onProgressChanged(SeekBar seekBar, int progress,
			     boolean fromUser) {
			    // TODO Auto-generated method stub
				   progress+=40;
			    txt2.setText(""+progress);
			   }

			   @Override
			   public void onStartTrackingTouch(SeekBar seekBar) {
			    // TODO Auto-generated method stub
			   }

			   @Override
			   public void onStopTrackingTouch(SeekBar seekBar) {
			    // TODO Auto-generated method stub
			   }
			       });
			   
	}

}
