package com.example.projectmodule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


public class MessageHandler {
	
	private int msgID;
	private String getURL;
	private HashMap<String, String> msg;
	JSONParser jParser;
	
	public MessageHandler()
	{
		msgID = 0;
		jParser = new JSONParser();
		getURL = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/ActivityLog.php";
	}
	
	
	public void GetLatest()
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ID","900"));
		
		JSONObject json = jParser.makeHttpRequest(getURL, "GET", params);
		
	}

}
