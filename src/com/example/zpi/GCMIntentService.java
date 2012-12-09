package com.example.zpi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import com.example.zpi.alerts.Notyfikacje;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;
import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {
	
	final private static String SENDER_ID = "303941619301";

	
	public GCMIntentService(){
		super(SENDER_ID);

	}

	@Override
	protected void onError(Context arg0, String arg1) {
		Toast.makeText(arg0, "Error: "+arg1, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO What's up tutaj

        Log.d("messageGCM",arg1.getExtras().getString("event"));
        String event=arg1.getExtras().getString("event");
        if(event.equals("ALARM")){
            AudioManager am=(AudioManager) arg0.getSystemService(Context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            NotificationManager mn= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent i=new Intent(arg0,WywolanieAlarmu.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
            Notification n= new Notification(R.drawable.ic_launcher,"ALARM!",System.currentTimeMillis()+5000);
            n.setLatestEventInfo(this, "Uruchomił się alarm!", "Powiadom POLICJE!", pIntent);
            n.flags |= Notification.FLAG_AUTO_CANCEL;
            n.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE ;
            mn.notify(1,n);
        }


//		throw new UnsupportedOperationException();
	}

	@Override
	protected void onRegistered(Context arg0, String regId) {
        Connect c = new Connect(arg0);
        try {
            c.requestRegister(regId);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	@Override
	protected void onUnregistered(Context arg0, String regId) {

		// TODO unregister do serwera tutaj
		//throw new UnsupportedOperationException();
	}

}
