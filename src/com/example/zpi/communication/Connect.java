package com.example.zpi.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import com.example.zpi.Harmonogram;

public class Connect {

	private static final String url = "156.17.234.1:8080/zpi_server/";
    //private static final String url = "192.168.1.106:8080/zpi_server/";
	private Context ctx = null;
    private ArrayList<ResponseListener> listeners = new ArrayList<ResponseListener>();


    public Connect(Context c) {
		ctx = c;
	}

    public void addResponseListener(ResponseListener rl){
        listeners.add(rl);
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

    public void requestGetHarm()throws ServerErrorException, NoInternetException {
        request("harmonogram.get");
    }

    public void requestSetHarm(Harmonogram h)throws ServerErrorException, NoInternetException {
        request(h.getDni(),h.getCzasStart(),h.getCzasStop(),""+h.getModul(),""+h.getPort(),h.getValStart(),h.getValStop(),""+(h.isWl()?1:0));
    }

    public void requestGet(int m, int p) throws ServerErrorException, NoInternetException {
        request(""+m,""+p);
    }

    public void requestSet(int m, int p, String val) throws ServerErrorException, NoInternetException {
        request(""+m,""+p,val);
    }

	private void request(String... action) throws NoInternetException,
			ServerErrorException {
		if (!isInternet()) {
			throw new NoInternetException();
		}
		AsyncTask<String, Void, Response> at = new AsyncTask<String, Void, Response>() {

            protected void onPostExecute(Response res){
                raiseEvent(res);
            }

			@Override
			protected Response doInBackground(String... t) {
				URL link = null;
				String u = "";
                Response res = new Response();
				switch (t.length) {
                case 1:
                     u=t[0];
                     res.setType(Response.GETHARM);
                     break;
				case 2:
					u="module.get&id="+t[0]+"&port_num="+t[1];
                    res.setModule(Integer.parseInt(t[0]));
                    res.setPort(Integer.parseInt(t[1]));
                    res.setType(Response.GET);
                    break;
				case 3:
					u="module.set&id="+t[0]+"&port_num="+t[1]+"&value="+t[2];
                    res.setModule(Integer.parseInt(t[0]));
                    res.setPort(Integer.parseInt(t[1]));
                    res.setType(Response.SET);
					break;
                case 8:
                    u="harmonogram.set&dni="+t[0]+"&g_start="+t[1]+"&g_stop="+t[2]+"&m_id="+t[3]+"&p="+t[4]+"&w_start="+t[5]+"&w_end="+t[6]+"&active="+t[7];
                    break;
				default:
					throw new Error("Wrong argument list!");
				}
				try {
					String s = "http://" + url + "do?action="+u;
                    Log.d("URL",s);
                    link = new URL(s);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				HttpURLConnection urlConnection = null;
				StringBuffer sb = null;
				try {
					urlConnection = (HttpURLConnection) link.openConnection();
                    urlConnection.setReadTimeout(1000);
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
                    res.setERROR(true);
                    return res;
				} finally {
					urlConnection.disconnect();
				}
				// return null;

				try {
					return XMLParser.parse(new StringReader(sb.toString()),res);
				} catch (ServerErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}
		};
        at.execute(action);

	}

    private void raiseEvent(Response res) {
        for(ResponseListener l: listeners){
            l.processResponse(res);
        }
    }

}
