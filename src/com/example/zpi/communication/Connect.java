package com.example.zpi.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Connect {

	private static final String url = "192.168.1.102:8080/servtest/";
	

	
	public static boolean isInternet(Context ctx){
		ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conMgr.getActiveNetworkInfo();
		if (net==null || !net.isConnected()){
			return false;
		}
		return true;
	}

	public static Response request(String module, String action) {
		URL link = null;
		Log.d("ble",url);
		try {
			link = new URL("http://"+url + "do?action=" + module + "." + action);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection urlConnection = null;
		StringBuffer sb = null;
		try {
			urlConnection = (HttpURLConnection) link.openConnection();
			Log.d("code",""+urlConnection.getResponseCode());
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

	}
}
