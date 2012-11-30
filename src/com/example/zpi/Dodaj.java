package com.example.zpi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class Dodaj extends Activity{
		
	int hour, minute;
    static final int TIME_DIALOG_ID = 0;
    static final int CHECKBOX_DIALOG_ID=1;
    static final int RADIOBUTTON_DIALOG_ID=2;
    static final int SEEKBAR_DIALOG_ID=3;
    boolean[] states = {false,false,false,false,false,false,false};
	String[] temp = null;
	ListView list;
	boolean wybor;
    boolean edycja=false;
	int i=0;
	String progres;
	String dni="";
	Button zmien;
	Button anuluj;
    Button usun;
	Button dodaj;
	TextView txt;
    ToggleButton wlacz;
    Harmonogram h=new Harmonogram();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dodaj_czas);
		//Harmonogram h=new Harmonogram();
        zmien=(Button) findViewById(R.id.zmiana);
        usun=(Button) findViewById(R.id.harmUsun);
        dodaj=(Button) findViewById(R.id.harmDod);
        wlacz=(ToggleButton)findViewById(R.id.dodWlacz);
		list = (ListView) findViewById(R.id.list);
        usun.setEnabled(false);
		//   showDialog(RADIOBUTTON_DIALOG_ID);
        try{
            h= (Harmonogram)getIntent().getExtras().get("harm");
            wlacz.setChecked(h.wl);
            zmien.setEnabled(false);
//            stanyDni(h);
            usun.setEnabled(true);
            edycja=true;
        }
        catch (Exception e){
            wlacz.setChecked(true);
            showDialog(RADIOBUTTON_DIALOG_ID);

        }
        switch (h.modul){
            case 0:
                setAdapter(1);
                break;
            case 1:
                setAdapter(2);
                break;
            case 4:
                setAdapter(0);
                break;
        }
		   addListListener();
		   addButtonListener();

	}
	public void addButtonListener() {

		zmien.setOnClickListener(new OnClickListener()
        {           
            public void onClick(View arg0){
            	showDialog(RADIOBUTTON_DIALOG_ID);
            }
            
        });
		anuluj=(Button) findViewById(R.id.harmAnl);
		anuluj.setOnClickListener(new OnClickListener()
        {           
            public void onClick(View arg0){
            	finish();
            }
            
        });
	}
	public void setAdapter(int x){
		final String[] items={pobZasob(R.string.godzSt),
				pobZasob(R.string.godzStop),
				pobZasob(R.string.powt)};
		final String[] itemsWoda={pobZasob(R.string.godzSt),pobZasob(R.string.godzStop),
				pobZasob(R.string.powt),pobZasob(R.string.temper)};
		final String[] itemsRolety={pobZasob(R.string.godzSt),pobZasob(R.string.godzStop),
				pobZasob(R.string.powt),pobZasob(R.string.ustPoz)};
		for(int i=0;i<7;i++)
			states[i]=false;
        for(String i: itemsWoda){
            Log.d("items",i);
        }

		switch(x){
		case 0:
			temp=items;
			break;
		case 1:
			temp=itemsWoda;
			break;
		case 2:
			temp=itemsRolety;
			break;
		}
        SpecialAdapter adapter=null;
        if(!edycja)
		    adapter = new SpecialAdapter(this, temp);
        else
            adapter = new SpecialAdapter(this, temp,h);
		list.setAdapter(adapter);
	}
	public void addListListener(){
		list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	switch(position){
            	case 0:
            		i=position;
                    showDialog(TIME_DIALOG_ID);
            		break;
            	case 1:
            		i=position;
                    showDialog(TIME_DIALOG_ID);
            		break;
            	case 2:
            		i=position;
            		showDialog(CHECKBOX_DIALOG_ID);
            		break;
            	case 3:
            		i=position;
            		showDialog(SEEKBAR_DIALOG_ID);
            	}
            }
        } );
	}
	public void changeItems(){
		String czas="";
		switch(i){
		case 0:
		case 1:
			if(minute>10)
				czas="Godzina start: "+hour+":"+minute;
			else
				czas="Godzina start: "+hour+":0"+minute;
			temp[i]=czas; 
			break;
		case 2:
			temp[i]="Powtarzanie: "+dni;
			dni="";
			break;
		case 3:
			if(wybor)
			temp[i]+=" "+progres+" st. C";
			else
				temp[i]+=" "+progres+"%";
		}
		SpecialAdapter adapter2 = new SpecialAdapter(getApplicationContext(), temp);
		list.setAdapter(adapter2);
		adapter2.notifyDataSetChanged();
	}
	@Override    
    protected Dialog onCreateDialog(int id) 
    {
        switch (id) {
            case TIME_DIALOG_ID:
                final TimePickerDialog tp;
                Log.d("wybor",""+i);
                if(i==0){
                    tp=new TimePickerDialog(this, mTimeSetListener, godziny(h.czasStart), minuty(h.czasStart), true);
                    Log.d("zegar1","klej");
                    tp.setCancelable(true);
                   /* tp.setOnCancelListener(new TimePickerDialog.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            Log.d("zegar3","klej");
                            dialogInterface.cancel();
                        }
                    });*/


                //return new TimePickerDialog(
                  //  this, mTimeSetListener, godziny(h.czasStart), minuty(h.czasStart), true);
                }
                else{
                    tp=new TimePickerDialog(this, mTimeSetListener, godziny(h.czasStop), minuty(h.czasStop), true);
                    Log.d("zegar2","klej");
                  //  return new TimePickerDialog(
                    //        this, mTimeSetListener, godziny(h.czasStop), minuty(h.czasStop), true);
                }
                tp.setOnDismissListener(new TimePickerDialog.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Log.d("zegar3","klej");
                        tp.dismiss();
                        dialogInterface.cancel();
                    }
                });

                return tp;
            case CHECKBOX_DIALOG_ID:
            	final CharSequence[] items = {
            			pobZasob(R.string.ponP), pobZasob(R.string.wtP),
            			pobZasob(R.string.srP),pobZasob(R.string.czP),
            			pobZasob(R.string.ptP),pobZasob(R.string.sobP),
            			pobZasob(R.string.ndP)
            			};
                
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(pobZasob(R.string.powt));
                for(int i=0;i<states.length;i++){
                    Log.d("stanyDial",""+states[i]);
                }
                builder.setMultiChoiceItems(items, stanyDni(h), new DialogInterface.OnMultiChoiceClickListener(){
                    public void onClick(DialogInterface dialogInterface, int item, boolean state) {
                    }
                });
                builder.setPositiveButton(pobZasob(R.string.ustaw), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SparseBooleanArray zaznaczone = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
                        int i=0;
                        String pom="";
                        if(zaznaczone.get(0)){ 
                        	dni+=pobZasob(R.string.pon)+" ";
                        	pom=pobZasob(R.string.ponP);
                        	states[0]=true;
                        	i++;
                        }
                        if(zaznaczone.get(1)){
                        	dni+=pobZasob(R.string.wt)+" ";
                        	pom=pobZasob(R.string.wtP);
                        	states[1]=true;
                        	i++;
                        }
                        if(zaznaczone.get(2)){
                        	dni+=pobZasob(R.string.sr)+" ";
                        	pom=pobZasob(R.string.srP);
                        	states[2]=true;
                        	i++;
                        }
                        if(zaznaczone.get(3)){
                        	dni+=pobZasob(R.string.cz)+" ";
                        	pom=pobZasob(R.string.czP);
                        	states[3]=true;
                        	i++;
                        }
                        if(zaznaczone.get(4)){
                        	dni+=pobZasob(R.string.pt)+" ";
                        	pom=pobZasob(R.string.ptP);
                        	states[4]=true;
                        	i++;
                        }
                        if(zaznaczone.get(5)){
                        	dni+=pobZasob(R.string.sb)+" ";
                        	pom=pobZasob(R.string.sobP);
                        	states[5]=true;
                        	i++;
                        }
                        if(zaznaczone.get(6)){
                        	dni+=pobZasob(R.string.nd)+" ";
                        	pom=pobZasob(R.string.ndP);
                        	states[6]=true;
                        	i++;
                        }
                        if(i==1)
                        	dni=pom;
                        if(i==7)
                        	dni=pobZasob(R.string.codz);
                        changeItems();
                    }
                });
                builder.setNegativeButton(pobZasob(R.string.harmAnl), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         dialog.cancel();
                    }
                });
                builder.create().show();
                break;
            case RADIOBUTTON_DIALOG_ID:
            	CharSequence []opcje={pobZasob(R.string.osw),pobZasob(R.string.wod),pobZasob(R.string.rol)};
            	AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle(pobZasob(R.string.tytRodzHarm));
                b.setSingleChoiceItems(opcje, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        SparseBooleanArray zaznaczone = ((AlertDialog) dialog).getListView().getCheckedItemPositions();
                        if (zaznaczone.get(0)) {
                            setAdapter(0);
                        }
                        if (zaznaczone.get(1)) {
                            setAdapter(1);
                            wybor = true;
                        }
                        if (zaznaczone.get(2)) {
                            setAdapter(2);
                            wybor = false;
                        }
                        dialog.dismiss();
                    }
                });
                b.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialogInterface.cancel();
                        finish();
                    }
                });
                b.create().show();
                break;
            case SEEKBAR_DIALOG_ID:
            	final Dialog sk=new Dialog(this);
            	LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
            	View layout=inflater.inflate(R.layout.seek_dialog, (ViewGroup)findViewById(R.id.dialog_lay));
            	sk.setContentView(layout);
            	sk.setTitle(pobZasob(R.string.ustPoz));
            	
            	txt=(TextView)layout.findViewById(R.id.dialog_status);
            	SeekBar seek=(SeekBar)layout.findViewById(R.id.dialog_seek_bar);
            	if(wybor)
            		seek.setMax(40);
            	else
            		seek.setMax(100);
                seek.setProgress(suwakProgres(h));
                txt.setText(""+suwakProgres(h));
            	seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					
					public void onStopTrackingTouch(SeekBar seekBar) {}
					
					public void onStartTrackingTouch(SeekBar seekBar) {}
					
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						int pom=0;
						if(wybor){
							pom=progress+40;
							txt.setText(""+pom);
							progres=""+pom;
						}
						else{
							txt.setText(""+progress);
							progres=""+progress;
							}
						
					}
				});
            	Button ok=(Button)layout.findViewById(R.id.dialog_ok);
            	ok.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						changeItems();
						sk.cancel();

					}
				});
            	Button anl=(Button)layout.findViewById(R.id.dialog_anl);
            	anl.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						sk.cancel();

					}
				});
            	sk.show();
        }
        
        return null;    
    }
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour)
        {
            hour = hourOfDay;
            minute = minuteOfHour;
            changeItems();

        }
    };
    private  TimePickerDialog.OnCancelListener mTCancelListener= new TimePickerDialog.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            dialogInterface.cancel();
        }
    };

	static class ViewHolder {
    TextView text;
	}
	public String pobZasob(int z){
		return getResources().getString(z);
	}
    public boolean[] stanyDni(Harmonogram h){
        String[] pom2={"Pon","Wt","Śr","Cz","Pt","Sob","Nd"};
        String[] pom3={"Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota","Niedziela","Codziennie"};
        try{
            String dni=h.dni;
            String[] pom=dni.split("[., ]+");
            if(pom.length>1){
                for(int i=0;i<pom.length;i++){
                    for(int j=0;j<pom2.length;j++){
                        if(pom[i].equals(pom2[j]))
                            states[j]=true;
                    }
                }
            }
            else{
                for(int i=0;i<pom2.length;i++){
                    if(pom[0].equals(pom3[i])){
                        if(i!=7){
                            states[i]=true;
                        }
                        else{
                            for(int j=0;j<states.length;j++){
                                states[j]=true;
                            }
                        }
                    }
                }

            }
        }
        catch (NullPointerException e){}
        return states;
    }
    public int suwakProgres(Harmonogram h){
        try{
            String[] pom=h.valStart.split("[st,% ]+");
            return Integer.parseInt(pom[0]);
        }
        catch (Exception e){
            return 0;
        }
    }
    public int godziny(int czas){
        try{
            return czas/100;
        }
        catch (Exception e){
            return 0;
        }

    }
    public int minuty(int czas){
        try{
            return czas%100;
        }
        catch (Exception e){
            return 0;
        }
    }

}
