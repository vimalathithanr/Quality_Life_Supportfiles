package com.example.projectmodule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	ImageButton addButton;
	ImageButton activityButton;
	ImageButton removeButton;
	ImageButton outputButton;
	ImageButton adminButton;
	Timer timer;

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	   	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
        // Buttons
		addButton = (ImageButton) findViewById(R.id.addButton);
		activityButton = (ImageButton) findViewById(R.id.activityButton);
		removeButton = (ImageButton) findViewById(R.id.removeButton);
		outputButton = (ImageButton) findViewById(R.id.outputButton);
		adminButton = (ImageButton) findViewById(R.id.adminButton);
		timer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				MessageHandler mh = new MessageHandler();
				mh.GetLatest();
				//new MessageHander().execute();
			}
		};
		timer.scheduleAtFixedRate(task, 5000, 5000);
 
        // view products click event
		addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent in = new Intent(getApplicationContext(), AddActivity.class);
                //i.putExtra("city",  City.getText().toString());
                startActivity(in);
             }
        });
		
		
		activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), LogActivity.class);
                startActivity(in);
             }
        });
		
		
		removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), RemoveActivity.class);
                startActivity(in);
             }
        });
		
		
		outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent in = new Intent(getApplicationContext(), WebActivity.class);
                //startActivity(in);
            	//Uri uri = Uri.parse("http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/index.html");
            	//Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            	Intent in = new Intent(getApplicationContext(), WebActivity.class);
            	startActivity(in);
             }
        });
		
		
		adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(in);
             }
        });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	

}
