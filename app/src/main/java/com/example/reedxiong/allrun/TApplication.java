package com.example.reedxiong.allrun;

import android.app.Application;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Reed.Xiong on 2016/7/13.
 */
public class TApplication extends Application {
    public static MultiUserChat multiUserChat;
    public static String host,domain;
    private static int port;
    public static XMPPConnection xmppConnection;
    public static TApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        //this super() mustn‘t be deleted
        // get the value by pull paser
        instance=this;
        getValue();
        connectServer();
    }

    private void connectServer() {
        new Thread(){
            @Override
            public void run() {
                //1configure class
                //2.connect classnnection
                ConnectionConfiguration connectionConfiguration
                        =new ConnectionConfiguration(host,port,domain);
                 xmppConnection=new XMPPConnection(connectionConfiguration);
                try {
                    xmppConnection.connect();
                    Log.i("info","XMPP CONNext"+xmppConnection.isConnected());
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getValue() {
        XmlResourceParser xmlResourceParser =this.getResources().getXml(R.xml.config);
        try {
            int eventType=xmlResourceParser.getEventType();
            while(eventType!=XmlResourceParser.END_DOCUMENT){
                switch (eventType){
                    case XmlResourceParser.START_TAG :
                        String key=xmlResourceParser.getName();
                        if(key.equals("host")){
                            host=xmlResourceParser.nextText();
                        }
                        if(key.equals("port")){
                            port=Integer.parseInt(xmlResourceParser.nextText());
                        }
                        if(key.equals("host")){
                            domain=xmlResourceParser.nextText();
                        }
                        break;
                }
                eventType=xmlResourceParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
