package com.example.zpi.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 07.12.12
 * Time: 06:48
 * To change this template use File | Settings | File Templates.
 */
public class InternetAlert extends Activity{
    Context ctx;

    public InternetAlert(Context context){
        ctx=context;
    }

    public AlertDialog.Builder zwrocAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
        builder.setTitle("Brak połączenia z internetem");
        builder.setMessage("Aby korzystać z aplikacji wymagane jest połączenie z internetem. " +
            "Uruchom aplikacje gdy nawiązane zostanie połączenie internetowe");
        builder.setPositiveButton("OK", new Dialog.OnClickListener()
            {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }});
    builder.create().show();
        return builder;
    }
}
