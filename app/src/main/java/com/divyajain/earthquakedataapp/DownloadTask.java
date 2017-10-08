package com.divyajain.earthquakedataapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.divyajain.earthquakedataapp.Data.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class DownloadTask extends AsyncTask<Object, Integer, String> {

    MainActivity callerActivity;
    ArrayList<Earthquake> earthquakeModelList;
    Geocoder geocoder;


    @Override
    protected String doInBackground(Object... params) {

        String urlStr = (String) params[0];
        callerActivity = (MainActivity) params[1];
        if (!isCancelled()) {

            try {
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String resultString = stringBuilder.toString();
                    if (resultString != null) {

                        return resultString;
                    } else {
                        throw new IOException("No response received.");
                    }
                }
                finally{
                    urlConnection.disconnect();
                }

            } catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        return "";
    }

    @Override
    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }

        Log.i("INFO", response);

        JSONObject jObject = null;
        try {
            jObject = new JSONObject(response);
            JSONArray dataArray = jObject.getJSONArray("earthquakes");

            earthquakeModelList = new ArrayList<>();
            geocoder = new Geocoder(callerActivity.getApplicationContext(), Locale.getDefault());
            for (int i = 0; i < dataArray.length(); i++) {
                Earthquake earthquakeModel = new Earthquake();
                JSONObject dataobj = dataArray.getJSONObject(i);
                try {

                    earthquakeModel.setLatitude(dataobj.getString("lat"));
                    earthquakeModel.setLongitude(dataobj.getString("lng"));

                    earthquakeModel.setDate(dataobj.getString("datetime"));

                    earthquakeModel.setMagnitude(dataobj.getString("magnitude"));

                    List<Address> addresses = geocoder.getFromLocation(Double.valueOf(earthquakeModel.getLatitude()), Double.valueOf(earthquakeModel.getLongitude()), 1);
                    String subArea = "";
                    if(addresses != null && addresses.size() > 0)
                    {
                        subArea = addresses.get(0).getFeatureName();
                    }
                    else
                    {
                        subArea = "Show Location on Map";
                    }
                    earthquakeModel.setSubArea(subArea);
                    earthquakeModel.setDepth(dataobj.getString("depth"));
                    earthquakeModelList.add(earthquakeModel);
                }
                catch (Exception e) {
                            e.printStackTrace();

                }
            }

             callerActivity.populateList(earthquakeModelList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
