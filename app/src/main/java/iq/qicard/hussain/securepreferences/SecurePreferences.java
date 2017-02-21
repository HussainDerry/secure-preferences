package iq.qicard.hussain.securepreferences;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import iq.qicard.hussain.securepreferences.security.CipherAES;
import iq.qicard.hussain.securepreferences.security.CipherSHA;
import iq.qicard.hussain.securepreferences.util.AesCipherHelper;

public class SecurePreferences implements SharedPreferences{

    private static final String DEFAULT_FILENAME = "secured_prefs";
    private static final String CHARSET = "UTF-8";
    private final CipherAES mAES;

    private Context mContext;
    private SharedPreferences mProxyPreferences;

    private SecurePreferences(Context context, String fileName, String password) {
        this.mContext = context;
        this.mAES = AesCipherHelper.generateFromSinglePassphrase(password);
        this.mProxyPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    private String generateKeyHash(String key){
        try{
            byte[] mBytes = CipherSHA.hashUsingSHA256(key.getBytes(CHARSET));
            return Base64.encodeBase64URLSafeString(mBytes);
        }catch(UnsupportedEncodingException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String encryptToBase64(String data){
        try{
            byte[] mBytes = mAES.encrypt(data.getBytes(CHARSET));
            return Base64.encodeBase64URLSafeString(mBytes);
        }catch(UnsupportedEncodingException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String decryptFromBase64(String base64Data){
        try{
            byte[] data = Base64.decodeBase64(base64Data);
            byte[] decrypted = mAES.decrypt(data);
            return new String(decrypted, CHARSET);
        }catch(UnsupportedEncodingException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String key, String defValue){
        final String encryptedData = mProxyPreferences.getString(generateKeyHash(key), null);
        return encryptedData != null ? decryptFromBase64(encryptedData) : defValue;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> set) {
        return null;
    }

    @Override
    public int getInt(String key, int defValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
        return 0;
    }

    @Override
    public float getFloat(String key, float defValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return mProxyPreferences.contains(generateKeyHash(key));
    }

    @Override
    public SharedPreferences.Editor edit() {
        return new Editor();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        mProxyPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        mProxyPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public class Editor implements SharedPreferences.Editor{

        protected SharedPreferences.Editor mProxyEditor;

        public Editor(){
            this.mProxyEditor = SecurePreferences.this.mProxyPreferences.edit();
        }

        @Override
        public SharedPreferences.Editor putString(String key, String data){
            String hashedKey = generateKeyHash(key);
            String encryptedData = encryptToBase64(data);
            mProxyEditor.putString(hashedKey, encryptedData);
            return this;
        }

        @Override
        public SharedPreferences.Editor putStringSet(String key, Set<String> set) {
            String hashedKey = generateKeyHash(key);
            Set<String> encryptedSet = new HashSet<>();

            for(String temp : set) {
                encryptedSet.add(encryptToBase64(temp));
            }

            mProxyEditor.putStringSet(hashedKey, encryptedSet);
            return this;
        }

        @Override
        public SharedPreferences.Editor putInt(String key, int data) {
            String hashedKey = generateKeyHash(key);
            String encryptedData = encryptToBase64(Integer.toString(data));
            mProxyEditor.putString(hashedKey, encryptedData);
            return this;
        }

        @Override
        public SharedPreferences.Editor putLong(String key, long data){
            String hashedKey = generateKeyHash(key);
            String encryptedData = encryptToBase64(Long.toString(data));
            mProxyEditor.putString(hashedKey, encryptedData);
            return this;
        }

        @Override
        public SharedPreferences.Editor putFloat(String key, float data) {
            String hashedKey = generateKeyHash(key);
            String encryptedData = encryptToBase64(Float.toString(data));
            mProxyEditor.putString(hashedKey, encryptedData);
            return this;
        }

        @Override
        public SharedPreferences.Editor putBoolean(String key, boolean data) {
            String hashedKey = generateKeyHash(key);
            String encryptedData = encryptToBase64(Boolean.toString(data));
            mProxyEditor.putString(hashedKey, encryptedData);
            return this;
        }

        @Override
        public SharedPreferences.Editor remove(String key) {
            mProxyEditor.remove(generateKeyHash(key));
            return this;
        }

        @Override
        public SharedPreferences.Editor clear() {
            mProxyEditor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return mProxyEditor.commit();
        }

        @Override
        public void apply() {
            mProxyEditor.commit();
        }

    }

}
