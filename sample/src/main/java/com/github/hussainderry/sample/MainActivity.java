package com.github.hussainderry.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.hussainderry.securepreferences.SecurePreferences;

public class MainActivity extends AppCompatActivity {

    private static final String PASSWORD = "thisisastrongpassword";
    private static final String FILENAME = "securefile";
    private SecurePreferences mPreferences;
    private SecurePreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = SecurePreferences.getInstance(MainActivity.this, FILENAME, PASSWORD);
        mEditor = mPreferences.edit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Saving to SecurePreferences ...", Toast.LENGTH_SHORT).show();
                saveToSecurePref("Hello World!");
                Toast.makeText(MainActivity.this, "Retrieved from Prefs: " + getFromSecurePref(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToSecurePref(String data){
        mEditor.putString("msg", data);
        mEditor.commit();
    }

    private String getFromSecurePref(){
        return mPreferences.getString("msg", null);
    }


}
