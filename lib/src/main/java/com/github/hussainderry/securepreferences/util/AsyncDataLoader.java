/*
 * Copyright 2017 Hussain Al-Derry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hussainderry.securepreferences.util;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Helps get data from shared preferences asynchronously
 * @author Hussain Al-Derry
 */
public final class AsyncDataLoader{

    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 4;
    private static final long KEEP_ALIVE_SECONDS = 30L;

    private final SharedPreferences mPreferences;
    private final ExecutorService mExecutorService;
    private final Handler mCallbackHandler;

    public AsyncDataLoader(SharedPreferences preferences){
        this(preferences, new Handler(Looper.getMainLooper()));
    }

    public AsyncDataLoader(SharedPreferences preferences, Handler callbackHandler){
        if(preferences == null){
            throw new IllegalArgumentException("Param cannot be null!");
        }

        this.mPreferences = preferences;
        this.mCallbackHandler = callbackHandler;
        this.mExecutorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public void shutdown(){
        mExecutorService.shutdown();
    }

    public Future<String> getString(final String key, final String defValue) {
        return mExecutorService.submit(new Callable<String>() {
            @Override
            public String call(){
                return mPreferences.getString(key, defValue);
            }
        });
    }

    public Future<Set<String>> getStringSet(final String key, final Set<String> defSet) {
        return mExecutorService.submit(new Callable<Set<String>>() {
            @Override
            public Set<String> call(){
                return mPreferences.getStringSet(key, defSet);
            }
        });
    }

    public Future<Integer> getInt(final String key, final Integer defValue) {
        return mExecutorService.submit(new Callable<Integer>() {
            @Override
            public Integer call(){
                return mPreferences.getInt(key, defValue);
            }
        });
    }

    public Future<Long> getLong(final String key, final Long defValue) {
        return mExecutorService.submit(new Callable<Long>() {
            @Override
            public Long call(){
                return mPreferences.getLong(key, defValue);
            }
        });
    }

    public Future<Float> getFloat(final String key, final Float defValue) {
        return mExecutorService.submit(new Callable<Float>() {
            @Override
            public Float call(){
                return mPreferences.getFloat(key, defValue);
            }
        });
    }

    public Future<Boolean> getBoolean(final String key, final Boolean defValue) {
        return mExecutorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                return mPreferences.getBoolean(key, defValue);
            }
        });
    }

    public void getString(final String key, final String defValue, final DataCallback<String> callback){
        mExecutorService.execute(new Runnable(){
            @Override
            public void run(){
                final String result = mPreferences.getString(key, defValue);
                mCallbackHandler.post(new Runnable(){
                    @Override
                    public void run(){ callback.onDataLoaded(result); }
                });
            }
        });
    }

    public void getStringSet(final String key, final Set<String> defSet, final DataCallback<Set<String>> callback){
        mExecutorService.execute(new Runnable(){
            @Override
            public void run(){
                final Set<String> result = mPreferences.getStringSet(key, defSet);
                mCallbackHandler.post(new Runnable(){
                    @Override
                    public void run(){ callback.onDataLoaded(result); }
                });
            }
        });
    }

    public void getInt(final String key, final Integer defValue, final DataCallback<Integer> callback){
        mExecutorService.execute(new Runnable(){
            @Override
            public void run(){
                final int result = mPreferences.getInt(key, defValue);
                mCallbackHandler.post(new Runnable(){
                    @Override
                    public void run(){ callback.onDataLoaded(result); }
                });
            }
        });
    }

    public void getLong(final String key, final Long defValue, final DataCallback<Long> callback){
        mExecutorService.execute(new Runnable(){
            @Override
            public void run(){
                final long result = mPreferences.getLong(key, defValue);
                mCallbackHandler.post(new Runnable(){
                    @Override
                    public void run(){ callback.onDataLoaded(result); }
                });
            }
        });
    }

    public void getFloat(final String key, final Float defValue, final DataCallback<Float> callback){
        mExecutorService.execute(new Runnable(){
            @Override
            public void run(){
                final float result = mPreferences.getFloat(key, defValue);
                mCallbackHandler.post(new Runnable(){
                    @Override
                    public void run(){ callback.onDataLoaded(result); }
                });
            }
        });
    }

    public void getBoolean(final String key, final Boolean defValue, final DataCallback<Boolean> callback){
        mExecutorService.execute(new Runnable(){
            @Override
            public void run(){
                final boolean result = mPreferences.getBoolean(key, defValue);
                mCallbackHandler.post(new Runnable(){
                    @Override
                    public void run(){ callback.onDataLoaded(result); }
                });
            }
        });
    }
}
