package com.example.zpi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.zpi.Harmonogramy.ViewHolder;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 04.12.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class ProfileMenuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private String[] naglowek;
    private String[] opis;
    private ArrayList<Profil> profile;
    private boolean brakProfilu=false;
    Connect c;
    Profile profy;

    public ProfileMenuAdapter(Context context,ArrayList<Profil> p){
        mInflater = LayoutInflater.from(context);
        this.naglowek = nazwy(p);
        this.opis=opisy(p);
        profy = (Profile)context;
        c=new Connect(context);
        profile=p;
    }
    public String[] nazwy(ArrayList<Profil> prof){

        String pom="";
        for(int i=0;i<prof.size();i++){
            pom+=prof.get(i).getNazwa()+" ";
            if(prof.get(i).getNazwa().equals("BrakProfili"))
                brakProfilu=true;
        }
        String[] nazwy=pom.split("[ ]+");
        return nazwy;
    }
    public String[] opisy(ArrayList<Profil> prof){
        String[] opisy=new String[prof.size()];
        for(int i=0;i<prof.size();i++){
            if(prof.get(i).getNazwa().equals("BrakProfili"))
                opisy[i]+="Dodaj nowe profile";
            else
                opisy[i]="Swiatlo "+(prof.get(i).getSwiatlo().equals("true")?"Wł.":"Wył")+
                        " Roleta "+prof.get(i).getRoleta()+"%"+" Woda "+prof.get(i).getWoda()+"C";

        }
        return opisy;
    }
    public Profil pobierzProfil(int i){
        return profile.get(i);
    }
    @Override
    public int getCount() {
        return naglowek.length;
    }

    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public Profil getProfil(int i){
        return profile.get(i);
    }
    public void setProfil(Profil p,int position){
        profile.remove(position);
        profile.add(position, p);
        try {
            c.requestDelProfile(p.getId());
            c.requestSetProfile(p);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            //InternetAlert internetAlert=new InternetAlert();
            //internetAlert.zwrocAlert();
        }
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
         Button btn;
        final int pos=position;
        final ViewGroup rodzic=parent;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.prof_menu_adapter, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.ProfNagl);
            holder.text2=(TextView) convertView.findViewById(R.id.ProfOpis);
            btn=(Button) convertView.findViewById(R.id.ProfMenuWl);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    setProfil(getProfil(pos), pos);
                    profy.onResume();
                    Toast.makeText(rodzic.getContext(), "Uruchomiono profil "+getProfil(pos).getNazwa(), Toast.LENGTH_SHORT).show();
                }
            });
            if(brakProfilu)
                btn.setEnabled(false);
            btn.setFocusable(false);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.text.setText(naglowek[position]);
        holder.text2.setText(opis[position]);

        try{
            btn=(Button) convertView.findViewById(R.id.ProfMenuWl);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setProfil(getProfil(pos), pos);
                    profy.onResume();
                    Toast.makeText(rodzic.getContext(), "Uruchomiono profil "+getProfil(pos).getNazwa(), Toast.LENGTH_SHORT).show();
                }
            });
            //btn.setChecked(profile.get(position).isWl());
        }
        catch (Exception e){}
        convertView.setBackgroundColor(0xFFFFFFF);
        return convertView;
    }
}
