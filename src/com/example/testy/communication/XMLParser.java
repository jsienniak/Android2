package com.example.testy.communication;

import java.io.IOException;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class XMLParser {

	public static Response parse(Reader in) {
		XmlPullParser parser = null;
		try {
			parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// return readFeed(parser);
		return readResponse(parser);
	}

	private static Response readResponse(XmlPullParser parser) {
		String message = null;
		try {
			int eventType = parser.getEventType();
			parser.require(XmlPullParser.START_DOCUMENT, null, null);
			while (eventType != XmlPullParser.END_DOCUMENT) {
				Log.d("dep",""+parser.getDepth());
				Log.d("type",""+eventType);
				if (eventType == XmlPullParser.START_TAG&&parser.getName().equals("message")) {
					parser.next();
					message = parser.getText();
					Log.d("parse"," "+message);
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
		Log.d("parse","costam"+message);
		return new Response(message);
	}

}
