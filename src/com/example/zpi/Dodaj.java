package com.example.zpi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

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
    int opcja;
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
    Connect c;
    Harmonogram h=new Harmonogram();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        c=new Connect(this);
		setContentView(R.layout.dadaj_harmonogram);
        zmien=(Button) findViewById(R.id.zmiana);
        usun=(Button) findViewById(R.id.harmUsun);
        dodaj=(Button) findViewById(R.id.harmDod);
        wlacz=(ToggleButton)findViewById(R.id.dodWlacz);
		list = (ListView) findViewById(R.id.list);
        usun.setEnabled(false);

        try{
            h= (Harmonogram)getIntent().getExtras().get("harm");
            wlacz.setChecked(h.isWl());
            zmien.setEnabled(false);
            usun.setEnabled(true);
            wybor=h.getModul()==0?true:false;
            setTitle("Edycja harmonogramu");
            edycja=true;
        }
        catch (Exception e){
            wlacz.setChecked(true);
            setTitle("Dodawanie harmonogramu");
            showDialog(RADIOBUTTON_DIALOG_ID);

        }
        switch (h.getModul()){
            case 0:
                setAdapter(1);
                opcja=1;
                break;
            case 1:
                setAdapter(2);
                opcja=2;
                break;
            case 4:
                setAdapter(0);
                opcja=0;
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
        dodaj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(czyPoprawnieWypelniony()){
                        c.requestSetHarm(h);
                        Toast.makeText(getApplicationContext(), "Zapisano harmonogram", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie dane!", Toast.LENGTH_LONG).show();
                    }
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();
                    InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                    internetAlert.zwrocAlert();
                }
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
            zmien.setText("Oświetlenie");
			break;
		case 1:
			temp=itemsWoda;
            zmien.setText("Woda");
			break;
		case 2:
			temp=itemsRolety;
            zmien.setText("Roleta");
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
            if(minute>10){
                czas="Godzina start: "+hour+":"+minute;
                ustawHarmonogram(""+(hour>=10?hour:"0"+hour)+":"+minute);
            }
            else{
                czas="Godzina start: "+hour+":0"+minute;
                ustawHarmonogram(""+(hour>=10?hour:"0"+hour)+":0"+minute);
            }
            temp[i]=czas;
            break;
		case 1:
			if(minute>10){
				czas="Godzina stop: "+hour+":"+minute;
                ustawHarmonogram(""+(hour>=10?hour:"0"+hour)+":"+minute);
            }
			else{
				czas="Godzina stop: "+hour+":0"+minute;
                ustawHarmonogram(""+(hour>=10?hour:"0"+hour)+":0"+minute);
            }
			temp[i]=czas; 
			break;
		case 2:
			temp[i]="Powtarzanie: "+dni;
			dni="";
			break;
		case 3:
			if(wybor)
			temp[i]="Temperatura: "+progres+" st. C";
			else
				temp[i]="Stopien zamknięcia: "+progres+"%";
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
                if(i==0){
                    tp=new TimePickerDialog(this, mTimeSetListener, godziny(h.getCzasStart()), minuty(h.getCzasStart()), true);
                    tp.setCancelable(true);

                }
                else{
                    tp=new TimePickerDialog(this, mTimeSetListener, godziny(h.getCzasStop()), minuty(h.getCzasStop()), true);
                }
                tp.setOnDismissListener(new TimePickerDialog.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
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
                        String pom2="";
                        if(zaznaczone.get(0)){ 
                        	dni+=pobZasob(R.string.pon)+" ";
                        	pom=pobZasob(R.string.ponP);
                            pom2+=1;
                        	states[0]=true;

                        	i++;
                        }
                        if(zaznaczone.get(1)){
                        	dni+=pobZasob(R.string.wt)+" ";
                        	pom=pobZasob(R.string.wtP);
                        	states[1]=true;
                            pom2+=2;
                        	i++;
                        }
                        if(zaznaczone.get(2)){
                        	dni+=pobZasob(R.string.sr)+" ";
                        	pom=pobZasob(R.string.srP);
                        	states[2]=true;
                            pom2+=3;
                        	i++;
                        }
                        if(zaznaczone.get(3)){
                        	dni+=pobZasob(R.string.cz)+" ";
                        	pom=pobZasob(R.string.czP);
                        	states[3]=true;
                            pom2+=4;
                        	i++;
                        }
                        if(zaznaczone.get(4)){
                        	dni+=pobZasob(R.string.pt)+" ";
                        	pom=pobZasob(R.string.ptP);
                        	states[4]=true;
                            pom2+=5;
                        	i++;
                        }
                        if(zaznaczone.get(5)){
                        	dni+=pobZasob(R.string.sb)+" ";
                        	pom=pobZasob(R.string.sobP);
                        	states[5]=true;
                            pom2+=6;
                        	i++;
                        }
                        if(zaznaczone.get(6)){
                        	dni+=pobZasob(R.string.nd)+" ";
                        	pom=pobZasob(R.string.ndP);
                        	states[6]=true;
                            pom2+=7;
                        	i++;
                        }
                        if(i==1)
                        	dni=pom;
                        if(i==7)
                        	dni=pobZasob(R.string.codz);
                        changeItems();
                        ustawHarmonogram(pom2);
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
                            opcja=0;
                        }
                        if (zaznaczone.get(1)) {
                            setAdapter(1);
                            wybor = true;
                            opcja=1;
                        }
                        if (zaznaczone.get(2)) {
                            setAdapter(2);
                            wybor = false;
                            opcja=2;
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
            	if(wybor){
            		seek.setMax(40);
                    txt.setText(""+suwakProgres(h));
                    seek.setProgress(suwakProgres(h)-40);
                }
            	else{
            		seek.setMax(100);
                    txt.setText(""+suwakProgres(h));
                    seek.setProgress(suwakProgres(h));
                }
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
                        ustawHarmonogram(progres);
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
        try{
        String d=h.getDni();

        if(!d.equals(null)){
            char[] pom=d.toCharArray();
            if(pom.length==1)
                states[Integer.valueOf(String.valueOf(pom[0]))-1]=true;
            else if(pom.length==7){
                for(int i=0;i<states.length;i++){
                    states[i]=true;
                }
            }
            else{
                for(int i=0;i<pom.length;i++){
                    states[Integer.parseInt(String.valueOf(pom[i]))-1]=true;
                }
            }
        }
        }
        catch (NullPointerException e){}

        return states;

    }
    public int suwakProgres(Harmonogram h){
        try{
            String[] pom=h.getValStart().split("[st,% ]+");
            Log.d("progresSuwak",pom[0]);
            return Integer.parseInt(pom[0]);
        }
        catch (Exception e){
            return 0;
        }
    }
    public int godziny(String czas){
        String[] pom;
        try{
            pom=czas.split(":");
            return Integer.parseInt(pom[0]);
        }
        catch (Exception e){
            return 0;
        }

    }
    public int minuty(String czas){
        String[] pom;
        try{
            pom=czas.split(":");
            return Integer.parseInt(pom[1]);
        }
        catch (Exception e){
            return 0;
        }
    }

    public void ustawHarmonogram(String wart){

        h.setWl(wlacz.isChecked());
        Log.d("modul",""+opcja);
        switch (opcja){
            case 0:
                h.setValStart("true");
                h.setModul(4);
                break;
            case 1:
                h.setModul(0);
                break;
            case 2:
                h.setModul(1);
                break;
        }
        switch (i){
            case 0:
                h.setCzasStart(wart);
                break;
            case 1:
                h.setCzasStop(wart);
                break;
            case 2:
                h.setDni(wart);
                break;
            case 3:
                h.setValStart(wart);
                break;
        }
        h.setValStop("10");
    }
    public boolean czyPoprawnieWypelniony(){
        boolean wypelnione=false;

        for(int i=0;i<temp.length;i++){
            if((temp[i].split("[ ]+")).length>1)
                wypelnione=true;
            else
                wypelnione=false;
        }

        return wypelnione;
    }
}
