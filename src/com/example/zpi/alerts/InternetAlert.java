package com.example.zpi.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.example.zpi.MainActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 07.12.12
 * Time: 06:48
 * To change this template use File | Settings | File Templates.
 */
public class InternetAlert extends Activity{
    Context context;

    public InternetAlert(Context context){
        this.context =context;
    }
    public AlertDialog.Builder zwrocAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Brak połączenia z internetem");
        builder.setMessage("Aby korzystać z aplikacji wymagane jest połączenie z internetem. " +
            "Uruchom aplikacje gdy nawiązane zostanie połączenie internetowe");
        final Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("zakoncz", true);
        //intent.putExtra("zakoncz", true);
        builder.setPositiveButton("OK", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("zakoncz", true);
                startActivity(intent);
                ((Activity) context).finish();

            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();

                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("zakoncz", true);
                startActivity(intent);
                ((Activity) context).finish();

            }
        });
        builder.create().show();
        //builder.setCancelable(true);

        return builder;
    }
}
