package com.example.zpi;

import android.util.Log;
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


public class Harmonogramy extends Activity
{

	private Button dodaj;
	private Button wroc;
    private String[] items={"Pierwsze","Drugie","Trzecie"};
    private String[] opisy={"15:00-19:00 100%","15:00-9:00","14:00-20:00 85st" };
    Harmonogram h1=new Harmonogram(1,900,1200,"90st","60st",0,0,"Pon., Wt., Śr.",true);
    Harmonogram h2=new Harmonogram(2,1200,1500,"80%","100%",0,1,"Pon Śr",false);
    Harmonogram h3=new Harmonogram(3,800,1209,"","",0,4,"Pon., Wt., Śr., Pt., Sob., Nd.",true);
    ArrayList<Harmonogram> harm=new ArrayList<Harmonogram>();
    ArrayList<Harmonogram> harmPom=new ArrayList<Harmonogram>();
    Context ctx;
    ListView list;
    HarmMenuAdapter adapter;
    @Override  
    protected void onCreate(Bundle savedInstanceState) 
    {

        harmPom.add(h1);
        harmPom.add(h2);
        harmPom.add(h3);
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.harmonogram);
        int opcja;
        try{

            opcja=getIntent().getExtras().getInt("modul");
            for(int i=0;i<harmPom.size();i++){
                if(harmPom.get(i).modul==opcja){
                    harm.add(harmPom.get(i));
                }
            }
        }
        catch (Exception e){
            harm=harmPom;
            opcja=-1;
        }
        switch (opcja){
            case 0:
                Log.d("modul","woda");
                break;
            case 1:
                Log.d("modul","rolety");
                break;
            case 4:
                Log.d("modul","ogrod");
                break;
            default:
              /*  list = (ListView) findViewById(R.id.harmMenuList);
                HarmMenuAdapter adapter = new HarmMenuAdapter(this, items,opisy);
                list.setAdapter(adapter);*/
                break;
        }
        list = (ListView) findViewById(R.id.harmMenuList);
       // adapter = new HarmMenuAdapter(this, items,opisy);
        adapter=new HarmMenuAdapter(this,harm);
        list.setAdapter(adapter);
        addListener();
        ctx = this.getApplicationContext();
        
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
        wroc=(Button) findViewById(R.id.harmWroc);
        wroc.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();	
			}
		});
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),Dodaj.class);
                i.putExtra("harm",adapter.getHarmonogram(position));
                startActivity(i);
            }
        } );
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
     
    public final String getElementValue( Node elem ) {
             Node child;
             if( elem != null){
                 if (elem.hasChildNodes()){
                     for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                         if( child.getNodeType() == Node.TEXT_NODE  ){
                             return child.getNodeValue();
                         }
                     }
                 }
             }
             return "";
      }
}


