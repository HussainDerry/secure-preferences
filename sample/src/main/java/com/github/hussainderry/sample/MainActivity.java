package com.github.hussainderry.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.SecurityConfig;

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

        // Minimum Configurations
        SecurityConfig minimumConfig = new SecurityConfig.Builder(PASSWORD)
                .build();

        // Full Configurations
        SecurityConfig fullConfig = new SecurityConfig.Builder(PASSWORD)
                .setAesKeySize(256)
                .setPbkdf2SaltSize(32)
                .setPbkdf2Iterations(24000)
                .setDigestType(DigestType.SHA256)
                .build();

        mPreferences = SecurePreferences.getInstance(MainActivity.this, FILENAME, minimumConfig);
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
