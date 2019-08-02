package com.github.hussainderry.sample;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.EncryptionAlgorithm;
import com.github.hussainderry.securepreferences.model.SecurityConfig;
import com.github.hussainderry.securepreferences.util.AsyncDataLoader;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    private static final String PASSWORD = "thisisastrongpassword";
    private static final String FILENAME = "securefile";
    private SecurePreferences mPreferences;
    private SecurePreferences.Editor mEditor;
    private AsyncDataLoader mAsyncLoader;

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
                .setKeySize(256)
                .setEncryptionAlgorithm(EncryptionAlgorithm.AES)
                .setPbkdf2SaltSize(64)
                .setPbkdf2Iterations(24000)
                .setDigestType(DigestType.SHA256)
                .build();

        mPreferences = SecurePreferences.getInstance(MainActivity.this, FILENAME, fullConfig);
        mEditor = mPreferences.edit();
        mAsyncLoader = mPreferences.getAsyncDataLoader();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Saving to Secure Prefs", Toast.LENGTH_SHORT).show();
                saveToSecurePref("msg", "Hello World!");
                try {
                    Toast.makeText(MainActivity.this, "From Secure Prefs: " + getFromSecurePref("msg"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveToSecurePref(String key, String data){
        mEditor.putString(key, data);
        mEditor.apply();
    }

    private String getFromSecurePref(String key) throws Exception{
        Future<String> mFuture = mAsyncLoader.getString(key, null);
        while(!mFuture.isDone()){
            Toast.makeText(this, "Loading from pref!", Toast.LENGTH_SHORT).show();
            SystemClock.sleep(50);
        }

        return mFuture.get();
    }


}
