package com.fredrikhulaas.xmlparser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private TextView tv;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        parseXml();
    }

    private void parseXml() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("forecast.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            processParsing(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<WeatherData> weatherList = new ArrayList<>();
        int eventType = parser.getEventType();
        String weather = "";
        WeatherData currentWeather = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tag = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    System.out.println(tag);
                    if (parser.getName().equalsIgnoreCase("name") && parser.getAttributeCount() == 0) {
                        tag = parser.nextText();
                        tv.setText("Location:  " + tag);
                    } else if (parser.getName().equalsIgnoreCase("credit")) {
                        parser.nextTag();
                        tag = parser.getAttributeValue(null, "text");
                        tv5.setText("Fra yr:  " + tag);
                    } else if (parser.getName().equalsIgnoreCase("temperature")){
                        tag = parser.getAttributeValue(null, "value");
                        weather = parser.getAttributeValue(null, "unit");
                        tv1.setText("Temperature: " + tag + " " + weather);
                    } else if (tag.equalsIgnoreCase("pressure")) {
                        tag = parser.getAttributeValue(null,"value");
                        tv2.setText("Pressure: " + tag);
                    } else if (parser.getName().equalsIgnoreCase("title")) {
                        tag = parser.nextText();
                        tv3.setText("Today:  " + tag);
                    } else if (parser.getName().equalsIgnoreCase("windDirection")) {
                        tag = parser.getAttributeValue(null,"name");
                        tv4.setText("WindDirection:  " + tag);
                        weather = parser.getAttributeValue(null, "name");
                    } else if (parser.getName().equalsIgnoreCase("windSpeed")) {
                        tag = parser.getAttributeValue(null, "name");
                        weather = parser.getAttributeValue(null,"mps");
                        tv7.setText("WindSpeed:  " + tag + " " + weather +" mps");
                    } else if (parser.getName().equalsIgnoreCase("tabular")) {
                        parser.nextTag();
                        tag = parser.getAttributeValue(null,"from");
                        weather = parser.getAttributeValue(null,"to");
                        tv6.setText("Time from: " + tag + " " + "Time to: " + weather);
                    } else if (parser.getName().equalsIgnoreCase("strong")) {
                        tag = parser.nextText();
                        tv8.setText("Body:  " + tag);
                    }
            }
            eventType = parser.next();
        }
    }
}
