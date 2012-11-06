package com.example.testy.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class Connect{

	private static final String url = "192.168.1.101:8080/servtest/";
	private Context ctx = null;
	
	public Connect(Context c){
		ctx=c;
	}

	
	public boolean isInternet(){
		ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conMgr.getActiveNetworkInfo();
		if (net==null || !net.isConnected()){
			return false;
		}
		return true;
	}

	public Response request(String module, String action) throws NoInternetException {
		if (!isInternet()){
			throw new NoInternetException();
		}
		AsyncTask<String,Void,Response> at = new AsyncTask<String,Void,Response>(){
			

			@Override
			protected Response doInBackground(String... arg0) {
				URL link = null;
				Log.d("ble",url);
				try {
					link = new URL("http://"+url + "do?action=" + arg0[0] + "." + arg0[1]);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpURLConnection urlConnection = null;
				StringBuffer sb = null;
				try {
					urlConnection = (HttpURLConnection) link.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(
		  					urlConnection.getInputStream()));
		  			sb = new StringBuffer();
		  			String line;
		  			while ((line = in.readLine())!=null){
		  				sb.append(line);
		  			}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					urlConnection.disconnect();
				}
				//return null;

				return XMLParser.parse(new StringReader(sb.toString()));
			
			
		}};
		try {
			return at.execute(module,action).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
