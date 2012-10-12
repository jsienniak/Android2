package com.example.zpi;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Rolety extends Activity {
	Button bt;
	Button bt2;
	Button bt3;
	VerticalSeekBar_Reverse sb;
	TextView txt;
	TextView txt2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolety);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		txt=(TextView)findViewById(R.id.rolety);
		txt2=(TextView)findViewById(R.id.rolStatus);
		
		sb=(VerticalSeekBar_Reverse)findViewById(R.id.vertical_Seekbar);
		
		bt=(Button)findViewById(R.id.otwR);
		bt2=(Button)findViewById(R.id.zamR);
		bt3=(Button)findViewById(R.id.wrR);
		
		bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				sb.setProgress(0);
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				sb.setProgress(100);
				sb.klik();
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
