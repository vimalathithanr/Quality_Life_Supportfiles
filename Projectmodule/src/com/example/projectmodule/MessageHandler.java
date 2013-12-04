package com.example.projectmodule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;



public class MessageHandler {
	
	private int msgID;
	private String URL;
	private HashMap<String, String> msg;
	JSONParser jParser;
	
	public MessageHandler()
	{
		msgID = 0;
		jParser = new JSONParser();
		URL = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/MessageQueue.php";
	}
	
	
	public void GetLatest()
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ID","900"));
		
		JSONObject json = jParser.makeHttpRequest(URL, "GET", params);
		
		try{
		Log.d("Latest Message :", json.toString());
		}
		catch(Exception e)
		{
			Log.d("Error :", e.getMessage());
		}
	}
	
	
	
	public void AckMsg(String msgID)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ID",msgID));
		
		JSONObject json = jParser.makeHttpRequest(URL, "GET", params);
		
		try{
		Log.d("Ack Message :", json.toString());
		}
		catch(Exception e)
		{
			Log.d("Error :", e.getMessage());
		}
	}
		

}
