package com.example.projectmodule;

import java.util.ArrayList;
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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EditLogActivity extends Activity {
	EditText inputDesc;
	Button btnSave;

	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_detials = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/GetActivityID.php";
	//private static final String url_product_detials = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/ActivityLog.php";
	// url to update product
	private static final String url_update_product = "http://www.cs.indiana.edu/cgi-pub/vrajasek/Pervasive-Project/QViz/php/UpdateActivity.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "mastertable";
	private static final String TAG_PID = "Activity";
	private static final String TAG_ActivityID = "ActivityID";
	private static final String TAG_NAME = "IsQualityTime";
	private static final String TAG_DESCRIPTION = "Comments";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_log);

		// save button
		btnSave = (Button) findViewById(R.id.btnSave);

		// getting product details from intent
		Intent i = getIntent();

		// getting product id (pid) from intent
		pid = i.getStringExtra(TAG_PID);
		
		System.out.println("PIDSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" + pid);

		// Getting complete product details in background thread
		new GetProductDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

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
				pDialog = new ProgressDialog(EditLogActivity.this);
				pDialog.setMessage("Loading product details. Please wait...");
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
						int success;
						try {
							// Building Parameters
							String ActivityID = pid;
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("ActivityID", ActivityID));

							System.out.println("REACHEDDDDDDD 112");
							System.out.println("IDDDDDDDDDDDDDDDDDDDD:" + ActivityID);
							// getting product details by making HTTP request
							// Note that product details url will use GET
							// request

							// try {
							JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
							// } catch(Exception e) {
								// System.out.println("Exception:" + e.getStackTrace());
							// }
							System.out.println("REACHEDDDDDDD 119");
							// check your log for json response
							Log.d("Single Product Details", json.toString());

							// json success tag
							success = json.getInt(TAG_SUCCESS);
							System.out.println("REACHEDDDDDDD 125");
							if (success == 1) {
								// successfully received product details
								
								System.out.println("REACHEDDDDDDD 130");
								JSONArray productObj = json
										.getJSONArray(TAG_PRODUCT); // JSON
																	// Array

								// get first product object from JSON Array
								JSONObject mastertable = productObj
										.getJSONObject(0);

								// product with this pid found
								// Edit Text
								
								
								inputDesc = (EditText) findViewById(R.id.inputDesc);
								//txtPrice = (EditText) findViewById(R.id.inputPrice);
								//txtDesc = (EditText) findViewById(R.id.inputDesc);

								// display product data in EditText
								System.out.println("INIITIAL");
								System.out.println("FINALLLLLLLLLLLL" + mastertable.getString(TAG_DESCRIPTION));
								inputDesc.setText(mastertable.getString(TAG_DESCRIPTION));
								
								String yesBoolean = mastertable.getString(TAG_NAME);
								
								RadioButton radioButtonYes;
						        RadioButton radioButtonNo;
						        radioButtonYes = (RadioButton) findViewById(R.id.radioYes);
						        radioButtonNo = (RadioButton) findViewById(R.id.radioNo);

						        if(yesBoolean.equals("1")){
						        	radioButtonYes.setChecked(true);
						        	radioButtonNo.setChecked(false);

						        }else{
						        	radioButtonYes.setChecked(true);
						        	radioButtonNo.setChecked(true);
						        }
								
								//txtPrice.setText(product.getString(TAG_PRICE));
								//txtDesc.setText(product
									//	.getString(TAG_DESCRIPTION));

							} else {
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
		class SaveProductDetails extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(EditLogActivity.this);
				pDialog.setMessage("Saving product ...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Saving product
			 * */
			protected String doInBackground(String... args) {

				// getting updated data from EditTexts
				String ActivityID = pid;
				String IsQualityTime = "1";
				String Comments = inputDesc.getText().toString();

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(TAG_ActivityID, ActivityID));
				params.add(new BasicNameValuePair(TAG_NAME, IsQualityTime));
				params.add(new BasicNameValuePair(TAG_DESCRIPTION, Comments));

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
