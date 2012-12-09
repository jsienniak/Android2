package com.example.zpi;

import android.util.Log;
import android.widget.TextView;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.alerts.ServerAlert;
import com.example.zpi.communication.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Harmonogramy extends Activity implements ResponseListener
{

	private Button dodaj;
	private Button wroc;
    ArrayList<Harmonogram> harm=new ArrayList<Harmonogram>();
    ArrayList<Harmonogram> harmPom=new ArrayList<Harmonogram>();
    Context ctx;
    ListView list;
    HarmMenuAdapter adapter;
    Connect c;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        c=new Connect(this);
        c.addResponseListener(this);

        try {
            c.requestGetHarm();
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert internetAlert=new InternetAlert(this);
            internetAlert.zwrocAlert();
        }
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.harmonogram);


        ctx = this.getApplicationContext();
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            c.requestGetHarm();
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            InternetAlert internetAlert=new InternetAlert(this);
            internetAlert.zwrocAlert();
        }
    }

    public void addListener(){
    	dodaj = (Button)findViewById(R.id.btnShowPopUp);
        dodaj.setOnClickListener(new OnClickListener()
        {           
            public void onClick(View arg0)
            {
            	Intent i=new Intent(getApplicationContext(),Dodaj.class);
    			startActivity(i);
            }
        });
       // wroc=(Button) findViewById(R.id.harmWroc);
       /* wroc.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();	
			}
		});*/
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),Dodaj.class);
                i.putExtra("harm",adapter.getHarmonogram(position));
                startActivity(i);
            }
        } );

    }

    @Override
    public void processResponse(Response res) {
        if(res.isERROR()){
            ServerAlert servA=new ServerAlert(this);
            servA.zwrocAlert();

        }
        else{
        if(res.getType()==Response.GETHARM){
            harmPom= (ArrayList<Harmonogram>) res.getExtras();

            int opcja;
            try{

                opcja=getIntent().getExtras().getInt("modul");
                for(int i=0;i<harmPom.size();i++){
                    if(harmPom.get(i).getModul()==opcja){
                        harm.add(harmPom.get(i));
                    }
                }
            }
            catch (Exception e){
                harm=harmPom;
                opcja=-1;
            }
            Harmonogram h=new Harmonogram(1,"","","","",-1,-1,"",false);
            list = (ListView) findViewById(R.id.harmMenuList);
            if(harm.isEmpty()){
                harm.add(h);
                list.setEnabled(false);
            }
            adapter=new HarmMenuAdapter(this,harm);
            list.setAdapter(adapter);
            addListener();
        }

        }
    }
    static class ViewHolder{
        TextView text;
        TextView text2;
    }
}


