package com.example.zpi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class Dodaj extends Activity{
		
	int hour, minute;
    static final int TIME_DIALOG_ID = 0;
    static final int CHECKBOX_DIALOG_ID=1;
    static final int RADIOBUTTON_DIALOG_ID=2;
    static final int SEEKBAR_DIALOG_ID=3;
    boolean[] states = {false, false, false,false,false,false,false};
	String[] temp = null;
	ListView list;
	boolean wybor;
	int i=0;
	String progres;
	String dni="";
	Button zmien;
	Button anuluj;
	Button dodaj;
	TextView txt;
	public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.dodaj_czas);
		 
		   list = (ListView) findViewById(R.id.list);
		   showDialog(RADIOBUTTON_DIALOG_ID);
		   addListListener();
		   addButtonListener();

	}
	public void addButtonListener() {
		zmien=(Button) findViewById(R.id.zmiana);
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
		SpecialAdapter adapter = new SpecialAdapter(this, temp);
		list.setAdapter(adapter);
	}
	public void addListListener(){
		list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	switch(position){
            	case 0:
            		showDialog(TIME_DIALOG_ID);
            		i=position;
            		break;
            	case 1:
            		showDialog(TIME_DIALOG_ID);
            		i=position;
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
                return new TimePickerDialog(
                    this, mTimeSetListener, hour, minute, true);
            case CHECKBOX_DIALOG_ID:
            	final CharSequence[] items = {
            			pobZasob(R.string.ponP), pobZasob(R.string.wtP),
            			pobZasob(R.string.srP),pobZasob(R.string.czP),
            			pobZasob(R.string.ptP),pobZasob(R.string.sobP),
            			pobZasob(R.string.ndP)
            			};
                
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(pobZasob(R.string.powt));
                builder.setMultiChoiceItems(items, states, new DialogInterface.OnMultiChoiceClickListener(){
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

                    public void onClick(DialogInterface dialog, int item){
                    	SparseBooleanArray zaznaczone = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
                    	if(zaznaczone.get(0)){
                    		setAdapter(0);
                    	}
                    	if(zaznaczone.get(1)){
                    		setAdapter(1);
                    		wybor=true;
                    	}
                    	if(zaznaczone.get(2)){
                    		setAdapter(2);
                    		wybor=false;
                    	}
                    	dialog.cancel();
                    }});
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
	static class ViewHolder {
    TextView text;
	}
	public String pobZasob(int z){
		return getResources().getString(z);
	}


}
