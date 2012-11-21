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


public class Harmonogramy extends Activity
{

	int hour, minute;
	private Button dodaj;
	private Button wroc;
    private String[] items={"Pierwsze","Drugie","Trzecie"};
    private String[] opisy={"15:00-19:00","15:00-9:00","14:00-20:00" };
    Context ctx;
    ListView list;
 
    @Override  
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.harmonogram);
        int opcja;
        try{

            opcja=getIntent().getExtras().getInt("modul");
        }
        catch (Exception e){
            opcja=-1;
        }
        switch (opcja){
            case 0:
                Log.d("modul","woda");
                break;
            case 1:
                Log.d("modul","rolety");
                break;
            case 2:
                Log.d("modul","ogrod");
                break;
            default:
              /*  list = (ListView) findViewById(R.id.harmMenuList);
                HarmMenuAdapter adapter = new HarmMenuAdapter(this, items,opisy);
                list.setAdapter(adapter);*/
                break;
        }
        list = (ListView) findViewById(R.id.harmMenuList);
        HarmMenuAdapter adapter = new HarmMenuAdapter(this, items,opisy);
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
            	/*try {
        			URL url = new URL(
        					"http://192.168.43.252:8080/servtest/do?action=module.ping");
        			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        			conn.connect();
        			BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
        			StringBuilder sb = new StringBuilder();
        			String line = "";
        			while ((line = br.readLine())!=null){
        				sb.append(line);
        				Log.d("http",line);
        			}
        			TextView tv = (TextView) findViewById(R.id.textView1);
        			tv.setText(sb.toString());
        			Toast t =	Toast.makeText(ctx, sb.toString(), 1000);
        			t.show();
                } catch (Exception e) {
        			e.printStackTrace();
        		}
            	*/
           /* 	Context ctx=getApplication();
            	Connect c = new Connect(ctx);
                Response req = null;
                        try {
                                req = c.request("module", "dupa");
                        } catch (NoInternetException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                TextView tv = (TextView) findViewById(R.id.textView1);
                //tv.setText("text"/*req.getMessage()*///);
                //tv.setText(req.getMessage());
            	Intent i=new Intent(getApplicationContext(),Dodaj.class);
    			startActivity(i);
            	//showDialog(TIME_DIALOG_ID);
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
            	System.out.println(position);
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


