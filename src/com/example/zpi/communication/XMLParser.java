package com.example.zpi.communication;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import android.util.Log;
import com.example.zpi.Harmonogram;
import com.example.zpi.Profil;
import com.example.zpi.Profile;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XMLParser {

    private static ArrayList<Harmonogram> harmonogramy;
    private static ArrayList<Profil> profile;

    public static Response parse(Reader in, Response res) throws ServerErrorException {
		XmlPullParser parser = null;
		try {
			parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return readResponse(parser,res);
	}

	private static Response readResponse(XmlPullParser parser, Response wyn) throws ServerErrorException {
		try {
            if(wyn.getType()==Response.GETHARM){
                harmonogramy = new ArrayList<Harmonogram>();
                wyn.setExtras(harmonogramy);
            }
            if(wyn.getType()==Response.GETPROFILE){
                profile = new ArrayList<Profil>();
                wyn.setExtras(profile);
            }
			int eventType = parser.getEventType();
			parser.require(XmlPullParser.START_DOCUMENT, null, null);
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("status")) {
					parser.next();
                    if(parser.getText().equals("ERR")){
                        wyn.setERROR(true);
                        return wyn;
                    }
				}  else if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("message")) {
					parser.next();
					wyn.setMessage(parser.getText());
				}  else if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("value")) {
                    parser.next();
                    wyn.setValue(parser.getText());
                }  else if(eventType == XmlPullParser.START_TAG&&parser.getName().equals("harmonogramy")){
                    Harmonogram h = new Harmonogram();
                    harmonogramy.add(h);
                    Log.d("liczba",""+harmonogramy.size());
                    parser.next();
                    parser.next();
                    h.setValStop(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setValStart(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    String port = parser.getText();
                    h.setPort(port.equals("null")?0:Integer.parseInt(port));
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setWl(Integer.parseInt(parser.getText())>0);
                    parser.next();
                    parser.next();
                    parser.next();
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setCzasStart(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setModul(Integer.parseInt(parser.getText()));
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setId(Integer.parseInt(parser.getText()));
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setCzasStop(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    h.setDni(parser.getText());
                } else if(eventType == XmlPullParser.START_TAG&&parser.getName().equals("profiles")){
                    Profil p = new Profil();
                    profile.add(p);
                    parser.next();
                    parser.next();
                    p.setWoda(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    p.setSwiatlo(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    p.setRoleta(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                    p.setNazwa(parser.getText());
                    parser.next();
                    parser.next();
                    parser.next();
                eventType = parser.next();
			}
            }
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wyn;
	}

}
