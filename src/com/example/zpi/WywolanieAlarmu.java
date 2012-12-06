package com.example.zpi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 30.11.12
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public class WywolanieAlarmu extends Activity {
    Button tak;
    Button nie;
    public String tel="tel:123";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_wywolanie);
        nie=(Button)findViewById(R.id.wywAlarmNie);
        tak=(Button)findViewById((R.id.wywAlarmTak));

        addListners();

    }

    private void addListners() {
        nie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent=new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(tel));
                startActivity(callIntent);
                finish();
            }
        });
    }
}