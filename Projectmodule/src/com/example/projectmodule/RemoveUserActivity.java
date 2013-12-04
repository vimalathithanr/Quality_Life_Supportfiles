package com.example.projectmodule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class RemoveUserActivity extends Activity {

	EditText inputDesc;
	Button deleteButton;
	
	Spinner s = (Spinner) findViewById(R.id.spinner1);
    SpinnerAdapter adapter;
	
    ArrayList<HashMap<String, String>> productsList;

	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_detials = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/UserActivity.php";
	// url to update product
	private static final String url_update_product = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/UpdateUser.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "products";
	private static final String TAG_PID = "RFID";
	//private static final String TAG_ActivityID = "ActivityID";
	private static final String TAG_NAME = "IsQualityTime";
	private static final String TAG_DESCRIPTION = "Comments";
	private static final String TAG_USERNAME = "UserName";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_user);

		// Delete button
		deleteButton = (Button) findViewById(R.id.deleteButton);

		// getting product details from intent
		//Intent i = getIntent();

		// getting product id (pid) from intent
		//pid = i.getStringExtra(TAG_PID);
		
		// Getting complete product details in background thread
		new GetProductDetails().execute();

		// save button click event
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// starting background task to update product
				new SaveProductDetails().execute();
			}
		});
	}

		/**
		 * Background Async Task to Get complete product details
		 * */
		class GetProductDetails extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(RemoveUserActivity.this);
				pDialog.setMessage("Loading all the Users... Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Getting product details in background thread
			 * */
			protected String doInBackground(String... params) {

				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						// Check for success tag

						try {
							// Building Parameters
							//String ActivityID = pid;
							List<NameValuePair> params = new ArrayList<NameValuePair>();

							JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
							Log.d("Single Product Details", json.toString());

			                int success = json.getInt(TAG_SUCCESS);
		                
							if (success == 1) {
								// successfully received product details
								
								JSONArray products = json
										.getJSONArray(TAG_PRODUCT); // JSON
																	// Array

			                    for (int i = 0; i < products.length(); i++) {
			                        JSONObject c = products.getJSONObject(i);
			 
			                        String id = c.getString(TAG_PID);
			                        String userName = c.getString(TAG_USERNAME);
			 
			                        // creating new HashMap
			                        HashMap<String, String> map = new HashMap<String, String>();
			 
			                        // adding each child node to HashMap key => value
			                        map.put(TAG_PID, id);
			                        map.put(TAG_USERNAME, userName);
			                        

									s.setAdapter(adapter);
									//adapter.add(userName);
			 
			                        // adding HashList to ArrayList
			                        productsList.add(map);

							} 
							}else {
								// product with pid not found
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog once got all details
				pDialog.dismiss();
			}
		}

		/**
		 * Background Async Task to Save product Details
		 * */
		
		//Vimal - update pid with Username
		class SaveProductDetails extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(RemoveUserActivity.this);
				pDialog.setMessage("Deleting...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Saving product
			 * */
			protected String doInBackground(String... args) {

				// getting updated data from EditTexts
				String UserName = pid;

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(TAG_USERNAME, UserName));

				// sending modified data through http request
				// Notice that update product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(
						url_update_product, "POST", params);

				// check json success tag
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// successfully updated
						Intent i = getIntent();
						// send result code 100 to notify about product update
						setResult(100, i);
						finish();
					} else {
						// failed to update product
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog once product updated
				pDialog.dismiss();
			}
		}

	
}
