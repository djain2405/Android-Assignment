package com.divyajain.earthquakedataapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.divyajain.earthquakedataapp.Data.Earthquake;
import com.divyajain.earthquakedataapp.Fragments.MainActivityFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentedManager;
    private Button getDataButton;
    private FrameLayout frameLayout;
    private MainActivityFragment mainActivityFragment;
    private TextView description;
    //public static ArrayList<Earthquake> earthquakeModelList;
    ProgressDialog dialog;
    private String API_URL = "http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getDataButton = (Button) findViewById(R.id.getdata_button);
        fragmentedManager = getSupportFragmentManager();
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        description = (TextView) findViewById(R.id.description);
        description.setText(R.string.descriptionText);
        getDataButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initializeDialog();
                startDownload();


            }
        });

    }
    private void startDownload() {

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(API_URL, this);

    }

    private void initializeDialog() {
        dialog = ProgressDialog.show(MainActivity.this, "", getApplicationContext().getResources().getString(R.string.ProgressbarText), true);
        dialog.show();
    }

    public void populateList(ArrayList<Earthquake> earthquakeModelList){
        if (frameLayout != null) {
            frameLayout.setVisibility(View.VISIBLE);
            mainActivityFragment = new MainActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("earthquakelist", earthquakeModelList);
            mainActivityFragment.setArguments(bundle);
            fragmentedManager.beginTransaction().add(R.id.fragment_container, mainActivityFragment).commit();
            dialog.dismiss();
            getDataButton.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
