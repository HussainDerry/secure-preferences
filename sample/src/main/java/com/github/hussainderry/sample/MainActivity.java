package com.github.hussainderry.sample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.SecurityConfig;

import java.security.SecureRandom;
import java.util.UUID;

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
                Toast.makeText(MainActivity.this, "Saving to Secure Prefs", Toast.LENGTH_SHORT).show();
                saveToSecurePref("msg", "Hello World!");
                Toast.makeText(MainActivity.this, "From Secure Prefs: " + getFromSecurePref("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToSecurePref(String key, String data){
        mEditor.putString(key, data);
        mEditor.apply();
    }

    private String getFromSecurePref(String key){
        return mPreferences.getString(key, null);
    }


}
