package com.github.hussainderry.sample;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.EncryptionAlgorithm;
import com.github.hussainderry.securepreferences.model.SecurityConfig;
import com.github.hussainderry.securepreferences.util.DataCallback;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private static final String PASSWORD = "demo_secure_password";
    private static final String FILENAME = "secure_demo";

    /* UI elements */
    private TextView mTvConfig;
    private TextView mTvOutput;
    private EditText mEtInput;

    /* Encryption Configurations */
    private SecurityConfig mConfig;
    private SecurePreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTvConfig = (TextView) findViewById(R.id.tv_config);
        mTvOutput = (TextView) findViewById(R.id.tv_output);
        mEtInput = (EditText) findViewById(R.id.et_input);

        initPreferences();
        setupButtons();
    }

    private void initPreferences(){
        mConfig = new SecurityConfig.Builder(PASSWORD.toCharArray())
                .setKeySize(256)
                .setEncryptionAlgorithm(EncryptionAlgorithm.AES)
                .setPbkdf2SaltSize(32)
                .setPbkdf2Iterations(24000)
                .setDigestType(DigestType.SHA256)
                .build();

        mPreferences = SecurePreferences.getInstance(this, FILENAME, mConfig);

        // Register a change listener to show key changes in the output
        mPreferences.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener(){
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key){
                        log("[Listener] Key changed: " + key);
                    }
                });

        mTvConfig.setText(String.format(
                "Algorithm: AES-GCM\nKey Size: 256 bits\nPBKDF2 Iterations: 24,000\nSalt Size: 32 bytes\nDigest: SHA-256"));
    }

    private void setupButtons(){
        /* Store String */
        findViewById(R.id.btn_store_string).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String value = mEtInput.getText().toString();
                if(value.isEmpty()){
                    log("Enter a value first");
                    return;
                }
                try{
                    mPreferences.edit().putString("name", value).commit();
                    log("Stored string: name = \"" + value + "\"");
                }catch(Exception e){
                    log("Error: " + e.getMessage());
                }
            }
        });

        /* Store All Types */
        findViewById(R.id.btn_store_all_types).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    SecurePreferences.Editor editor = mPreferences.edit();
                    editor.putString("name", "Hussain");
                    editor.putInt("age", 30);
                    editor.putLong("timestamp", System.currentTimeMillis());
                    editor.putFloat("score", 98.5f);
                    editor.putBoolean("premium", true);

                    Set<String> tags = new HashSet<>();
                    tags.add("android");
                    tags.add("security");
                    tags.add("encryption");
                    editor.putStringSet("tags", tags);

                    editor.commit();
                    log("Stored all types: String, int, long, float, boolean, StringSet");
                }catch(Exception e){
                    log("Error: " + e.getMessage());
                }
            }
        });

        /* Read All */
        findViewById(R.id.btn_read_all).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Map<String, ?> all = mPreferences.getAll();
                    if(all.isEmpty()){
                        log("getAll(): (empty)");
                        return;
                    }
                    StringBuilder sb = new StringBuilder("getAll():\n");
                    for(Map.Entry<String, ?> entry : all.entrySet()){
                        sb.append("  ").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
                    }
                    log(sb.toString().trim());
                }catch(Exception e){
                    log("Error: " + e.getMessage());
                }
            }
        });

        /* Read Async (Callback) */
        findViewById(R.id.btn_read_async).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    log("Loading \"name\" async...");
                    mPreferences.getAsyncDataLoader().getString("name", "(not set)", new DataCallback<String>(){
                        @Override
                        public void onDataLoaded(String data){
                            log("Async result (main thread): name = \"" + data + "\"");
                        }
                    });
                }catch(Exception e){
                    log("Error: " + e.getMessage());
                }
            }
        });

        /* Remove Key */
        findViewById(R.id.btn_remove_key).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    mPreferences.edit().remove("name").commit();
                    log("Removed key: \"name\"");
                }catch(Exception e){
                    log("Error: " + e.getMessage());
                }
            }
        });

        /* Clear All */
        findViewById(R.id.btn_clear_all).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    mPreferences.edit().clear().commit();
                    log("Cleared all preferences");
                }catch(Exception e){
                    log("Error: " + e.getMessage());
                }
            }
        });

        /* Close + Try Read */
        findViewById(R.id.btn_close_and_read).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    mPreferences.close();
                    log("Closed SecurePreferences (key material zeroed)");
                    log("Attempting read after close...");
                    mPreferences.getString("name", null);
                    log("ERROR: Read succeeded after close (should not happen)");
                }catch(IllegalStateException e){
                    log("Caught expected error: " + e.getMessage());
                }catch(Exception e){
                    log("Unexpected error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        });

        /* Reinitialize */
        findViewById(R.id.btn_reinitialize).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                initPreferences();
                log("Reinitialized SecurePreferences");
            }
        });
    }

    private void log(String message){
        String current = mTvOutput.getText().toString();
        if(current.isEmpty()){
            mTvOutput.setText("> " + message);
        }else{
            mTvOutput.setText(current + "\n> " + message);
        }
    }
}
