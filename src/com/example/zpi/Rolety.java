package com.example.zpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Rolety extends Activity {
	Button bt;
	Button bt2;
	Button bt3;
	ImageView imv;
	VerticalSeekBar_Reverse sb;
	TextView txt;
	TextView txt2;
	
	public int[] roletaImg={
			R.drawable.r0,
			R.drawable.r1,
			R.drawable.r2,
			R.drawable.r3,
			R.drawable.r4,
			R.drawable.r5,
			R.drawable.r6,
			R.drawable.r7,
			R.drawable.r8,
			R.drawable.r9,
			R.drawable.r10,
			R.drawable.r11,
			R.drawable.r12,
			R.drawable.r13,
			R.drawable.r14,
			R.drawable.r15,
			R.drawable.r16,
			R.drawable.r17,
			R.drawable.r18,
			R.drawable.r19,
			R.drawable.r20,
			R.drawable.r21,
			R.drawable.r22,
			R.drawable.r23,
			R.drawable.r24,
			R.drawable.r25,
			R.drawable.r26,
			R.drawable.r27,
			R.drawable.r28,
			R.drawable.r29,
			R.drawable.r30,
			R.drawable.r31,
			R.drawable.r32,
			R.drawable.r33,
			R.drawable.r34,
			R.drawable.r35,
			R.drawable.r36,
			R.drawable.r37,
			R.drawable.r38,
			R.drawable.r39,
			R.drawable.r40,
			R.drawable.r41,
			R.drawable.r42,
			R.drawable.r43,
			R.drawable.r44,
			R.drawable.r45,
			R.drawable.r46,
			R.drawable.r47,
			R.drawable.r48,
			R.drawable.r49,
			R.drawable.r50,
			R.drawable.r51,
			R.drawable.r52,
			R.drawable.r53,
			R.drawable.r54,
			R.drawable.r55,
			R.drawable.r56,
			R.drawable.r57,
			R.drawable.r58,
			R.drawable.r59,
			R.drawable.r60,
			R.drawable.r61,
			R.drawable.r62,
			R.drawable.r63,
			R.drawable.r64,
			R.drawable.r65,
			R.drawable.r66,
			R.drawable.r67,
			R.drawable.r68,
			R.drawable.r69,
			R.drawable.r70,
			R.drawable.r71,
			R.drawable.r72,
			R.drawable.r73,
			R.drawable.r74,
			R.drawable.r75,
			R.drawable.r76,
			R.drawable.r77,
			R.drawable.r78,
			R.drawable.r79,
			R.drawable.r80,
			R.drawable.r81,
			R.drawable.r82,
			R.drawable.r83,
			R.drawable.r84,
			R.drawable.r85,
			R.drawable.r86,
			R.drawable.r87,
			R.drawable.r88,
			R.drawable.r89,
			R.drawable.r90,
			R.drawable.r91,
			R.drawable.r92,
			R.drawable.r93,
			R.drawable.r94,
			R.drawable.r95,
			R.drawable.r96,
			R.drawable.r97,
			R.drawable.r98,
			R.drawable.r99,
			R.drawable.r100
	};
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolety);
        
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		imv=(ImageView) findViewById(R.id.rolObrRol);
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
			   public void onProgressChanged(SeekBar seekBar, int progress,
			     boolean fromUser) {
			    // TODO Auto-generated method stub
			    txt2.setText(""+progress);
			    imv.setImageResource(roletaImg[progress]);
			   }

			   public void onStartTrackingTouch(SeekBar seekBar) {
			    // TODO Auto-generated method stub
			   }

			   public void onStopTrackingTouch(SeekBar seekBar) {
			    // TODO Auto-generated method stub
			   } });
	}
}
