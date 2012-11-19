package com.example.zpi.communication;

import java.io.IOException;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XMLParser {

	public static Response parse(Reader in) throws ServerErrorException {
		XmlPullParser parser = null;
		try {
			parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return readResponse(parser);
	}

	private static Response readResponse(XmlPullParser parser) throws ServerErrorException {
		Response wyn = new Response();
		try {
			int eventType = parser.getEventType();
			parser.require(XmlPullParser.START_DOCUMENT, null, null);
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("error")) {
					parser.next();
					throw new ServerErrorException(parser.getText());
				} 
				if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("message")) {
					parser.next();
					wyn.setMessage(parser.getText());
				}  else
                if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("value")) {
                    parser.next();
                    wyn.setValue(parser.getText());
                }
                eventType = parser.next();
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
