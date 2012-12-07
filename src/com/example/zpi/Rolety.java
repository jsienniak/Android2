package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.zpi.communication.*;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

public class Rolety extends Activity implements ResponseListener{
	Button otw;
	Button zamk;
	Button wroc;
    Button ustaw;
    Button wyczysc;
	ImageView imv;
	VerticalSeekBar_Reverse sb;
    Connect c;
    int prog;
	public int[] roletaImg={
			R.drawable.r0,R.drawable.r1,R.drawable.r2,
			R.drawable.r3,R.drawable.r4,R.drawable.r5,R.drawable.r6,
			R.drawable.r7,R.drawable.r8,R.drawable.r9,R.drawable.r10,
			R.drawable.r11,R.drawable.r12,R.drawable.r13,R.drawable.r14,
			R.drawable.r15,R.drawable.r16,R.drawable.r17,R.drawable.r18,
			R.drawable.r19,R.drawable.r20,R.drawable.r21,R.drawable.r22,
			R.drawable.r23,R.drawable.r24,R.drawable.r25,R.drawable.r26,
			R.drawable.r27,R.drawable.r28,R.drawable.r29,R.drawable.r30,
			R.drawable.r31,R.drawable.r32,R.drawable.r33,R.drawable.r34,
			R.drawable.r35,R.drawable.r36,R.drawable.r37,R.drawable.r38,
			R.drawable.r39,R.drawable.r40,R.drawable.r41,R.drawable.r42,
			R.drawable.r43,R.drawable.r44,R.drawable.r45,R.drawable.r46,
			R.drawable.r47,R.drawable.r48,R.drawable.r49,R.drawable.r50,
            R.drawable.r51,R.drawable.r52,R.drawable.r53,R.drawable.r54,
			R.drawable.r55,R.drawable.r56,R.drawable.r57,R.drawable.r58,
			R.drawable.r59,R.drawable.r60,R.drawable.r61,R.drawable.r62,
			R.drawable.r63,R.drawable.r64,R.drawable.r65,R.drawable.r66,
			R.drawable.r67,R.drawable.r68,R.drawable.r69,R.drawable.r70,
			R.drawable.r71,R.drawable.r72,R.drawable.r73,R.drawable.r74,
			R.drawable.r75,R.drawable.r76,R.drawable.r77,R.drawable.r78,
			R.drawable.r79,R.drawable.r80,R.drawable.r81,R.drawable.r82,
			R.drawable.r83,R.drawable.r84,R.drawable.r85,R.drawable.r86,
			R.drawable.r87,R.drawable.r88,R.drawable.r89,R.drawable.r90,
			R.drawable.r91,R.drawable.r92,R.drawable.r93,R.drawable.r94,
			R.drawable.r95,R.drawable.r96,R.drawable.r97,R.drawable.r98,
			R.drawable.r99,R.drawable.r100
	};
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolety);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
            c.requestGet(1, 0);
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        addListenerOnButton();
    }
	public void addListenerOnButton(){
		imv=(ImageView) findViewById(R.id.rolObrRol);

		sb=(VerticalSeekBar_Reverse)findViewById(R.id.vertical_Seekbar);
        imv.setImageResource(roletaImg[prog]);

		
		otw =(Button)findViewById(R.id.otwR);
		zamk =(Button)findViewById(R.id.zamR);
		wroc =(Button)findViewById(R.id.wrR);
       /* switch (prog){
            case 0:
                otw.setEnabled(false);
                break;
            case 100:
                zamk.setEnabled(false);
                break;
        }*/
		otw.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                try {
                    c.requestSet(1, 0, "" + 0);
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                zamk.setEnabled(true);
                otw.setEnabled(false);
                sb.setProgress(0);
                imv.setImageResource(roletaImg[0]);
            }
        });
		zamk.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                try {
                    c.requestSet(1, 0, "" + 100);
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                sb.setProgress(100);
                imv.setImageResource(roletaImg[100]);
                zamk.setEnabled(false);
                otw.setEnabled(true);
                sb.klik();
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
			    imv.setImageResource(roletaImg[progress]);
                prog=progress;
               }
			   public void onStartTrackingTouch(SeekBar seekBar) {

			   }
			   public void onStopTrackingTouch(SeekBar seekBar) {
                   try {
                       c.requestSet(1, 0, "" + seekBar.getProgress());
                   } catch (NoInternetException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   } catch (ServerErrorException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   }
               } });
        ustaw=(Button) findViewById(R.id.rolUstaw);
        ustaw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Harmonogramy.class);
                i.putExtra("modul",1);
                startActivity(i);
            }
        });

        //wyczysc=(Button) findViewById(R.id.rolWyczysc);
	}

    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){

        }
        if(res.getType()==Response.GET){
            prog = Integer.parseInt(res.getValue());
            sb.setProgress(prog);
            imv.setImageResource(roletaImg[prog]);
            sb.klik();
        }
    }
}
