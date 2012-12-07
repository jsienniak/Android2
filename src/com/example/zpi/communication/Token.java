package com.example.zpi.communication;

import android.accounts.*;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 07.12.12
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class Token {

    private ArrayList<TokenListener> listeners = new ArrayList<TokenListener>();
    private static Activity act = null;

    public void addTokenListener(TokenListener tl){
        listeners.add(tl);
    }

    //public void


    public void getToken(Activity action){
        AsyncTask<Activity, Void, String> at = new AsyncTask<Activity, Void, String>() {

            protected void onPostExecute(String res){
                raiseEvent(res);
            }

            @Override
            protected String doInBackground(Activity ... p) {
                String token = "nima" ;
                Account[] accounts = AccountManager.get(p[0].getApplicationContext()).getAccountsByType("com.google");
                AccountManagerFuture<Bundle> accFut = AccountManager.get(p[0]).getAuthToken(accounts[0],"ah",null,p[0],null,null);
                try {
                    Bundle authTokenBundle = accFut.getResult();
                    token = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
                } catch (OperationCanceledException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (AuthenticatorException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                 return token;
            }
        };
        at.execute(action);

    }

    private void raiseEvent(String res) {
        for(TokenListener l: listeners){
            l.processToken(res);
        }
    }


}
