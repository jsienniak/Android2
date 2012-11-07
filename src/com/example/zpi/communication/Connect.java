package com.example.zpi.communication;

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

public class Connect {

	private static final String url = "192.168.1.101:8080/servtest/";
	private Context ctx = null;

	public Connect(Context c) {
		ctx = c;
	}

	public boolean isInternet() {
		ConnectivityManager conMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conMgr.getActiveNetworkInfo();
		if (net == null || !net.isConnected()) {
			return false;
		}
		return true;
	}

	public Response request(String... action) throws NoInternetException,
			ServerErrorException {
		if (!isInternet()) {
			throw new NoInternetException();
		}
		AsyncTask<String, Void, Response> at = new AsyncTask<String, Void, Response>() {

			@Override
			protected Response doInBackground(String... arg0) {
				URL link = null;
				String u = "";
				switch (arg0.length) {
				case 0:
					u="ping";
					break;
				case 2:
					u="set&id="+arg0[0]+"&port_num="+arg0[1];
					break;
				case 3:
					u="set&id="+arg0[0]+"&port_num="+arg0[1]+"&value="+arg0[2];
					break;
				default:
					throw new Error("Wrong argument list!");
				}
				try {
					link = new URL("http://" + url + "do?action=module."+u);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpURLConnection urlConnection = null;
				StringBuffer sb = null;
				try {
					urlConnection = (HttpURLConnection) link.openConnection();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()));
					sb = new StringBuffer();
					String line;
					while ((line = in.readLine()) != null) {
						sb.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					urlConnection.disconnect();
				}
				// return null;

				try {
					return XMLParser.parse(new StringReader(sb.toString()));
				} catch (ServerErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}
		};
		try {
			return at.execute(action).get();
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
