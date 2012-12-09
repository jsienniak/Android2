package com.example.zpi.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.*;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import com.example.zpi.Harmonogram;
import com.example.zpi.Profil;

public class Connect {

	//public static String url="156.17.234.1:8080/zpi_server/";
    public static String url="";

    //private static final String url = "192.168.1.103:8080/zpi_server/";
	private Context ctx = null;
    private ArrayList<ResponseListener> listeners = new ArrayList<ResponseListener>();


    public Connect(Context c) {
		ctx = c;
        System.out.print(url);
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
        //login(1);
        request("harmonogram.get");
    }

    public void requestActivateHarm(int id)throws ServerErrorException, NoInternetException {
       // login(1);
        request("harmonogram.activate&id="+id);
    }

    public void requestDeactivateHarm(int id)throws ServerErrorException, NoInternetException {
        //login(1);
        request("harmonogram.deactivate&id="+id);
    }

    public void requestDelHarm(int id)throws ServerErrorException, NoInternetException {
        //login(1);
        request("harmonogram.delete&id="+id);
    }

    public void requestDelProfile(int id)throws ServerErrorException, NoInternetException {
        //login(1);
        request("profile.delete&id="+id);
    }

    public void requestGetProfile()throws ServerErrorException, NoInternetException {
       // login(1);
        request("profile.get");
    }

    public void requestSetHarm(Harmonogram h)throws ServerErrorException, NoInternetException {
        //login(1);
        request(h.getDni(),h.getCzasStart(),h.getCzasStop(),""+h.getModul(),""+h.getPort(),h.getValStart(),h.getValStop(),""+(h.isWl()?1:0));
    }

    private void login(int id) throws ServerErrorException, NoInternetException {
        request("user.login&id="+id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }

    public void requestSetProfile(Profil p)throws ServerErrorException, NoInternetException {
        //login(1);
        request(p.getNazwa(),p.getWoda(),p.getRoleta(),p.getSwiatlo());
    }

    public void requestUseProfile(int id) throws ServerErrorException, NoInternetException {
        //login(1);
        request("profile.use&id="+id);
    }

    public void requestGet(int m, int p) throws ServerErrorException, NoInternetException {
       //login(1);
        request(""+m,""+p);
    }

    public void requestSet(int m, int p, String val) throws ServerErrorException, NoInternetException {
        //login(1);
        request(""+m,""+p,val);
    }

	private void request(String... action) throws NoInternetException,
			ServerErrorException {
		if (!isInternet()) {
			throw new NoInternetException();
		}
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
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
                     if(u.equals("harmonogram.get")){
                        res.setType(Response.GETHARM);
                     } else {
                         res.setType(Response.GETPROFILE);
                     }
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
                    case 4:
                     u="profile.set&name="+t[0]+"&m1_v="+t[1]+"&m2_v="+t[2]+"&m3_v="+t[3];
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
                    urlConnection.setRequestProperty("TOKEN","eloprotoken");
                    //urlConnection.setReadTimeout(1000);
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

    public void requestRegister(String regId) throws ServerErrorException, NoInternetException {
        //login(1);
        request("device.register&device="+regId);
    }
}
