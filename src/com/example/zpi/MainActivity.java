package com.example.zpi;

import android.accounts.*;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.zpi.communication.*;
import com.example.zpi.alerts.*;
import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.content.Intent;
import android.view.View.OnClickListener;

import java.io.IOException;

public class MainActivity extends Activity implements ResponseListener, TokenListener {
	ToggleButton brama;
	Button woda;
	ToggleButton alarm;
	Button rolety;
	Button ogrod;
	Button status;
	Button harmon;
    Button profil;
    EditText haslo;
    Boolean statusAlarm;
    Connect c;
    Dialog sk;
    protected AccountManager accountManager;
    int i=0;

    final private static String SENDER_ID = "303941619301";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Connect.url=sharedPrefs.getString("adres_ip","NULL");
        Token t = new Token();
        t.addTokenListener(this);
        t.getToken(this);
        try{
            if(getIntent().getExtras().containsKey("notification")){

            }
        }
        catch (Exception e){
            testuNotyfikacje();
        }
        setContentView(R.layout.activity_main);
        c=new Connect(this);
        c.addResponseListener(this);
        try {
            c.requestGet(2,0);
            c.requestGet(3,0);
        } catch (ServerErrorException e) {
            e.printStackTrace();
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert ia=new InternetAlert(this);
            ia.zwrocAlert();
        }

        //Log.d("cos",""+accounts[0]);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        GCMRegistrar.unregister(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, SENDER_ID);
        }

        addListenerOnButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Connect.url=sharedPrefs.getString("adres_ip","NULL");
        try{
        if(getIntent().getExtras().getBoolean("zakoncz")==true)
            finish();
        }
        catch (Exception e){

        }
    }

    private void testuNotyfikacje() {
        AudioManager am=(AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Intent i=new Intent(getApplicationContext(),WywolanieAlarmu.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);

        NotificationManager mn= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification n= new Notification(R.drawable.ic_launcher,"ALARM!",System.currentTimeMillis()+5000);
        n.setLatestEventInfo(this, "Uruchomił się alarm!", "Powiadom POLICJE!", pIntent);
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE ;
        mn.notify(1,n);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ustawienia:
                Intent i =new Intent(getApplicationContext(),Ustawienia.class);
                startActivity(i);
                break;
            case R.id.menu_odswiezanie:
                odswiez();
                break;

        }
        return true;

    }
    public void odswiez(){
        try {
            c.requestGet(2,0);
            c.requestGet(3,0);
        } catch (ServerErrorException e) {
            e.printStackTrace();
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert ia=new InternetAlert(this);
            ia.zwrocAlert();
        }

    }

    public void addListenerOnButton(){
    	brama= (ToggleButton) findViewById(R.id.brama);
    	woda=(Button)findViewById(R.id.woda);
    	alarm= (ToggleButton) findViewById(R.id.alarm);
    	rolety=(Button)findViewById(R.id.rolety);
    	ogrod=(Button)findViewById(R.id.ogrod);
    	status=(Button)findViewById(R.id.status);
    	harmon=(Button)findViewById(R.id.harmonogram);
        profil=(Button) findViewById(R.id.profil);
    	brama.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			if(brama.isChecked()){
                Toast.makeText(getApplicationContext(), "Brama otwarta", Toast.LENGTH_SHORT).show();
                try {
                    c.requestSet(2,0,"true");
                    Log.d("alarm",""+alarm.isChecked());
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                    internetAlert.zwrocAlert();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Brama zamknięta", Toast.LENGTH_SHORT).show();
                try {
                    c.requestSet(2,0,"false");
                } catch (ServerErrorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoInternetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
			}
		});
    	woda.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Woda.class);
			startActivity(i);
			}
		});

    	alarm.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
                alarm.toggle();
                onCreateDialog();
			}
		});
    	rolety.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Rolety.class);
			startActivity(i);
			}
		});
    	ogrod.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Ogrod.class);
			startActivity(i);
			}
		});
    	status.setOnClickListener(new OnClickListener() {
    		
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Status.class);
			startActivity(i);
			}
		});
        profil.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Profile.class);
                startActivity(i);
            }
        });
    	harmon.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Harmonogramy.class);
			startActivity(i);
			}
		});
    }
    protected void onCreateDialog() {

         sk=new Dialog(this);
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.alarm_dialog, (ViewGroup)findViewById(R.id.dialog_lay_alr));
        haslo=(EditText)layout.findViewById(R.id.alrHaslDial);
        haslo.setInputType(InputType.TYPE_CLASS_NUMBER);
        haslo.setTransformationMethod(PasswordTransformationMethod.getInstance());
            sk.setContentView(layout);
            sk.setTitle("Wprowadz PIN");
            Button ok=(Button)layout.findViewById(R.id.alrOkDial);
            Button anl=(Button)layout.findViewById(R.id.alrAnlDial);
            ok.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    try {
                        c.requestGet(3,1);
                    } catch (ServerErrorException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (NoInternetException e) {
                        e.printStackTrace();
                        InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                        internetAlert.zwrocAlert();
                    }
                }});

            anl.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    sk.cancel();
                }});
            sk.show();
    }
   /* protected void brakInternetu(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Brak połączenia z internetem");
        builder.setMessage("Aby korzystać z aplikacji wymagane jest połączenie z internetem. " +
                "Uruchom aplikacje gdy nawiązane zostanie połączenie internetowe");
        builder.setPositiveButton("OK", new Dialog.OnClickListener()

        {
              @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                 finish();
            }
        });
        builder.create().show();
    }
    protected void problemSerwer(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Brak połączenia z serwerem");
        builder.setMessage("Wynikł problem połączenia z serwerem systemu. Zrestartuj serwer poczym wciśnij OK");
        builder.setPositiveButton("OK", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                //Intent intent = getIntent();
                //finish();
               // startActivity(intent);

            }
        });
        builder.create().show();
    }*/

    @Override
    public void processResponse(Response res) {
        if(res.isERROR())   {
            if(i==0){
                ServerAlert servP=new ServerAlert(this);
                servP.zwrocAlert();
                i++;
            }
        }
        else{
        if(res.getType()==Response.GET){
            if(res.getModule()==2){
                Boolean statusBramy = Boolean.valueOf(res.getValue());
                brama.setChecked(statusBramy);
            }
            else
            if(res.getModule()==3){
                if(res.getPort()==0){
                    statusAlarm = Integer.parseInt(res.getValue())>0;
                    alarm.setChecked(statusAlarm);
                }
                else{
                    if(res.getValue().equals(haslo.getText().toString())){
                            try {
                                Log.d("statusAlr",""+statusAlarm);
                                c.requestSet(3,0,""+(statusAlarm?0:1));
                                if(alarm.isChecked())
                                    Toast.makeText(getApplicationContext(), "Alarm uzbrojony", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Alarm rozbrojony", Toast.LENGTH_SHORT).show();
                                sk.cancel();
                            } catch (ServerErrorException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            } catch (NoInternetException e) {
                                e.printStackTrace();
                                InternetAlert internetAlert=new InternetAlert(getApplicationContext());
                                internetAlert.zwrocAlert();
                            }
                             catch (NullPointerException e){
                                ServerAlert servP=new ServerAlert(this);
                                servP.zwrocAlert();
                             }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Hasło nieprawidłowe, spróbuj ponownie", Toast.LENGTH_LONG).show();
                    }
                }

            }

        }
        else {
            if(res.getModule()==3)
                alarm.setChecked(statusAlarm = !statusAlarm);
        }
    }
    }

    @Override
    public void processToken(String token) {
        Log.d("token",token);
    }
}
